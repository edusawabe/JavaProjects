/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author eduardo.sawabe
 */
public class MailSender {
    private String user;
    private String pswd;

    public void sendMail(String subject, String msg, String dest, boolean gmail) throws MessagingException {
        Properties props = new Properties();

        if (gmail) {
            /**
             * Parâmetros de conexão com servidor Gmail
             */
            user = "edusawabe@gmail.com";
            pswd = "Uol2004Terra";

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

        } else {
            user = "eduardo.sawabe";
            pswd = "Educap123455";

            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.host", "webmail.cpmbraxis.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth.mechanisms", "NTLM");

            // *** CHANGED ***
            props.put("mail.smtp.auth.ntlm.domain", "corporate");
        }

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pswd);
            }
        };

        Session session = Session.getInstance(props, authenticator);

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("edusawabe@gmail.com")); //Remetente

        message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(dest.replace(';', ',')));
        message.setSubject(subject);//Assunto
        message.setText(msg);
        message.setContent(msg, "text/html;charset=utf-8");
        /**
         * Método para enviar a mensagem criada
         */
        Transport.send(message);

    }
}
