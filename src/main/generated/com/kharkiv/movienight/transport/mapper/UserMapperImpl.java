package com.kharkiv.movienight.transport.mapper;

import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import java.security.Principal;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-23T00:09:57+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class UserMapperImpl extends UserMapper {

    @Override
    public UserOutcomeDto toOutcomeDto(Principal principal) {
        if ( principal == null ) {
            return null;
        }

        UserOutcomeDto userOutcomeDto = new UserOutcomeDto();

        userOutcomeDto.setName( principal.getName() );

        return userOutcomeDto;
    }
}
