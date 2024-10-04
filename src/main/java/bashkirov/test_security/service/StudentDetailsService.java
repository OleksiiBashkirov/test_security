package bashkirov.test_security.service;

import bashkirov.test_security.model.Student;
import bashkirov.test_security.security.StudentDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDetailsService implements UserDetailsService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> optionalStudent = getOptionalStudent(username);

        if (optionalStudent.isEmpty()) {
            throw new UsernameNotFoundException("Failed to find user with username=" + username);
        }
        Student existedStudent = optionalStudent.get();

        return new StudentDetails(existedStudent);
    }

    public Optional<Student> getOptionalStudent(String username) {
        return jdbcTemplate.query(
                "select * from student where username = ?",
                new Object[]{username},
                new BeanPropertyRowMapper<>(Student.class)
        ).stream().findAny();
    }
}
