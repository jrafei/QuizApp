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

    // Méthode principale pour envoyer l'email de bienvenue
    public void sendWelcomeEmail(String toEmail, String firstName, String activationToken) {
        Mail mail = buildWelcomeMail(toEmail, firstName, activationToken);
        sendEmail(mail);
    }

    // Méthode principale pour envoyer l'email avec les informations de connexion
    public void sendCredentialsEmail(String toEmail, String firstName, String email, String password) {
        String messageText = buildCredentialsMessage(firstName, email, password);
        Mail mail = buildMail(toEmail, "Your QuizApp credentials", messageText);
        sendEmail(mail);
    }

    // Méthode générique pour envoyer un email avec SendGrid
    private void sendEmail(Mail mail) {
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            handleSendGridResponse(response);
        } catch (IOException ex) {
            handleError(ex);
        }
    }

    // Gestion des erreurs et affichage de la réponse SendGrid
    private void handleSendGridResponse(Response response) {
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
        System.out.println("Response headers: " + response.getHeaders());
    }

    private void handleError(IOException ex) {
        System.err.println("Error sending email: " + ex.getMessage());
        // Ajoutez une gestion des erreurs plus approfondie si nécessaire (logs, alertes, etc.)
    }

    // Construction du message de bienvenue avec un lien d'activation
    private Mail buildWelcomeMail(String toEmail, String firstName, String activationToken) {
        String activationUrl = "http://localhost:8080/users/activate?token=" + activationToken;
        String subject = "Welcome on QuizApp !";
        String messageText = String.format(
                "Welcome %s,\n\nThank you for registering on QuizApp. Click on the link %s to activate your account.\n\nBest regards,\nQuizApp Team.",
                firstName, activationUrl
        );
        return buildMail(toEmail, subject, messageText);
    }

    // Construction du message contenant les informations de connexion
    private String buildCredentialsMessage(String firstName, String email, String password) {
        return String.format(
                "Hello %s,\n\nYour account has been created successfully." +
                        "Here are your credentials:\n\nE-mail : %s\nTemporary password : %s\n\nPlease log in with this password, once logged in, you can change it in your account settings.\n\nBest regards,\nQuizApp Team.",
                firstName, email, password
        );
    }

    // Méthode pour construire un mail SendGrid générique
    private Mail buildMail(String toEmail, String subject, String messageText) {
        Email from = new Email("quizapputc@gmail.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", messageText);
        return new Mail(from, subject, to, content);
    }

    public void sendForgotPasswordEmail(String toEmail, String firstName, String temporaryPassword) {
        String subject = "Password Reset Request - QuizApp";
        String messageText = String.format(
                "Hello %s,\n\n" +
                        "We received a request to reset your password. Here is your temporary password:\n\n" +
                        "Temporary password: %s\n\n" +
                        "Please log in using this password and change it immediately in your account settings.\n\n" +
                        "If you did not request this password reset, please ignore this email.\n\n" +
                        "Best regards,\nThe QuizApp Team",
                firstName, temporaryPassword
        );
        Mail mail = buildMail(toEmail, subject, messageText);
        sendEmail(mail);
    }

    public void sendValidationCodeEmail(String toEmail, String firstName, String validationCode) {
        String subject = "Account Reactivation Code - QuizApp";
        String messageText = String.format(
                "Hello %s,\n\n" +
                        "We received a request to reactivate your account. Use the following validation code to reactivate your account:\n\n" +
                        "Validation Code: %s\n\n" +
                        "This code is valid for 15 minutes.\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best regards,\nThe QuizApp Team",
                firstName, validationCode
        );
        Mail mail = buildMail(toEmail, subject, messageText);
        sendEmail(mail);
    }

    public void sendQuizAssignmentEmail(String toEmail, String firstName, String quizTitle) {
        String subject = "A New Quiz Has Been Assigned to You!";
        String messageText = String.format(
                "Hello %s,\n\n" +
                        "A new quiz titled '%s' has been assigned to you. Please log in to your account to start the quiz.\n\n" +
                        "Best regards,\nThe QuizApp Team",
                firstName, quizTitle
        );
        Mail mail = buildMail(toEmail, subject, messageText);
        sendEmail(mail);
    }
}
