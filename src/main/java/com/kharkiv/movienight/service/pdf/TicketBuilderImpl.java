package com.kharkiv.movienight.service.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.service.event.EventService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@Setter(onMethod_ = @Autowired)
public class TicketBuilderImpl implements TicketBuilder {

    private EventService eventService;
    private UserEventRepository userEventRepository;

    @Override
    public void buildPDF(Long eventId) {
        Event event = eventService.findByIdAndDeletedFalse(eventId);
        User actor = getActorFromContext();
        validateEvent(event, actor);

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(actor.getUsername() + ".pdf"));
            document.open();
            document.add(new Paragraph(event.toString()));
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void validateEvent(Event event, User actor){
        if(!userEventRepository.existsByEventAndUser(event, actor)){
            throw new BadRequestException("You have not ticket for this event");
        }
    }
}
