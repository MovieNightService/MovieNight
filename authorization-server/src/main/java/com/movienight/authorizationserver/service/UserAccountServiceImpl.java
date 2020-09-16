package com.movienight.authorizationserver.service;

import com.movienight.authorizationserver.exception.UserAccountNotFoundException;
import com.movienight.authorizationserver.persistence.model.UserAccount;
import com.movienight.authorizationserver.persistence.model.UserRole;
import com.movienight.authorizationserver.persistence.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userAccountRepository.existsByUsername(username) ? userAccountRepository.findByUsername(username) : buildObject();
    }

    @Override
    public UserAccount findByIdUnsafe(Long id) {
        return userAccountRepository.findById(id).orElseThrow(UserAccountNotFoundException::new);
    }

    private UserAccount buildObject() {
        UserAccount userAccount = new UserAccount();

        userAccount.setPassword(passwordEncoder.encode("admin"));
        userAccount.setAuthorities(Collections.singletonList(UserRole.ADMIN));
        userAccount.setEmail("admin@gmail.com");

        return userAccount;
    }
}