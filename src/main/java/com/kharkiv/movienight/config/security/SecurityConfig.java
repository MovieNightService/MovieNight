package com.kharkiv.movienight.config.security;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.service.utils.mail.EmailService;
import com.kharkiv.movienight.transport.dto.mail.EmailTemplateDto;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collections;
import java.util.Random;

@Configuration
@Order(1)
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;
    private final String[] REQUESTS = {
            "/login",
            "/oauth/authorize",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/webjars/**",
            "favicon.ico",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/static/**",
            "/css/**",
            "/vendor/**",
            "/fonts/**",
            "/js/**",
            "/images/**"
    };

    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers(REQUESTS)
                .and()
                .authorizeRequests()
                .antMatchers(REQUESTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/users/registration");
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserService userService) {
        return map -> {
            Long id = (Long) map.get("sub");

            User user = userService.findById(id);

            if(user == null) {
                User newUser = new User();

                String[] fullName = ((String) map.get("name")).split("\\s+");
                String firstName = fullName[0];
                String lastName = fullName[1];

                newUser.setId(id);
                newUser.setUpdatedAt(Instant.now());
                newUser.setCreatedAt(Instant.now());
                newUser.setAgreement(true);
                newUser.setAuthorities(Collections.singletonList(UserRole.USER));
                newUser.setDeleted(false);
                newUser.setUsername(fullName[0] + fullName[1]);
                newUser.setAvatar((byte[]) map.get("picture"));
                newUser.setEmail((String) map.get("email"));
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);

                String password = generatePassword();
                newUser.setPassword(password);
                EmailTemplateDto dto = new EmailTemplateDto(
                        newUser.getEmail(),
                        "password",
                        "Your new password: " + password
                );
                emailService.sendMessage(dto);

                return newUser;
            }

            return userService.create(user);
        };
    }

    private String generatePassword(){
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[12];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        StringBuilder stringPassword = new StringBuilder();
        for(int i = 4; i < 12 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
            stringPassword.append(password[i]);
        }
        return stringPassword.toString();
    }
}
