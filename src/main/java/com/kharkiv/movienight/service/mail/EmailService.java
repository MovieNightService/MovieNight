package com.kharkiv.movienight.service.mail;

import com.kharkiv.movienight.transport.dto.mail.EmailTemplateDto;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void sendMessage(EmailTemplateDto dto);

    @Async
    void sendEmailByTemplate(EmailTemplateDto dto);
}
