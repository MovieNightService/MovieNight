package com.movienight.app.service.utils.validation.model.event;

import com.movienight.app.exception.global.BadRequestException;
import com.movienight.app.service.utils.actor.RoleUtils;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.foundation.dto.event.EventCreateDto;
import com.movienight.foundation.dto.event.EventUpdateDto;
import com.movienight.foundation.persistence.model.event.Event;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.repository.EventRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class EventValidatorImpl implements Validator<Event> {

    private EventRepository eventRepository;

    @Override
    public void validate(MethodType methodType, Object... objects) {
        User actor = getActorFromContext();
        Object dto;
        Object object;
        Event event = null;

        if (objects.length == 1) {
            object = objects[0];

            if (object instanceof Event) {
                event = (Event) object;
            }
            executeValidation(methodType, actor, null, event);

        } else if (objects.length == 2) {
            dto = objects[0];
            object = objects[1];

            if (object instanceof Event) {
                event = (Event) object;
            }
            executeValidation(methodType, actor, dto, event);

        } else {
            executeValidation(methodType, actor, null, null);
        }
    }

    private void executeValidation(MethodType methodType, User actor, Object dto, Event event) {
        switch (methodType) {
            case CREATE -> validateCreate(dto, actor);
            case UPDATE -> validateUpdate(dto, event);
            case DELETE -> validateDelete(actor, event);
            case RESTORE -> validateRestore(actor, event);
            default -> throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    private void validateRestore(User actor, Event event) {
        if(RoleUtils.isManager(actor)){
            if(!actor.getId().equals(event.getCreatedBy().getId())){
                throw new BadRequestException("Can't restore event another manager");
            }
        }
    }

    private void validateDelete(User actor, Event event) {
        if(RoleUtils.isManager(actor)){
            if(!actor.getId().equals(event.getCreatedBy().getId())){
                throw new BadRequestException("Can't delete event another manager");
            }
        }
    }

    private void validateUpdate(Object dto, Event event) {
        EventUpdateDto updateDto = null;

        if (dto instanceof EventUpdateDto) {
            updateDto = (EventUpdateDto) dto;
        }

        if (updateDto != null) {
            validateExistsByNameAndIdNot(updateDto.getName(), event.getId());
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateCreate(Object dto, User actor) {
        EventCreateDto createDto = null;

        if (dto instanceof EventCreateDto) {
            createDto = (EventCreateDto) dto;
        }

        if (createDto != null) {
            validateExistsByIdAndMovieId(actor, createDto.getMovieId());
            validateExistsByName(createDto.getName());
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateExistsByNameAndIdNot(String name, Long id) {
        if (eventRepository.existsByNameAndIdNot(name, id)) {
            throw new BadRequestException("Event with such name already exists");
        }
    }

    private void validateExistsByName(String name) {
        if (eventRepository.existsByName(name)) {
            throw new BadRequestException("Event with such name already exists");
        }
    }

    private void validateExistsByIdAndMovieId(User actor, Long movieId) {
        if (eventRepository.existsByCreatedByAndMovieId(actor, movieId)) {
            throw new BadRequestException("Event with such movie already exists");
        }
    }
}
