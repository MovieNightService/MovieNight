package com.kharkiv.movienight.service.utils.validation.validator;

import com.kharkiv.movienight.service.utils.actor.ActorService;
import com.kharkiv.movienight.service.utils.validation.type.MethodType;

public interface Validator<T> extends ActorService {

    void validate(MethodType methodType, Object... objects);
}
