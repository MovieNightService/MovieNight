package com.kharkiv.movienight.transport.mapper.user;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.model.user.UserRole;
import com.kharkiv.movienight.service.actor.ActorService;
import com.kharkiv.movienight.transport.dto.user.UserRegistrationDto;
import com.kharkiv.movienight.transport.dto.user.UserOutcomeDto;
import com.kharkiv.movienight.transport.dto.user.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.util.List;

@Mapper(imports = {Instant.class, UserRole.class, List.class})
public abstract class UserMapper implements ActorService {

    @Mapping(target = "createdBy", source = "user.createdBy.id")
    @Mapping(target = "updatedBy", source = "user.updatedBy.id")
    @Mapping(target = "roles", expression = "java((List<UserRole>) user.getAuthorities())")
    public abstract UserOutcomeDto toOutcomeDto(User user);

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    @Mapping(target = "createdBy", expression = "java(getActorFromContext())")
    @Mapping(target = "updatedBy", expression = "java(getActorFromContext())")
    public abstract User toEntity(UserRegistrationDto dto);

    public abstract User toEntity(UserUpdateDto dto, @MappingTarget User user);
}
