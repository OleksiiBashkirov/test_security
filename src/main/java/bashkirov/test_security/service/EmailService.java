package bashkirov.test_security.service;

import bashkirov.test_security.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getEmail());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getText());

        javaMailSender.send(message);
    }
}
