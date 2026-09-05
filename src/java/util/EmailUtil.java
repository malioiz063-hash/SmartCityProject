package util;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

    private static final String FROM_EMAIL =
            "smartcityportal131@gmail.com";

    private static final String APP_PASSWORD =
            "cfwo jvzy clyu eodf";

    private static Session getSession() {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");

props.put("mail.smtp.connectiontimeout", "5000");
props.put("mail.smtp.timeout", "5000");
props.put("mail.smtp.writetimeout", "5000");

        return Session.getInstance(
                props,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                FROM_EMAIL,
                                APP_PASSWORD.replace(" ", ""));
                    }
                });
    }

    // ==========================
    // Welcome Email
    // ==========================

    public static void sendWelcomeEmail(
            String toEmail,
            String fullName) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(
                new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Welcome to Smart City Portal");

        String loginLink =
                "https://smartcityproject-production.up.railway.app/login.html";

        String htmlMessage =
                "<html><body style='font-family:Arial;background:#f4f6f9;padding:20px;'>"
                + "<div style='max-width:600px;margin:auto;background:white;padding:30px;border-radius:10px;'>"
                + "<h2 style='color:#0066cc;'>Welcome to Smart City Portal</h2>"
                + "<p>Dear <b>" + fullName + "</b>,</p>"
                + "<p>Your account has been created successfully.</p>"
                + "<p>You can now submit complaints, request services, book appointments and track requests online.</p>"
                + "<div style='text-align:center;margin:25px;'>"
                + "<a href='" + loginLink + "' "
                + "style='background:#0066cc;color:white;padding:12px 25px;text-decoration:none;border-radius:5px;'>"
                + "Login to Portal"
                + "</a>"
                + "</div>"
                + "<hr>"
                + "<p>Regards,<br><b>Smart City Management Team</b></p>"
                + "</div></body></html>";

        message.setContent(
                htmlMessage,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Login Email
    // ==========================

    public static void sendLoginEmail(
            String toEmail,
            String fullName) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(
                new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Login Alert - Smart City Portal");

        String htmlMessage =
                "<html><body style='font-family:Arial;background:#f4f6f9;padding:20px;'>"
                + "<div style='max-width:600px;margin:auto;background:white;padding:30px;border-radius:10px;'>"
                + "<h2 style='color:#28a745;'>Successful Login</h2>"
                + "<p>Dear <b>" + fullName + "</b>,</p>"
                + "<p>Your Smart City Portal account was successfully logged in.</p>"
                + "<p>If this login was not performed by you, please change your password immediately.</p>"
                + "<hr>"
                + "<p>Regards,<br><b>Smart City Security Team</b></p>"
                + "</div></body></html>";

        message.setContent(
                htmlMessage,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Complaint Submitted
    // ==========================

    public static void sendComplaintSubmittedEmail(
            String toEmail,
            String title) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Complaint Submitted Successfully");

        String html =
                "<html><body>"
                + "<h2>Complaint Received</h2>"
                + "<p>Your complaint has been submitted successfully.</p>"
                + "<p><b>Complaint:</b> " + title + "</p>"
                + "<p>Status: Pending</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Complaint Status Update
    // ==========================

    public static void sendComplaintStatusEmail(
            String toEmail,
            String complaintTitle,
            String status) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Complaint Status Updated");

        String html =
                "<html><body>"
                + "<h2>Complaint Update</h2>"
                + "<p>Your complaint status has been updated.</p>"
                + "<p><b>Complaint:</b> " + complaintTitle + "</p>"
                + "<p><b>Status:</b> " + status + "</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Appointment Booked
    // ==========================

    public static void sendAppointmentEmail(
            String toEmail,
            String department,
            String date,
            String time) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Appointment Booked Successfully");

        String html =
                "<html><body>"
                + "<h2>Appointment Booked</h2>"
                + "<p>Your appointment request has been submitted.</p>"
                + "<p><b>Department:</b> " + department + "</p>"
                + "<p><b>Date:</b> " + date + "</p>"
                + "<p><b>Time:</b> " + time + "</p>"
                + "<p>Status: Pending</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Appointment Status Update
    // ==========================

    public static void sendAppointmentStatusEmail(
            String toEmail,
            String department,
            String date,
            String time,
            String status) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Appointment Status Updated");

        String html =
                "<html><body>"
                + "<h2>Appointment Update</h2>"
                + "<p><b>Department:</b> " + department + "</p>"
                + "<p><b>Date:</b> " + date + "</p>"
                + "<p><b>Time:</b> " + time + "</p>"
                + "<p><b>Status:</b> " + status + "</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Service Request Submitted
    // ==========================

    public static void sendServiceRequestEmail(
            String toEmail,
            String serviceType,
            String location) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Service Request Submitted");

        String html =
                "<html><body>"
                + "<h2>Service Request Received</h2>"
                + "<p><b>Service:</b> " + serviceType + "</p>"
                + "<p><b>Location:</b> " + location + "</p>"
                + "<p>Status: Pending</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }

    // ==========================
    // Service Status Update
    // ==========================

    public static void sendServiceStatusEmail(
            String toEmail,
            String serviceType,
            String location,
            String status) throws Exception {

        MimeMessage message =
                new MimeMessage(getSession());

        message.setFrom(new InternetAddress(FROM_EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Service Request Status Updated");

        String html =
                "<html><body>"
                + "<h2>Service Request Update</h2>"
                + "<p><b>Service:</b> " + serviceType + "</p>"
                + "<p><b>Location:</b> " + location + "</p>"
                + "<p><b>Status:</b> " + status + "</p>"
                + "</body></html>";

        message.setContent(
                html,
                "text/html; charset=utf-8");

        Transport.send(message);
    }
    
    // ==========================
// Emergency Alert To Admin
// ==========================

public static void sendEmergencyAlertToAdmin(
        String incidentType,
        String location,
        String description,
        String severity) throws Exception {

    MimeMessage message =
            new MimeMessage(getSession());

    message.setFrom(
            new InternetAddress(FROM_EMAIL));

    message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse("smartcityportal131@gmail.com"));

    message.setSubject(
            "🚨 New Emergency Alert Received");

    String html =
            "<html><body>"
            + "<h2 style='color:red;'>Emergency Alert</h2>"

            + "<p><b>Incident Type:</b> "
            + incidentType
            + "</p>"

            + "<p><b>Location:</b> "
            + location
            + "</p>"

            + "<p><b>Severity:</b> "
            + severity
            + "</p>"

            + "<p><b>Description:</b><br>"
            + description
            + "</p>"

            + "<hr>"

            + "<p>Please check Smart City Admin Portal immediately.</p>"

            + "</body></html>";

    message.setContent(
            html,
            "text/html; charset=utf-8");

    Transport.send(message);
}

// ==========================
// Emergency Report Submitted
// ==========================

public static void sendEmergencyReportSubmittedEmail(
        String toEmail,
        String incidentType,
        String location) throws Exception {

    MimeMessage message =
            new MimeMessage(getSession());

    message.setFrom(
            new InternetAddress(FROM_EMAIL));

    message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(toEmail));

    message.setSubject(
            "Emergency Report Submitted Successfully");

    String html =
            "<html><body style='font-family:Arial;'>"

            + "<h2 style='color:green;'>Emergency Report Received</h2>"

            + "<p>Your emergency report has been submitted successfully.</p>"

            + "<p><b>Incident Type:</b> "
            + incidentType
            + "</p>"

            + "<p><b>Location:</b> "
            + location
            + "</p>"

            + "<p><b>Status:</b> Pending</p>"

            + "<hr>"

            + "<p>Our emergency response team has been notified.</p>"

            + "<p>Thank you for using Smart City Portal.</p>"

            + "</body></html>";

    message.setContent(
            html,
            "text/html; charset=utf-8");

    Transport.send(message);
}
}