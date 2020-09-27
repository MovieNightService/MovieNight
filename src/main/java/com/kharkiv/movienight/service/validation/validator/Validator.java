package com.kharkiv.movienight.service.validation.validator;

import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.service.validation.type.MethodType;

public interface Validator extends ActorService {

    void validate(MethodType methodType, Object... objects);
}
