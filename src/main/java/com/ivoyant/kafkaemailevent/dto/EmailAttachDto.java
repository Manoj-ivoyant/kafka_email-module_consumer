package com.ivoyant.kafkaemailevent.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an email with an attachment.
 * This class encapsulates the details of an email message with an attachment.
 */
@Data
public class EmailAttachDto {

    /**
     * The sender's email address.
     */
    private String fromEmail;

    /**
     * The recipient's email address.
     */
    private String toEmail;

    /**
     * The subject of the email.
     */
    private String subject;

    /**
     * The body content of the email.
     */
    private String body;

    /**
     * The file path or location of the attachment.
     */
    private String attachment;

    /**
     * Retrieves the sender's email address.
     *
     * @return A String representing the sender's email address.
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Sets the sender's email address.
     *
     * @param fromEmail The sender's email address to be set.
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * Retrieves the recipient's email address.
     *
     * @return A String representing the recipient's email address.
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * Sets the recipient's email address.
     *
     * @param toEmail The recipient's email address to be set.
     */
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    /**
     * Retrieves the subject of the email.
     *
     * @return A String representing the subject of the email.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     *
     * @param subject The subject of the email to be set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Retrieves the body content of the email.
     *
     * @return A String representing the body content of the email.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body content of the email.
     *
     * @param body The body content of the email to be set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Retrieves the file path or location of the attachment.
     *
     * @return A String representing the attachment's file path or location.
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * Sets the file path or location of the attachment.
     *
     * @param attachment The attachment's file path or location to be set.
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
