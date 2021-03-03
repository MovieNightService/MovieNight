package com.movienight.app.exception.user;

import com.movienight.app.exception.common.BadRequestException;

public class UploadAvatarException extends BadRequestException {

    public UploadAvatarException() {
        super("Upload avatar error");
    }

    public UploadAvatarException(String message) {
        super(message);
    }
}
