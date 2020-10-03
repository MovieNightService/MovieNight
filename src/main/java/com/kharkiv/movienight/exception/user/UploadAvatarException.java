package com.kharkiv.movienight.exception.user;

import com.kharkiv.movienight.exception.standard.BadRequestException;

public class UploadAvatarException extends BadRequestException {

    public UploadAvatarException() {
        super("Upload avatar error");
    }

    public UploadAvatarException(String message) {
        super(message);
    }
}
