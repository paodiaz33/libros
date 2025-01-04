package com.aluracursos.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") String[] autores,
                         @JsonAlias("languages") String[] idiomas,
                         @JsonAlias("subjects") String[] categorias) {
} 