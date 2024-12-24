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
        Mail mail = buildMail(toEmail, "Vos informations de connexion à QuizApp", messageText);
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
        String subject = "Bienvenue dans l'application QuizApp";
        String messageText = String.format(
                "Bonjour %s,\n\nMerci de vous être inscrit sur QuizApp. Cliquez sur le lien ci-dessous pour activer votre compte :\n%s\n\nCordialement,\nL'équipe QuizApp.",
                firstName, activationUrl
        );
        return buildMail(toEmail, subject, messageText);
    }

    // Construction du message contenant les informations de connexion
    private String buildCredentialsMessage(String firstName, String email, String password) {
        return String.format(
                "Bonjour %s,\n\nVotre compte a été activé avec succès. Voici vos informations de connexion :\n\nEmail : %s\nMot de passe temporaire : %s\n\nVeuillez vous connecter avec ce mot de passe et, une fois connecté, vous pourrez changer votre mot de passe dans les paramètres de votre compte.\n\nCordialement,\nL'équipe QuizApp.",
                firstName, email, password
        );
    }

    // Méthode pour construire un mail SendGrid générique
    private Mail buildMail(String toEmail, String subject, String messageText) {
        Email from = new Email("quizapputc@gmail.com"); // Remplacez par l'email de l'expéditeur
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", messageText);
        return new Mail(from, subject, to, content);
    }
}
