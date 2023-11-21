package com.ivoyant.kafkaemailevent.service;

import com.ivoyant.kafkaemailevent.dto.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "email-event", groupId = "group2")
    public void consumeMessage(EmailDto emailDto) {
        LOGGER.info("message from topic {}", emailDto.toString());

    }

}
