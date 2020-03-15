package org.enduser.service.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.enduser.service.exception.EndUserException;
import org.enduser.service.model.AdminUser;
import org.enduser.service.model.Car;
import org.enduser.service.model.CarTransaction;
import org.enduser.service.model.Comment;
import org.enduser.service.model.CustomedTour;
import org.enduser.service.model.LandingCar;
import org.enduser.service.model.LandingTour;
import org.enduser.service.model.Tour;
import org.enduser.service.model.TourOperator;
import org.enduser.service.model.TourTransaction;
import org.enduser.service.model.Tourist;
import org.enduser.service.model.TouristGroup;
import org.enduser.service.model.util.RefundObject;
import org.enduser.service.model.util.ResetPassword;
import org.enduser.service.services.impl.TourOperatorServiceImpl;
import org.enduser.service.services.impl.TourServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMailUtil {
    private static final Logger logger = Logger.getLogger(SendMailUtil.class);

    private TourServiceImpl tourServiceImpl;
    private TourOperatorServiceImpl tourOperatorServiceImpl;

    // Dev/(Prod, Test) environment flagf
    private final boolean isDevEnv = false;
    private String prodFrom = "no-reply@touranb.com";
    private String devFrom = "ethagencyconnection@gmail.com";

    // TEST env (solxget@yahoo.com)
    /*
     * static final String SMTP_USERNAME = "AKIAIGO5M2ZQFKRL4VRA"; static final String SMTP_PASSWORD
     * = "AnqeMfQgeuSuMOizY0YaP384VE5MQp8FEEeIy7as6hXB";
     */

    // PROD env (ethiopediait@gmail.com)
    static final String SMTP_USERNAME = "AKIAJ2TSA3QQBLZEA2ZQ";
    static final String SMTP_PASSWORD = "AtmJwm85Qu27j379h3xM0GLqhNdwQxeC1F/K9gsvlQaf";

    // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because
    // we will use
    // STARTTLS to encrypt the connection.
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    static final int PORT = 25;
    private final String baseUrl = "http://localhost:7001/enduser/service/";

    // Update for prod

    @Autowired
    public void setTourServiceImpl(TourServiceImpl tourServiceImpl) { // why the fuck this is not//
                                                                      // working???
        this.tourServiceImpl = tourServiceImpl;
    }

    @Autowired
    public void setTourOperatorServiceImpl(TourOperatorServiceImpl tourOperatorServiceImpl) {
        this.tourOperatorServiceImpl = tourOperatorServiceImpl;
    }

    // For local/dev environment
    public Session prepareEmailConnection() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.stmp.user", "username");

        // To use TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.password", "password");
        // To use SSL
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ethagencyconnection", "Solx!123456");
            }
        });
    }

    // For Production and Test Environments
    public Session prepareConnection() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", PORT);

        // Set properties indicating that we want to use STARTTLS to encrypt the connection.
        // The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        return Session.getDefaultInstance(props);
    }

    @SuppressWarnings("static-access")
    public void regestrationSuccessMail(TouristGroup touristGroup) {
        logger.debug("entered to RegestrationSuccessMail method");

        Session session = null;
        String from = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = touristGroup.getEmailAddress();
        String subject = "Touranb: new group created";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(groupRegistrationMessage(touristGroup));
            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending RegestrationSuccessMail ", exception);
            throw new EndUserException(exception);
        }
    }

    @SuppressWarnings("static-access")
    public void regestrationSuccessMail(Tourist tourist, String activationCode) {
        logger.debug("entered to RegestrationSuccessMail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = tourist.getEmailAddress();
        String subject = "Touranb: new account created";
        MimeMessage msg = new MimeMessage(session);
        Transport transport = null;
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setContent(turistRegistrationMessage(tourist, activationCode), "text/plain");
            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception ex) {
            logger.error("exception happend when sending RegestrationSuccessMail ", ex);
        }
    }

    public void regestrationSuccessMail(AdminUser adminUser, String activationCode) {
        logger.debug("entered to RegestrationSuccessMail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = adminUser.getEmailAddress();
        String subject = "Touranb: new account created";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(turistRegistrationMessage(adminUser, activationCode));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending RegestrationSuccessMail ", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void bookTourEmail(TourTransaction transaction) {
        logger.debug("entered to bookTourEmail method");
        try{
            bookTourCustomerEmail(transaction);
            bookTourSupplierEmail(transaction);
        }catch(Exception e){
            logger.error("exception happend when sending bookTourEmail ", e);
            throw new EndUserException(e);
        }
    }
    
    public void bookTourSupplierEmail(TourTransaction transaction) {
        logger.debug("entered to bookTourSupplierEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = transaction.getOperatorEmailAddress();
        String subject = "Touranb: A Tour is booked";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(bookTourSupplierMessage(transaction));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending bookTourSupplierEmail ", exception);
            throw new EndUserException(exception);
        }
    }

    public void bookTourCustomerEmail(TourTransaction transaction) {
        logger.debug("entered to bookTourCustomerEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = transaction.getSuppliedEmailAddress();
        String subject = "Touranb: Your tour is booked";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(bookTourMessage(transaction));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending bookTourCustomerEmail ", exception);
            throw new EndUserException(exception);
        }
    }

    public void bookCarEmail(CarTransaction transaction) {
        logger.debug("entered to bookCarEmail method");
        try{
            bookCarCustomerEmail(transaction);
            bookCarSupplierEmail(transaction);
        }catch(Exception e){
            logger.error("exception happend when sending bookCarEmail ", e);
            throw new EndUserException(e);
        }
    }
    
    public void bookCarCustomerEmail(CarTransaction transaction) {
        logger.debug("entered to bookCarCustomerEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = transaction.getSuppliedEmailAddress();
        String subject = "Touranb: Your Car is booked";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(bookCarCustomerMessage(transaction));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending bookCarCustomerEmail ", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void bookCarSupplierEmail(CarTransaction transaction) {
        logger.debug("entered to bookCarEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = transaction.getOperatorEmailAddress();
        String subject = "Touranb: Your Car is booked";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(bookCarSupplierMessage(transaction));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending bookTourEmail ", exception);
            throw new EndUserException(exception);
        }
    }

    public void sendPasswordResetEmail(URL url, String emailAddress) {
        logger.debug("entered to sendPasswordResetEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: Password Reset";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(sendPasswordResetMessage(url, emailAddress));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending bookTourEmail ", exception);
            throw new EndUserException(exception);
        }
    }

    public void refundTouristEmail(RefundObject refundObject) {
        logger.debug("entered to refundTouristEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = refundObject.getTouristEmailAddress();
        String subject = "Touranb: Refund is processed";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(refundTouristMessage(refundObject));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending RegestrationSuccessMail ", exception);
            throw new EndUserException(exception);
        }
    }

    public void tourOperatorRegistrationEmail(TourOperator tourOperator) {
        logger.debug("entered to tourOperatorRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = tourOperator.getEmailAddress();
        String subject = "Touranb: Travel Agent successfully registered";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            msg.setText(tourOperatorRegistrationMessage(tourOperator));
            msg.setContent(tourOperatorRegistrationMessage(tourOperator), "text/plain");

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourOperatorRegistrationEmail", exception);
            throw new EndUserException(exception);
        } finally {
            if (transport.isConnected())
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new EndUserException(e);
                }
        }
    }

    public void newGroupMemberEmail(String newMember, List<String> groupMembersList) {
        logger.debug("entered to newGroupMemberEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String subject = "Touranb: New Member has joined the group";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            for (String str : groupMembersList) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
            }
            msg.setSubject(subject);
            msg.setText(addMemberToGroup(newMember));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending newGroupMemberEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void carRegistrationEmail(Car car, String emailAddress) {
        logger.debug("entered to carRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: You have registered a new Car";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(registerCarMessage(car));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending carRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void carRegistrationEmail(LandingCar car, String emailAddress) {
        logger.debug("entered to carRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: You have registered a new Car";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(registerCarMessage(car));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending carRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void tourRegistrationEmail(LandingTour tour, String emailAddress) {
        logger.debug("entered to tourRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: You have registered a new Tour";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(registerTourMessage(tour));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void tourRegistrationNotification(String operatorId, String operatorName, String tourId) {
        logger.debug("entered to tourRegistrationNotification method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }

        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Tour Registered";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(tourRegistrationNotificationMessage(operatorId, operatorName, tourId));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationNotification", exception);
            throw new EndUserException(exception);
        }
    }

    public void carRegistrationEmailNotification(String operatorId, String operatorName, String carId) {
        logger.debug("entered to tourRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }

        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Car Registered";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(carRegistrationNotificationMessage(operatorId, operatorName, carId));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void tourRegistrationEmail(Tour tour, String emailAddress) {
        logger.debug("entered to tourRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: You have registered a new Tour";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(registerTourMessage(tour));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void receiveCommentEmail(Comment comment) {
        logger.debug("entered to tourRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Customer Comment/Question";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(receiveCommentMessage(comment));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void sendPasswordResetEmail(ResetPassword resetPassword) {
        logger.debug("entered to tourRegistrationEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = resetPassword.getEmailAddress();
        String subject = "Touranb: Password Changed";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(passwordReset(resetPassword));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void accountInitated(String country, String emailAddress) {
        logger.debug("entered to accountInitated method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Operator account created";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(accountInitatedMessage(country, emailAddress));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void ApproveAccountEmail(String emailAddress) {
        logger.debug("entered to ApproveAccountEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: Account Approved";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(ApproveAccountMessage(emailAddress));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void EnrollmentInitiated(String emailAddress) {
        logger.debug("entered to enrollmentInitated method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = emailAddress;
        String subject = "Touranb: Operator enrollment initiated";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(EnrollmentInitiatedMessage(emailAddress));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void EnrollmentInitiated(String country, String emailAddress) {
        logger.debug("entered to enrollmentInitated method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Operator Initiated registration";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(EnrollmentInitiatedMessage(country, emailAddress));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending tourRegistrationEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void TourEditEmail(String country, String operatorId, String tourId) {
        logger.debug("entered to TourEditEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Tour Edited";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(TourEditEmaildMessage(country, operatorId, tourId));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending TourEditEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void CarEditEmail(String country, String operatorId, String carId) {
        logger.debug("entered to CarEditEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Car Edited";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(CatEditEmaildMessage(country, operatorId, carId));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending CarEditEmail", exception);
            throw new EndUserException(exception);
        }
    }

    public void deleteTourEmail(String country, String operatorId, String tourId, String tableName){
        logger.debug("entered to deleteTourEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Tour Deleted";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(deleteTourEmailMessage(country, operatorId, tourId, tableName));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending deleteTourEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void deleteCarEmail(String country, String operatorId, String carId, String tableName){
        logger.debug("entered to deleteCarEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Car Deleted";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(deleteCarEmailMessage(country, operatorId, carId, tableName));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending deleteCarEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void customedTourRegistered(CustomedTour customedTour){
        logger.debug("entered to customedTourRegistered method");
        try{
            customedTourCustomerEmail(customedTour);
            customedTourCompanyEmail(customedTour);
        }catch(Exception e){
            logger.error("exception happend when sending customedTourRegistered ", e);
            throw new EndUserException(e);
        }
    }
    
    public void customedTourCustomerEmail(CustomedTour customedTour){
        logger.debug("entered to customedTourCustomerEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = customedTour.getEmailAddress();
        String subject = "Touranb: Customed Tour request submitted";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(customedTourCustomerEmailMessage(customedTour));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending customedTourCustomerEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public void customedTourCompanyEmail(CustomedTour customedTour){
        logger.debug("entered to customedTourCompanyEmail method");

        String from = null;
        Session session = null;
        if (isDevEnv) {
            session = prepareEmailConnection();
            from = devFrom;
        } else {
            session = prepareConnection();
            from = prodFrom;
        }
        String to = "ethagencyconnection@gmail.com";
        String subject = "Touranb: Customed Tour request submitted";
        Message msg = new MimeMessage(session);
        Transport transport = null;

        try {
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setText(customedTourCompanyEmailMessage(customedTour));

            if (isDevEnv) {
                transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", 465, "username", "password");
            } else {
                transport = session.getTransport();
                transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            }
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception exception) {
            logger.error("exception happend when sending customedTourCompanyEmail", exception);
            throw new EndUserException(exception);
        }
    }
    
    public String customedTourCustomerEmailMessage(CustomedTour customedTour){
        return "\n Thank you for submitting a request. Your Customed tour request details." 
                + "\n\n\t Country planning to visit : " + customedTour.getVisiting()
                + "\n\t Trip Start Date : " + (customedTour.getTourStartDate().toString()).substring(0, 10)
                + "\n\t Trip End Date : " + (customedTour.getTourEndDate().toString()).substring(0, 10)
                + "\n\t Number of travelers : " + customedTour.getTravellersCount()
                + "\n\t Trip Description : " + customedTour.getTourDescription()
                
                + "\n\n We will forward your request to the tour operators who operate on the ground and will get"
                + " back to you with multiple detailed itineraries within 24 hours "
                + "\n\n For any question you may have, please contact us using connection@touranb.com"
                + "\n\n Tour Africa and Beyond : Touranb Team";
        
    }
    
    public String customedTourCompanyEmailMessage(CustomedTour customedTour){
        return "\n Customer submitted a custom tour request. request details." 
                + "\n\n\t Cusomer email : " + customedTour.getEmailAddress()
                + "\n\t Country planning to visit : " + customedTour.getVisiting()
                + "\n\t Trip Start Date : " + (customedTour.getTourStartDate().toString()).substring(0, 10)
                + "\n\t Trip End Date : " + (customedTour.getTourEndDate().toString()).substring(0, 10)
                + "\n\t Number of travelers : " + customedTour.getTravellersCount()
                + "\n\t Trip Description : " + customedTour.getTourDescription()
                
                + "\n\n Get user a response within 24 hours"
                + "\n\n Tour Africa and Beyond : Touranb Team";
        
    }
    
    public String deleteTourEmailMessage(String country, String operatorId, String tourId, String tableName){
        return "\n\n Tour is deleted from " + tableName + " table" + "\n\t  Country : " + country + "\n\t  Operator Id : " + operatorId 
                + "\n\t  Tour Id : " + tourId;
    
    }
    
    public String deleteCarEmailMessage(String country, String operatorId, String carId, String tableName){
        return "\n\n Car is deleted from " + tableName + " table" + "\n\t  Country : " + country + "\n\t  Operator Id : " + operatorId 
                + "\n\t  Car Id : " + carId;
    
    }
    
    public String tourRegistrationNotificationMessage(String operatorId, String operatorName, String tourId){
        return "\n\n Tour is saved to LandingTour table " + "\n\t  Operator Id : " + operatorId + "\n\t  Operator Name : " + operatorName 
                + "\n\t  Tour Id : " + tourId;
    
    }
    
    public String carRegistrationNotificationMessage(String operatorId, String operatorName, String carId){
        return "\n\n Car is saved to LandingCar table " + "\n\t  Operator Id : " + operatorId + "\n\t  Operator Name : " + operatorName 
                + "\n\t  Car Id : " + carId;
    
    }
    
    public String TourEditEmaildMessage(String country, String operatorId, String tourId) {
        return "\n\n Tour is edited " + "\n\t  Country : " + country + "\n\t  Operator Id : " + operatorId + "\n\t  Tour Id : "
                + tourId;
    }

    public String CatEditEmaildMessage(String country, String operatorId, String carId) {
        return "\n\n Car is edited " + "\n\t  Country : " + country + "\n\t  Operator Id : " + operatorId + "\n\t  Tour Id : "
                + carId;
    }

    private String EnrollmentInitiatedMessage(String emailAddress) {
        return "\n\n Hi, \n\n\t Touranb account enrollment as a Tour Operator is initiated"
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String EnrollmentInitiatedMessage(String country, String emailAddress) {
        return "\n\n Operator initated registration" + "\n\n\t Country : " + country + "\n\t Account email : " + emailAddress
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String ApproveAccountMessage(String emailAddress) {
        return "\n\n\t We have reviewd the files you submitted and approved your account. Please login and finish the registration process."
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String accountInitatedMessage(String country, String emailAddress) {
        return "\n\n Tour Operator created account and finshied uploading files. Please review the files and activate account"
                + "\n\n\t Country : " + country + "\n\n\t Email Address : " + emailAddress;
    }

    public String sendPasswordResetMessage(URL url, String emailAddress) {
        return "\n We have received your password change request. This email contains the information that you need "
                + "to change your password. \n\n Login Name: " + emailAddress + "\n\n Click the link to reset your "
                + "passwrod:  " + url;
    }

    public String turistRegistrationMessage(Tourist tourist, String activationCode) {
        URL url = null;
        try {
            url = new URL(baseUrl + "user/activate");
        } catch (MalformedURLException e) {
            throw new EndUserException();
        }
        return "\n Welcome to Touranb. You have created a new account in our system. your login information is;"
                + "\n\n\t Email Address : " + tourist.getEmailAddress() + "\n\t Activation Code : " + activationCode
                + "\n\n Please go to this link " + url + " and click activate button. "
                + "It will take you to the login page where you can use your Email address and Password to login."
                + "\n If clicking the link don't work please copy and paste it in your browser's search tab"
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String turistRegistrationMessage(AdminUser adminUser, String activationCode) {
        URL url = null;
        try {
            url = new URL(baseUrl + "user/activate");
        } catch (MalformedURLException e) {
            throw new EndUserException();
        }
        return "\n Welcome to Touranb. You have created a new account in our system. your login information is;"
                + "\n\n\t Email Address : " + adminUser.getEmailAddress() + "\n\t Activation Code : " + activationCode
                + "\n\n Please go to this link " + url + " and click activate button. "
                + "It will take you to the login page where you can use your Email address and Password to login."
                + "\n If clicking the link don't work please copy and paste it in your browser's search tab"
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String groupRegistrationMessage(TouristGroup touristGroup) {
        return "\n Welcome to Touranb. You have created a new group. \n\t Group Name : "
                + touristGroup.getGroupName()
                + "\n\t Planned Trip Date : "
                + (touristGroup.getPlannedTripDate().toString()).substring(0, 10)
                + "\n\t Trip Duration : "
                + touristGroup.getTripDuration()
                + " days \n\t Tour Route : "
                + null
                // + (touristGroup.getTour()).getTourRoute() // Uncomment this line when Create
                // group form incorporates a tour
                + "\n\n The group you created will be avilable on search for others to join. You will receive an email notifications when someone joins the group. "
                + " You can also see the group members and other details by searching for your group"
                + "\n\n\t Tour prices vary based on group size and season of the year. Hence, We highly advise you to book a tour when all the group members confirm that they will be taking this tour for sure."
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String bookTourSupplierMessage(TourTransaction transaction) {  //Payment amount and phone # not working       
        String tripDate = transaction.getTripDate().toString();
        String groupSize = (transaction.getGroupSize() > 1)? ": people in this group " : ": person "; 
        String duration = (transaction.getTripDuration() > 1)?  transaction.getTripDuration() + " days " : " a day ";
        String airportPickUp = (transaction.isAirportPickUp()) ? "Please arrange Airport pick-up by communicating with customer about his/her/their arrival time via " 
        + transaction.getEmailAddress() : "";
        
        return " \n A Tour Package is bought by a customer "
                + "\n\n Tour Package details "
                + "\n\t Operator Name : " + transaction.getOperatorName()
                + "\n\t Tour Id : " + transaction.getTourId() + "\n\t Tour Type: " + transaction.getTourType() 
                + "\n\t Tour Price: " + transaction.getTourPrice()
                + "\n\t Tip in " + transaction.getTripCountry() + " Starting from " + tripDate.substring(0, 10) + " for " + duration
                + "\n\t Confirmation number : " + transaction.getConfirmationNumber() + " (" + transaction.getFirstName() + " " + transaction.getLastName() + ") \n\t "
                
                + "\n\n Traveler/s Summary: "
                + "\n  Travelers " + transaction.getGroupSize() + groupSize 
                + "\n\t Customer contact : " + transaction.getFirstName() + " " + transaction.getLastName()
                + "\n\t Email Address : " + transaction.getEmailAddress()
                + "\n\t Tour Price : " + transaction.getTourPrice() + " " + transaction.getCurrency() 

                + "\n\t Tour starts from " + transaction.getTourStartPlace() + " and ends " + transaction.getEndingPlace()
                + "\n " + airportPickUp
                + "\n\n\n Need More help "
                + "Call us at 1-906-231-4037. \n"
                + " For faster service, mention itinerary number " + transaction.getConfirmationNumber()
                + "\n\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String bookTourMessage(TourTransaction transaction) {  //Payment amount and phone # not working       
        String tripDate = transaction.getTripDate().toString();
        String groupSize = (transaction.getGroupSize() > 1)? ": Adults " : ": Adult "; 
        String duration = (transaction.getTripDuration() > 1)?  transaction.getTripDuration() + " days " : " a day ";
        String airportPickUp = (transaction.isAirportPickUp()) ? "Tour Operator will pick you from the Airport. please communicate with Operator via " 
        + transaction.getOperatorEmailAddress() + " or phone " + transaction.getOperatorPhone() + " to arrange airport pick-up" : "";
        double paymentAmount = transaction.getPaymentAmount()/100;
        
        return " \n Thank you for booking with Touranb!  Your reservation is booked and confirmed. "
                + "\n * E-ticket: This email can be used as an E-ticket. "
                + "\n\n Trip Overview "
                + "\n\t Confirmation number : " + transaction.getConfirmationNumber() + " (" + transaction.getFirstName() + " " + transaction.getLastName() + ") \n\t "
                + transaction.getTripCountry() + " " + tripDate.substring(0, 10) + " for " + duration
                + "\n\t " + transaction.getTourType() + " " + transaction.getTourRoute()
                + "\n\n Price Summary: "
                + "\n   Traveler " + transaction.getGroupSize() + groupSize + " " + paymentAmount + " " + transaction.getCurrency() 
                + "\n\t  Tour Price : " + transaction.getTourPrice()
                + "\n\t  Tax and Fees : " + transaction.getTax()
                + "\n\t  Trip Total : " + paymentAmount + " " + transaction.getCurrency() 
                + "\n\n Operator Details \n\t " + transaction.getOperatorName() + "\n\t Email " + transaction.getOperatorEmailAddress()
                + "\n\t Phone no " + transaction.getOperatorPhone()
                + "\n\t Tour starts from " + transaction.getTourStartPlace() + " and ends " + transaction.getEndingPlace()
                + "\n\n " + airportPickUp
                + "\n\n Additional fees : " + "The Tour Operator may charge additional fees for park enterance or other optional services." 
                + "\n\n Before you go : Remember to bring your Passport and/or government-issued photo ID for airport check-in and security."
                + "\n\n\n Need More help "
                + "Call us at 1-906-231-4037. \n"
                + " For faster service, mention itinerary number " + transaction.getConfirmationNumber()
                + "\n\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String bookCarCustomerMessage(CarTransaction transaction) {
        String duration = (transaction.getRentalDuration() > 1)?  transaction.getRentalDuration() + " days " : " a day ";
        String airportPickUp = (transaction.isAirportPickUp()) ? "Tour Operator will pick you from the Airport. please communicate with Operator via " 
        + transaction.getOperatorEmailAddress() + " or phone " + transaction.getOperatorPhone() + " to arrange airport pick-up" : "";
        double paymentAmount = transaction.getPaymentAmount()/100;
        String withDriver = transaction.isWithDriver()? " with driver " : "";
        String returnAddress = transaction.isReturnAddressFlag()? " same as pick-up address " : transaction.getReturnAddress();
        
        return " \n Thank you for booking with Touranb!  Your reservation is booked and confirmed. "
            + "\n * E-ticket: This email can be used as an E-ticket. "
            + "\n\n Rental Overview "
            + "\n\t Confirmation number : " + transaction.getConfirmationNumber() + " (" + transaction.getFirstName() + " " + transaction.getLastName() + ") \n\t "
            + transaction.getCarType() + " " + transaction.getCarModel() + withDriver + " \n\t "
            + transaction.getRentalCountry() + ": Pick-up date " + (transaction.getPickupDate().toString()).substring(0, 11) + transaction.getPickUpTime()
            + ", Return date " + (transaction.getReturnDate().toString()).substring(0, 11) + transaction.getDropOffTime()
            + "\n\t pick-up address: " + transaction.getPickUpAddress() + " Return Address: " + returnAddress
            + "\n\n Price Summary: "
            + "\n Renter " + transaction.getFirstName()+ " " + transaction.getLastName() 
            + "\n\t  Daily Price : " + transaction.getDailyPrice() + " " + transaction.getCurrency() 
            + "\n\t  Rental Price : " + transaction.getTotalPrice() 
            + "\n\t  Tax and Fees : " + transaction.getTax()
            + "\n\t  Trip Total : " + paymentAmount + " " + transaction.getCurrency() 
            + "\n\n Rental Company Details \n\t " + transaction.getOperatorName()
            + "\n\t Email " + transaction.getOperatorEmailAddress()
            + "\n\t Phone no " + transaction.getOperatorPhone()
            + "\n\t Pickup Address " + transaction.getPickUpAddress() + " and return to " + returnAddress
            + "\n\t " + airportPickUp
            + "\n\n Before you go : Remember to bring your Passport and/or government-issued photo ID for airport check-in and security."
            + "\n\n\n Need More help "
            + "Call us at 1-906-231-4037. \n"
            + " For faster service, mention itinerary number " + transaction.getConfirmationNumber()
            + "\n\n\n Tour Africa and Beyond : Touranb Team";
    }

    
    public String bookCarSupplierMessage(CarTransaction transaction) {

        String duration = (transaction.getRentalDuration() > 1)?  transaction.getRentalDuration() + " days " : " a day ";
        String airportPickUp = (transaction.isAirportPickUp()) ? "Please arrange Airport pick-up by communicating with customer about his/her/their arrival time via " 
                + transaction.getEmailAddress() : "";
        String withDriver = transaction.isWithDriver()? " with driver " : "";
        String returnAddress = transaction.isReturnAddressFlag()? " same as pick-up address " : transaction.getReturnAddress();
        
        return " \n A Car is booked by a customer "
            + "\n\n Rental Overview "

            + "\n\t Confirmation number : " + transaction.getConfirmationNumber() + " (" + transaction.getFirstName() + " " + transaction.getLastName() + ") \n\t "
            + transaction.getRentalCountry() + ": Pick-up date " + (transaction.getPickupDate().toString()).substring(0, 11) + transaction.getPickUpTime()
            + ", Return date " + (transaction.getReturnDate().toString()).substring(0, 11) + transaction.getDropOffTime()
             + "\n\t Car Id : " + transaction.getCarId() 
            + "\n\t Car Type: " + transaction.getCarType() + " " + transaction.getCarModel() + " " + withDriver 
            + "\n\t Daily price: " + transaction.getDailyPrice() + " for " + duration
            + "\n\t pick-up address: " + transaction.getPickUpAddress() + " Return Address: " + returnAddress
            + "\n\n Renter " + transaction.getFirstName()+ " " + transaction.getLastName() 
            + "\n\t  Daily Price : " + transaction.getDailyPrice() + " " + transaction.getCurrency() 
            + "\n\n\t " + airportPickUp
            + "\n\n\n Need More help "
            + "Call us at 1-906-231-4037. \n"
            + " For faster service, mention itinerary number " + transaction.getConfirmationNumber()
            + "\n\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String refundTouristMessage(RefundObject refundObject) {
        return " here it goes the email Message for the refunding  refund amount of " + refundObject.getAmount()
                + " bla bla bla ";
    }

    public String tourOperatorRegistrationMessage(TourOperator tourOperator) {
        return "\nWellcome to Touranb " + tourOperator.getOperatorName()
                + "\n\n\tYou have successfully registered your company to our system. We are excited to work with you. \n\n"
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    public String addMemberToGroup(String newMember) {

        return "\n A new user " + newMember + " has been added to the group" + "\n\n  Tour Africa and Beyond : Touranb Team";
    }

    private String registerCarMessage(Car car) {
        String withDriver;
        if (car.isWithDriver())
            withDriver = "Only With Driver";
        else
            withDriver = "";
        return " Hi, \n\n\b\bYou have successfully registered a new car.\n\t Car Type : " + car.getCarType()
                + "\n\t Car Model : " + car.getCarModel() + "\n\t Daily Price : $" + car.getDailyPrice()
                + "\n\t Number of Cars : " + car.getCarCount() + "\n\t " + withDriver
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String registerCarMessage(LandingCar car) {
        String withDriver;
        if (car.isWithDriver())
            withDriver = "Only With Driver";
        else
            withDriver = "";
        return " Hi, \n\n\b\bYou have successfully registered a new car.\n\t Car Type : " + car.getCarType()
                + "\n\t Car Model : " + car.getCarModel() + "\n\t Daily Price : $" + car.getDailyPrice()
                + "\n\t Number of Cars : " + car.getCarCount() + "\n\t " + withDriver
                + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String registerTourMessage(LandingTour tour) {
        return " Hi, \n\n\b\bYou have successfully registered a new tour. \n\n\t Tour Id : " + tour.getTourId()
                + "\n\t Tour Type : " + tour.getTourType() + "\n\t Tour Route : " + tour.getTourRoute() + "\n\t Tour Length : "
                + tour.getTourDuration() + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String registerTourMessage(Tour tour) {
        return " Hi, \n\n\b\bYou have successfully registered a new tour. \n\n\t Tour Id : " + tour.getTourId()
                + "\n\t Tour Type : " + tour.getTourType() + "\n\t Tour Route : " + tour.getTourRoute() + "\n\t Tour Length : "
                + tour.getTourDuration() + "\n\n Tour Africa and Beyond : Touranb Team";
    }

    private String receiveCommentMessage(Comment comment) {
        return "\n\t Customer Type : " + comment.getUserType() + "\n\t Customer Name : " + comment.getName()
                + "\n\t Customer email : " + comment.getEmailAddress() + "\n\t Message : " + comment.getMessage();
    }

    private String passwordReset(ResetPassword resetPassword) {
        return "\n You have changed your password. your new password is \n\n\t New Password : " + resetPassword.getNewPassword()
                + "\n\n Tour Africa and Beyond : Tour Africa and Beyond : Touranb Team";

    }

}
