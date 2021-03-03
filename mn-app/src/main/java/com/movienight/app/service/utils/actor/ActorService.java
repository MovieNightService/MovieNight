package com.movienight.app.service.utils.actor;

import com.movienight.app.service.user.UserService;
import com.movienight.foundation.persistence.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public interface ActorService {

    default User getActorFromContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
                return null;
            }

            return ApplicationContextProvider.getApplicationContext()
                    .getBean(UserService.class)
                    .findById(((User) authentication.getPrincipal()).getId());
        }
        return null;
    }
}


