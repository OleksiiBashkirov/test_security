package bashkirov.test_security.controller;

import bashkirov.test_security.model.Student;
import bashkirov.test_security.service.StudentRegistrationService;
import bashkirov.test_security.validation.StudentValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final StudentValidator studentValidator;
    private final StudentRegistrationService studentRegistrationService;


    @GetMapping("/registration")
    public String registrationPage(
            @ModelAttribute("newStudent") Student newStudent
    ) {
        return "registration-page";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("newStudent") Student newStudent,
            BindingResult bindingResult
    ) {
        studentValidator.validate(newStudent, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration-page";
        }
        studentRegistrationService.registration(newStudent);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "enter-page";
    }
}
