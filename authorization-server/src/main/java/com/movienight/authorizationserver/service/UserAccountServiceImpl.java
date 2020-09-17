package com.movienight.authorizationserver.service;

import com.movienight.authorizationserver.exception.UserAccountNotFoundException;
import com.movienight.authorizationserver.persistence.model.UserAccount;
import com.movienight.authorizationserver.persistence.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElseThrow(UserAccountNotFoundException::new);
    }

    @Override
    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id).orElseThrow(UserAccountNotFoundException::new);
    }
}