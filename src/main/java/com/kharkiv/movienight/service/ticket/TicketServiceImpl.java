package com.kharkiv.movienight.service.ticket;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.service.mail.EmailService;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.mail.EmailTicketDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
@Setter(onMethod_ = @Autowired)
public class TicketServiceImpl implements TicketService {

    private final String PDF_PATH_FOR_MAIL = "src/main/resources/ticket/pdf/";
    private final String PDF_PATH = "./src/main/resources/ticket/pdf/";
    private final String QR_PATH = "./src/main/resources/ticket/qr/";

    private UserEventRepository userEventRepository;
    private EmailService emailService;
    private Validator<UserEvent> validator;

    @Override
    public void create(UserEvent userEvent) {
        buildPDF(userEvent);

        User user = userEvent.getUser();
        Event event = userEvent.getEvent();

        EmailTicketDto emailDto = new EmailTicketDto(
                user.getFirstName() + " " + user.getLastName(),
                event.getName(),
                event.getDate(),
                PDF_PATH_FOR_MAIL,
                user.getEmail(),
                "Registration for event",
                "emailNotificationBuyTicket"
        );

        emailService.sendMessageByTemplate(emailDto);
    }

    private void buildPDF(UserEvent userEvent) {
        Event event = userEvent.getEvent();
        User actor = getActorFromContext();

        Document document = new Document();

        String path = QR_PATH + actor.getId() + "_" + event.getId() + ".png";
        String text = "Secured by MovieNight team! Event: " + event.getName() + "! User: " + actor.getUsername();

        generateQRCodeImage(text, path);

        try {
            PdfWriter writer = PdfWriter.getInstance(
                    document,
                    new FileOutputStream(
                            PDF_PATH +
                                    actor.getFirstName() + " " +
                                    actor.getLastName() + " " +
                                    event.getName() +
                                    ".pdf"
                    )
            );
            Image img = Image.getInstance(path);

            document.open();
            document.add(new Paragraph(event.toString()));
            document.add(img);
            document.close();

            writer.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void generateQRCodeImage(String text, String filePath) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
