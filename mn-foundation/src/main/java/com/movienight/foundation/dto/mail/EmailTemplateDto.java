package com.movienight.foundation.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailTemplateDto {

    private String recipient;

    private String subject;

    private String template;
}
