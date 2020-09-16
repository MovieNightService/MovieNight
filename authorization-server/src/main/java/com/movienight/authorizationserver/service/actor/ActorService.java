package com.movienight.authorizationserver.service.actor;

import com.movienight.authorizationserver.config.ApplicationContextProvider;
import com.movienight.authorizationserver.persistence.model.UserAccount;
import com.movienight.authorizationserver.service.UserAccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public abstract class ActorService {

    public UserAccount getActorFromContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return null;
        }

        return ApplicationContextProvider.getApplicationContext()
                .getBean(UserAccountService.class)
                .findByIdUnsafe(((UserAccount) authentication.getPrincipal()).getId());

    }
}
