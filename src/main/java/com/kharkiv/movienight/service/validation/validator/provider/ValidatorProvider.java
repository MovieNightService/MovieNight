package com.kharkiv.movienight.service.validation.validator.provider;

import com.kharkiv.movienight.service.validation.model.UserValidatorImpl;
import com.kharkiv.movienight.service.validation.type.ModelType;
import com.kharkiv.movienight.service.validation.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorProvider {

    static Map<ModelType, Validator> validatorMap = new HashMap<>();

    static {
        validatorMap.put(ModelType.USER, new UserValidatorImpl());
    }

    public static Validator getValidator(ModelType modelType) {
        return validatorMap.get(modelType);
    }
}
