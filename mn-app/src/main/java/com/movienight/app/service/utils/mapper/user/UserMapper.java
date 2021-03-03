package com.movienight.app.service.utils.mapper.user;

import com.movienight.foundation.dto.user.UserOutcomeDto;
import com.movienight.foundation.dto.user.UserRegistrationDto;
import com.movienight.foundation.dto.user.UserUpdateDto;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.model.user.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(imports = {Instant.class, UserRole.class, List.class})
public abstract class UserMapper {

    @Mapping(target = "createdBy", source = "user.createdBy.id")
    @Mapping(target = "updatedBy", source = "user.updatedBy.id")
    @Mapping(target = "roles", expression = "java(getUserRoleListByAuthorities(user.getAuthorities()))")
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "avatar", expression = "java(new byte[]{1,2,3,4,5})") //todo ONLY FOR DEVELOPMENT !!!!!!!!
    public abstract UserOutcomeDto toDto(User user);

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    public abstract User toEntity(UserRegistrationDto dto);

    public abstract User toEntity(UserUpdateDto dto, @MappingTarget User user);

    protected List<UserRole> getUserRoleListByAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> (UserRole) authority)
                .collect(Collectors.toList());
    }
}
