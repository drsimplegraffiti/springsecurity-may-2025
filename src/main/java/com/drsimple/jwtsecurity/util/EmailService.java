package com.drsimple.jwtsecurity.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;


    @Async
    public void sendVerificationEmail(String to, String subject, String templateName, Map<String, Object> templateModel)
            throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set email details
        helper.setTo(to);
        helper.setSubject(subject);

        // Prepare the HTML content using the template and data
        Context context = new Context();
        context.setVariables(templateModel);
        String htmlContent = templateEngine.process(templateName, context);

        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}