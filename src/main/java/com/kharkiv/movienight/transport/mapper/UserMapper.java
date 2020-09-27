package com.kharkiv.movienight.transport.mapper;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.transport.dto.UserRegistrationDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import com.kharkiv.movienight.transport.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.util.List;

@Mapper(imports = {Instant.class, UserRole.class, List.class})
public abstract class UserMapper {

    @Mapping(target = "createdBy", source = "user.createdBy.id")
    @Mapping(target = "updatedBy", source = "user.updatedBy.id")
    @Mapping(target = "roles", expression = "java((List<UserRole>) user.getAuthorities())")
    public abstract UserOutcomeDto toOutcomeDto(User user);

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    public abstract User toEntity(UserRegistrationDto dto);

    public abstract User toEntity(UserUpdateDto dto, @MappingTarget User user);
}
