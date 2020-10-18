package com.kharkiv.movienight.transport.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EmailBuyTicketDto extends EmailTemplateDto {

    private String userName;
    private String eventName;
    private Instant eventDate;
    private String ticketPath;

    public EmailBuyTicketDto(
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
