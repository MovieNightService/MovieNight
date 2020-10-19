package com.kharkiv.movienight.service.validation.model.userevent;

import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.exception.standard.ForbiddenException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import com.kharkiv.movienight.persistence.repository.UserEventRepository;
import com.kharkiv.movienight.service.event.EventService;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.event.EventUpdateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class UserEventValidatorImpl implements Validator<UserEvent> {

    private UserEventRepository userEventRepository;
    private EventService eventService;

    @Override
    public void validate(MethodType methodType, Object... objects) {
        User actor = getActorFromContext();
        Object dto;
        Object object;
        UserEvent userEvent = null;

        if (objects.length == 1) {
            object = objects[0];

            if (object instanceof UserEvent) {
                userEvent = (UserEvent) object;
            }
            executeValidation(methodType, actor, null, userEvent);

        } else if (objects.length == 2) {
            dto = objects[0];
            object = objects[1];

            if (object instanceof UserEvent) {
                userEvent = (UserEvent) object;
            }
            executeValidation(methodType, actor, dto, userEvent);

        } else {
            executeValidation(methodType, actor, null, null);
        }
    }

    private void executeValidation(MethodType methodType, User actor, Object dto, UserEvent userEvent) {
        switch (methodType) {
            case CREATE:
                validateCreate(dto, actor);
                break;
            case DELETE:
                validateDelete(actor, userEvent);
                break;
            default:
                throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    private void validateDelete(User actor, UserEvent userEvent) {
        if(!actor.getId().equals(userEvent.getUser().getId())){
            throw new ForbiddenException("You can't delete another ticket");
        }

    }

    private void validateCreate(Object dto, User actor) {
        UserEventCreateDto createDto = null;

        if (dto instanceof UserEventCreateDto) {
            createDto = (UserEventCreateDto) dto;
        }

        if (createDto != null) {
            Event event = eventService.findByIdAndDeletedFalse(createDto.getEventId());
            validateAge(event, actor);
            validateExistsByEvent(event, actor);
            validateEvent(event, actor);
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateExistsByEvent(Event event, User actor) {
        if (userEventRepository.existsByEventAndUser(event, actor)) {
            throw new BadRequestException("You have already bought a ticket for this event");
        }
    }

    private void validateAge(Event event, User actor) {
        Integer age = event.getMovie().getAge();

        if(actor.getAge() < age){
            throw new BadRequestException("Your age must be more than " + age);
        }
    }

    private void validateEvent(Event event, User actor) {
        if (userEventRepository.existsByEventAndUser(event, actor)) {
            throw new BadRequestException("You already have a ticket for this event");
        }
    }
}
