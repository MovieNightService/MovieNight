package com.movienight.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Controller
@Getter
@Setter
public class User extends IdCreatedUpdatedDeletedEntity {

}
