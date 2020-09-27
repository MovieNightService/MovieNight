package com.kharkiv.movienight.service.actor;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;

public class ActorUtils {

    public static boolean isAdmin(User actor){
        return actor.getAuthorities().contains(UserRole.ADMIN);
    }

    public static boolean isManager(User actor){
        return actor.getAuthorities().contains(UserRole.MANAGER);
    }

    public static boolean isUser(User actor){
        return actor.getAuthorities().contains(UserRole.USER);
    }
}
