package com.kharkiv.movienight.service.actor;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.user.UserService;
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


