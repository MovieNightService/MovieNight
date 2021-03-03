package com.movienight.app.service.utils.actor;


import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.model.user.UserRole;

public class RoleUtils {

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
