package com.movienight.app.service.mail;

import com.movienight.foundation.dto.mail.EmailTemplateDto;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    void sendMessage(EmailTemplateDto dto);

    @Async
    void sendMessageByTemplate(EmailTemplateDto dto);
}
