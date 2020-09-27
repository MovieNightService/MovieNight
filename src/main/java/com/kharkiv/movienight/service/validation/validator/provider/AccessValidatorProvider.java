package com.kharkiv.movienight.service.validation.validator.provider;

import com.kharkiv.movienight.service.validation.model.UserAccessValidatorImpl;
import com.kharkiv.movienight.service.validation.type.ModelType;
import com.kharkiv.movienight.service.validation.validator.AccessValidator;

import java.util.HashMap;
import java.util.Map;

public class AccessValidatorProvider {

    static Map<ModelType, AccessValidator> validatorMap = new HashMap<>();

    static {
        validatorMap.put(ModelType.USER, new UserAccessValidatorImpl());
    }

    public static AccessValidator getValidator(ModelType modelType) {
        return validatorMap.get(modelType);
    }
}
