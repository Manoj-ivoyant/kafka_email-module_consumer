package com.ivoyant.kafkaemailevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

/**
 * Main application class for the Kafka Email Module Application.
 * This class initializes the application and listens to a JMS queue for incoming messages.
 */
@SpringBootApplication
public class KafkaEmailModuleApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaEmailModuleApplication.class);

    /**
     * Main method to start the Kafka Email Module Application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(KafkaEmailModuleApplication.class, args);
    }

    /**
     * JMS Listener method that consumes messages from the specified destination queue.
     * This method is responsible for processing received messages.
     *
     * @param message The message received from the JMS queue as a String.
     */
    @JmsListener(destination = "${spring.jms.queue-name}")
    public void consumeMsg(String message) {
        LOGGER.info("Message Received: {}", message);
    }
}
