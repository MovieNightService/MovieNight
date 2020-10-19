package com.kharkiv.movienight.transport.dto.mail;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EmailTicketDto extends EmailTemplateDto {

    private String userName;
    private String eventName;
    private Instant eventDate;
    private String ticketPath;

    public EmailTicketDto(
            String userName,
            String eventName,
            Instant eventDate,
            String ticketPath,
            String recipient,
            String subject,
            String template
    ) {
        super(recipient, subject, template);
        this.userName = userName;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.ticketPath = ticketPath;
    }
}
