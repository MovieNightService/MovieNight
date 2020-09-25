package com.kharkiv.movienight.transport.mapper;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.transport.dto.UserCreateDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import com.kharkiv.movienight.transport.dto.UserUpdateDto;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-25T15:30:56+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserOutcomeDto toOutcomeDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutcomeDto userOutcomeDto = new UserOutcomeDto();

        userOutcomeDto.setUpdatedBy( userUpdatedById( user ) );
        userOutcomeDto.setCreatedBy( userCreatedById( user ) );
        userOutcomeDto.setFirstName( user.getFirstName() );
        userOutcomeDto.setLastName( user.getLastName() );
        userOutcomeDto.setUsername( user.getUsername() );
        userOutcomeDto.setDateOfBirth( user.getDateOfBirth() );
        userOutcomeDto.setPhone( user.getPhone() );
        userOutcomeDto.setEmail( user.getEmail() );
        byte[] avatar = user.getAvatar();
        if ( avatar != null ) {
            userOutcomeDto.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }
        userOutcomeDto.setCreatedAt( user.getCreatedAt() );
        userOutcomeDto.setUpdatedAt( user.getUpdatedAt() );
        if ( user.getDeleted() != null ) {
            userOutcomeDto.setDeleted( user.getDeleted() );
        }

        userOutcomeDto.setRoles( (List<UserRole>) user.getAuthorities() );

        return userOutcomeDto;
    }

    @Override
    public User toEntity(UserCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setDateOfBirth( dto.getDateOfBirth() );
        user.setPhone( dto.getPhone() );
        byte[] avatar = dto.getAvatar();
        if ( avatar != null ) {
            user.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        user.setCreatedAt( Instant.now() );
        user.setUpdatedAt( Instant.now() );

        return user;
    }

    @Override
    public User toEntity(UserUpdateDto dto, User user) {
        if ( dto == null ) {
            return null;
        }

        user.setUsername( dto.getUsername() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setDateOfBirth( dto.getDateOfBirth() );
        user.setPhone( dto.getPhone() );
        byte[] avatar = dto.getAvatar();
        if ( avatar != null ) {
            user.setAvatar( Arrays.copyOf( avatar, avatar.length ) );
        }
        else {
            user.setAvatar( null );
        }

        return user;
    }

    private Long userUpdatedById(User user) {
        if ( user == null ) {
            return null;
        }
        User updatedBy = user.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        Long id = updatedBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long userCreatedById(User user) {
        if ( user == null ) {
            return null;
        }
        User createdBy = user.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        Long id = createdBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
