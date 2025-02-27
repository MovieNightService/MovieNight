package com.movienight.app.service.utils.validation.model.userevent;

import com.movienight.app.exception.global.BadRequestException;
import com.movienight.app.exception.global.ForbiddenException;
import com.movienight.app.service.event.EventService;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.foundation.dto.userevent.UserEventCreateDto;
import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.model.userevent.UserEvent;
import com.movienight.foundation.persistence.repository.UserEventRepository;
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
