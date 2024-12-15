package com.quizapp.quizApp.service.impl;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendWelcomeEmail(String toEmail, String firstName) {
        Email from = new Email("quizapputc@gmail.com");
        String subject = "Bienvenue dans l'application QuizApp";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Bonjour " + firstName + ",\n\nBienvenue dans notre application ! Nous sommes ravis de vous compter parmi nos utilisateurs.\n\nCordialement,\nL'équipe QuizApp.");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
            System.out.println("Response headers: " + response.getHeaders());

        } catch (IOException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }
}
