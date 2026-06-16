package com.example.payroll_service.service.impl;

import com.example.payroll_service.payload.req.EmailReq;
import com.example.payroll_service.payload.res.GlobalRes;
import com.example.payroll_service.service.LayananEmailService;
import com.example.payroll_service.utility.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class LayananEmailServiceImpl implements LayananEmailService {

    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${smtp.port}")
    private String smtpPort;

    @Value("${smtp.username}")
    private String smtpUsername;

    @Value("${smtp.password}")
    private String smtpPassword;

    @Override
    public GlobalRes<String> kirimEmail(EmailReq req) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUsername, smtpPassword);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpUsername));
            message.setRecipients(MimeMessage.RecipientType.TO,
                    InternetAddress.parse(req.getTo()));
            message.setSubject(req.getSubject());
            message.setText(req.getBody());

            Transport.send(message);

            return new GlobalRes<>(true, Message.EMAIL_SUCCESS, null);
        } catch (Exception e) {
            return new GlobalRes<>(false, Message.EMAIL_FAILED
                    + ": " + e.getMessage(), null);
        }
    }
}