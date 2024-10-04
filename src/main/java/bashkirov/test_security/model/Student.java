package bashkirov.test_security.model;

import bashkirov.test_security.enumeration.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @NotBlank
    @Size(min = 2, max = 32)
    private String lastname;

    @NotBlank
    @Size(max = 64)
    private String email;

    @NotBlank
    @Size(min = 2, max = 32)
    private String username;

    @NotBlank
    private String password;

    private Role role;

    private boolean isEnable;
}
