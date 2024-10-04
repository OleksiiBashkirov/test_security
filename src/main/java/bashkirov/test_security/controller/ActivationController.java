package bashkirov.test_security.controller;

import bashkirov.test_security.exception.InvalidActivationKeyException;
import bashkirov.test_security.service.ActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/activate")
@RequiredArgsConstructor
public class ActivationController {
    private final ActivationService activationService;

    @GetMapping("/{key}")
    public String activationPage(
            @PathVariable("key") String key
    ) {
        try {
            activationService.activate(key);
            return "activation-page-successfully";
        } catch (InvalidActivationKeyException ex) {
            return "activation-page-failure";
        }
    }
}
