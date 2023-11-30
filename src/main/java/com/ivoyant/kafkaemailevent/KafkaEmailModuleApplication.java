package com.ivoyant.kafkaemailevent;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import jakarta.mail.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
public class KafkaEmailModuleApplication {
    private final Logger LOGGER= LoggerFactory.getLogger(KafkaEmailModuleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaEmailModuleApplication.class, args);
    }
    @JmsListener(destination = "${spring.jms.queue-name}")
    public void consumeMsg(String message) {
        LOGGER.info("Message Received: {}", message);
    }


}
