package bashkirov.test_security.validation;

import bashkirov.test_security.model.Student;
import bashkirov.test_security.service.StudentDetailsService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class StudentValidator implements Validator {
    private final StudentDetailsService studentDetailsService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean supports(Class<?> clazz) {
        return Objects.equals(clazz, Student.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;
        Optional<Student> optionalStudent = studentDetailsService.getOptionalStudent(student.getUsername());
        if (optionalStudent.isPresent()) {
            Student existedStudent = optionalStudent.get();
            if (student.getId() == 0 || student.getId() != existedStudent.getId()) {
                errors.rejectValue(
                        "username",
                        "",
                        String.format("Student with username= %s already exists", student.getUsername())
                );
            }
        }

        optionalStudent = jdbcTemplate.query(
                "select * from student where email = ?",
                new Object[]{student.getEmail()},
                new BeanPropertyRowMapper<>(Student.class)
        ).stream().findAny();
        if (optionalStudent.isPresent()) {
            Student existedStudent = optionalStudent.get();
            if (student.getId() == 0 || student.getId() != existedStudent.getId()) {
                errors.rejectValue(
                        "email",
                        "",
                        String.format("Student with email=%s already exists", student.getEmail())
                );
            }
        }
    }
}
