package com.ivoyant.kafkaemailevent.service;

import com.ivoyant.kafkaemailevent.dto.EmailAttachDto;
import com.ivoyant.kafkaemailevent.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    private final JavaMailSender emailSender;

    @Autowired
    public EmailConsumer(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @KafkaListener(topics = "email-event", groupId = "group2")
    public void consumeMessage(EmailDto emailDto) {
        LOGGER.info("Message from topic: {}", emailDto.toString());

        // Sending email
        sendEmail(emailDto);
    }

    @KafkaListener(topics = "email-attach-event", groupId = "group2")
    public void consumeMessageWithAttachment(EmailAttachDto emailAttachDto) {
        LOGGER.info("Message from the topic:{}", emailAttachDto.toString());
        try {
            sendEmailAttachment(emailAttachDto);
        } catch (MessagingException e) {
            LOGGER.error("caused by {}", e.getMessage());
        }
    }

    private void sendEmailAttachment(EmailAttachDto emailAttachDto) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(emailAttachDto.getFromEmail());
        mimeMessageHelper.setTo(emailAttachDto.getToEmail());
        mimeMessageHelper.setText(emailAttachDto.getBody());
        mimeMessageHelper.setSubject(emailAttachDto.getSubject());

        FileSystemResource fileSystem = new FileSystemResource(new File(emailAttachDto.getAttachment()));
        mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

        try {
            emailSender.send(mimeMessage);
            LOGGER.info("Email sent successfully to {}", emailAttachDto.getToEmail());
        } catch (Exception e) {
            LOGGER.error("Error sending email: {}", e.getMessage());

        }


    }

    private void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDto.getFromEmail());
        message.setTo(emailDto.getToEmail());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getBody());

        try {
            emailSender.send(message);
            LOGGER.info("Email sent successfully to {}", emailDto.getToEmail());
        } catch (Exception e) {
            LOGGER.error("Error sending email: {}", e.getMessage());
        }
    }
}
