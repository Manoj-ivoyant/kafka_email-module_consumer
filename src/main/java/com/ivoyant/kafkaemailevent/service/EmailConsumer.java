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

/**
 * Service class responsible for consuming Kafka messages and sending emails.
 */
@Service
public class EmailConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    private final JavaMailSender emailSender;

    /**
     * Constructor to initialize the EmailConsumer with a JavaMailSender.
     *
     * @param emailSender The JavaMailSender used for sending emails.
     */
    @Autowired
    public EmailConsumer(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * Listens to a Kafka topic for EmailDto messages and sends emails.
     *
     * @param emailDto The EmailDto received from Kafka to be sent as an email.
     */
    @KafkaListener(topics = "${spring.kafka.listener.topics.email-event}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(EmailDto emailDto) {
        LOGGER.info("Received message from Kafka topic: {}", emailDto);

        // Sending a simple email
        sendSimpleEmail(emailDto);
    }

    /**
     * Listens to a Kafka topic for EmailAttachDto messages and sends emails with attachments.
     *
     * @param emailAttachDto The EmailAttachDto received from Kafka to be sent as an email with an attachment.
     */
    @KafkaListener(topics = "${spring.kafka.listener.topics.email-attach-event}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessageWithAttachment(EmailAttachDto emailAttachDto) {
        LOGGER.info("Received message with attachment from Kafka topic: {}", emailAttachDto);
        try {
            sendEmailWithAttachment(emailAttachDto);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email with attachment due to: {}", e.getMessage());
        }
    }

    /**
     * Sends an email with an attachment using the provided EmailAttachDto.
     *
     * @param emailAttachDto The EmailAttachDto containing details for the email with an attachment.
     * @throws MessagingException If an error occurs while creating or sending the email message.
     */
    private void sendEmailWithAttachment(EmailAttachDto emailAttachDto) throws MessagingException {
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
            LOGGER.info("Email with attachment sent successfully to {}", emailAttachDto.getToEmail());
        } catch (Exception e) {
            LOGGER.error("Failed to send email with attachment: {}", e.getMessage());
        }
    }

    /**
     * Sends a simple email using the provided EmailDto.
     *
     * @param emailDto The EmailDto containing details for the simple email.
     */
    private void sendSimpleEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDto.getFromEmail());
        message.setTo(emailDto.getToEmail());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getBody());

        try {
            emailSender.send(message);
            LOGGER.info("Simple email sent successfully to {}", emailDto.getToEmail());
        } catch (Exception e) {
            LOGGER.error("Failed to send simple email: {}", e.getMessage());
        }
    }
}
