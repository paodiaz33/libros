package com.aluracursos.libros.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(@JsonAlias("count") Integer count,
                           @JsonAlias("next") String next,
                           @JsonAlias("previous") String previous,
                           @JsonAlias("results") DatosLibro[] results) {
}
