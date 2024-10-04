package bashkirov.test_security.service;

import bashkirov.test_security.dto.EmailDto;
import bashkirov.test_security.enumeration.Role;
import bashkirov.test_security.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentRegistrationService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final ActivationService activationService;
    private final EmailService emailService;

    public void registration(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(Role.ROLE_USER);
        student.setEnable(true);

        String key = activationService.generateKey(student.getEmail());
        emailService.sendEmail
                (new EmailDto(
                        student.getEmail(),
                        "Activation key",
                        "To activate account please follow the link\nhttp://localhost:8085/activate/" + key
                ));


        jdbcTemplate.update(
                "insert into student(name, lastname, email, username, password, role, is_enable) values (?,?,?,?,?,?,?)",
                student.getName(),
                student.getLastname(),
                student.getEmail(),
                student.getUsername(),
                student.getPassword(),
                student.getRole().toString(),
                student.isEnable()
        );
    }
}
