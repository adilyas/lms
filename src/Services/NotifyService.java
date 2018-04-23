package Services;

import Objects.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class NotifyService {
    private static String USER_NAME = "ilyasgm99@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "2999296029653001"; // GMail password

    /**
     * Not implemented yet.
     * @param receivers
     * @param message
     */
    public <T extends User> void notify(Collection<T> receivers, String message){
        for(T receiver: receivers) {
            System.out.println("NOTIFICATION FOR:");
            System.out.println(receiver);
            System.out.println("TEXT:");
            System.out.println(message);
        }
    }


    public static void sendMail(Collection<User> recipientUsers, String message) {
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";

        String from = USER_NAME;
        String password = PASSWORD;

        sendFromGMail(from, password, recipientUsers, subject, body);
    }

//    this is a toy method made for testing
//    public static void sendMail(String to, String subject, String body) {
//        User user = new User("name", "surname", "type", "12345", "address", to);
//        ArrayList<User> recipientUsers = new ArrayList<>();
//        recipientUsers.add(user);
//        String from = USER_NAME;
//        String password = PASSWORD;
//
//        sendFromGMail(from, password, recipientUsers, subject, body);
//    }

    private static void sendFromGMail(String from, String password,
                                      Collection<User> to, String subject,
                                      String body) {
        Properties props = new Properties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.size()];

            // To get the array of addresses
            int i = 0;
            for(User recipientUser:to) {
                toAddress[i] = new InternetAddress(recipientUser.getMailAddress());
                i++;
            }

            for(i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}
