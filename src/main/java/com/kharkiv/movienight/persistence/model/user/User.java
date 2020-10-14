package com.kharkiv.movienight.persistence.model.user;

//import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.general.IdCreatedUpdatedDeletedEntity;
//import com.kharkiv.movienight.persistence.model.userevent.UserEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends IdCreatedUpdatedDeletedEntity implements UserDetails  {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Instant dateOfBirth;

    @Column(nullable = false, unique = true)
    private String phone;

    private byte[] avatar;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean agreement = true;

    @Column(nullable = false)
    private String username;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return agreement;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getAge(){
        LocalDate currentDate = LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC);
        LocalDate dateOfBirth = LocalDate.ofInstant(this.dateOfBirth,  ZoneOffset.UTC);

        return Period.between(dateOfBirth, currentDate).getYears();
    }
}
