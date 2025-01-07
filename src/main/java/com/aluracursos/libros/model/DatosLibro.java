package com.aluracursos.libros.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("id") Long id,
                        @JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<Author> autores,
                          @JsonAlias("languages") List<String> idiomas,
                          @JsonAlias("download_count") Integer descargas){
} 