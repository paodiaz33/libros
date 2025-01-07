package com.aluracursos.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluracursos.libros.model.Libro;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

}
