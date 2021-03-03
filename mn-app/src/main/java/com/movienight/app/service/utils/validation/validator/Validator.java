package com.movienight.app.service.utils.validation.validator;

import com.movienight.app.service.utils.actor.ActorService;
import com.movienight.app.service.utils.validation.type.MethodType;

public interface Validator<T> extends ActorService {

    void validate(MethodType methodType, Object... objects);
}
