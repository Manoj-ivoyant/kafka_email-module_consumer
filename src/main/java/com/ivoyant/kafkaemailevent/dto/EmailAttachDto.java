package com.ivoyant.kafkaemailevent.dto;

import lombok.Data;

@Data
public class EmailAttachDto {
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
    private String attachment;
}
