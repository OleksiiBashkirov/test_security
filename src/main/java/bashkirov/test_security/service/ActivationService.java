package bashkirov.test_security.service;

import bashkirov.test_security.exception.InvalidActivationKeyException;
import bashkirov.test_security.model.Activation;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationService {
    private final JdbcTemplate jdbcTemplate;

    public void save(Activation activation) {
        jdbcTemplate.update(
                "insert into activtion(key, email) values (?,?)",
                activation.getKey(),
                activation.getEmail()
        );
    }

    public String generateKey(String email) {
        StringBuilder sb = new StringBuilder();
        String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        int keyLength = (int) (15 + (Math.random() * 6));
        for (int i = 0; i < keyLength; i++) {
            int randomAlphabetIndex = (int) (Math.random() * alphabet.length());
            sb.append(alphabet.charAt(randomAlphabetIndex));
        }

        save(new Activation(sb.toString(), email));

        return sb.toString();
    }

    public void activate(String key) {
        Optional<Activation> optionalActivation =
                jdbcTemplate.query(
                        "select * from activtion where key = ?",
                        new Object[]{key},
                        new BeanPropertyRowMapper<>(Activation.class)
                ).stream().findAny();
        if (optionalActivation.isEmpty()) {
            throw new InvalidActivationKeyException("Failed to activate key");
        }

        Activation existedActivation = optionalActivation.get();

        jdbcTemplate.update(
                "update student set is_enable = ? where email = ?",
                Boolean.TRUE,
                existedActivation.getEmail()
        );

        jdbcTemplate.update(
                "delete from activtion where key = ?",
                key
        );
    }
}
