package com.ivoyant.kafkaemailevent.service;

import com.ivoyant.kafkaemailevent.dto.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
