package com.kharkiv.movienight.transport.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
public class EmailTemplateDto {

    private String recipient;

    private String subject;

    private String template;
}
