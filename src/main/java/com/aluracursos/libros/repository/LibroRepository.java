package com.aluracursos.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluracursos.libros.model.Libro;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    // List<Libro> findTop5ByOrderByEvaluacionDesc();
    // List<Libro> findByGenero(Categoria categoria);
    //List<Libro> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);

    // @Query("SELECT s FROM Libro s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    // List<Libro> LibrosPorTemparadaYEvaluacion(int totalTemporadas, Double evaluacion);

    // @Query("SELECT e FROM Libro s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    // List<Episodio> episodiosPorNombre(String nombreEpisodio);

    // @Query("SELECT e FROM Libro s JOIN s.episodios e WHERE s = :Libro ORDER BY e.evaluacion DESC LIMIT 5 ")
    // List<Episodio> top5Episodios(Libro Libro);
}
