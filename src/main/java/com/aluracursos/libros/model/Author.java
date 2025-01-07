package com.aluracursos.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Author(@JsonAlias("name") String name,
                     @JsonAlias("birth_year") Integer birthYear,
                     @JsonAlias("death_year") Integer deathYear) {

}
