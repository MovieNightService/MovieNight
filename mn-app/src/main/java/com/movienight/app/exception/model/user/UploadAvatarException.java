package com.movienight.app.exception.model.user;

import com.movienight.app.exception.global.BadRequestException;

public class UploadAvatarException extends BadRequestException {

    public UploadAvatarException() {
        super("Upload avatar error");
    }

    public UploadAvatarException(String message) {
        super(message);
    }
}
