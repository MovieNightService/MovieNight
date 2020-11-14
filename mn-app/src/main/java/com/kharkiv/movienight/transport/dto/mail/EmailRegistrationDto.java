package com.kharkiv.movienight.transport.dto.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRegistrationDto extends EmailTemplateDto {

    private String name;

    public EmailRegistrationDto(String name, String recipient, String subject, String template) {
        super(recipient, subject, template);
        this.name = name;
    }
}
