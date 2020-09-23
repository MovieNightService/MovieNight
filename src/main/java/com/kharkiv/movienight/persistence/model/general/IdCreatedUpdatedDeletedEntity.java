package com.kharkiv.movienight.persistence.model.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class IdCreatedUpdatedDeletedEntity extends IdCreatedUpdatedEntity {

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

}