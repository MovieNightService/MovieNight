package com.kharkiv.movienight;

import org.joda.time.Years;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.annotation.PostConstruct;
import java.time.*;

@SpringBootApplication
@EnableResourceServer
public class MovieNightApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieNightApplication.class, args);
    }

}
