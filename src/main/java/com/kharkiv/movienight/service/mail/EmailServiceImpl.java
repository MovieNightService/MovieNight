package com.kharkiv.movienight.service.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kharkiv.movienight.transport.dto.mail.EmailTicketDto;
import com.kharkiv.movienight.transport.dto.mail.EmailTemplateDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Setter(onMethod_ = @Autowired)
public class EmailServiceImpl implements EmailService {

    private final TypeReference<HashMap<String, Object>> TYPE_REFERENCE = new TypeReference<>() {};
    private ObjectMapper objectMapper;
    private SpringTemplateEngine templateEngine;
    private JavaMailSender emailSender;

    @Override
    public void sendMessage(EmailTemplateDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(dto.getRecipient());
        message.setSubject(dto.getSubject());
        message.setText(dto.getTemplate());

        emailSender.send(message);
    }

    @Async
    @Override
    public void sendMessageByTemplate(EmailTemplateDto dto) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

            message.setSubject(dto.getSubject());
            message.setTo(dto.getRecipient());
            message.setText(buildContent(dto), true);

            buildAttachment(dto, mimeMessage);
            emailSender.send(mimeMessage);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void buildAttachment(EmailTemplateDto dto, MimeMessage mimeMessage) throws MessagingException {
        if(dto instanceof EmailTicketDto) {

            EmailTicketDto buyEmailTicketDto = (EmailTicketDto) dto;
            String fileName = buyEmailTicketDto.getUserName() + " " + buyEmailTicketDto.getEventName() + ".pdf";
            FileSystemResource file = new FileSystemResource(new File(buyEmailTicketDto.getTicketPath() + fileName));

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.addAttachment(fileName, file);
            helper.setText(buildContent(dto), true);
        }
    }

    private String buildContent(EmailTemplateDto dto) {
        final Context context = new Context();
        Map<String, Object> map = objectMapper.convertValue(dto, TYPE_REFERENCE);
        context.setVariables(map);
        return templateEngine.process(dto.getTemplate(), context);
    }
}
