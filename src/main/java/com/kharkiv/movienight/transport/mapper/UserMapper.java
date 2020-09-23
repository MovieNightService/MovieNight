package com.kharkiv.movienight.transport.mapper;

import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.service.UserService;
import com.kharkiv.movienight.transport.dto.UserCreateDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

@Mapper
public abstract class UserMapper {

    @Mapping(target = "createdBy", source = "user.createdBy.id")
    @Mapping(target = "updatedBy", source = "user.updatedBy.id")
    public abstract UserOutcomeDto toOutcomeDto(User user);

    public abstract User toEntity(UserCreateDto dto);
}
