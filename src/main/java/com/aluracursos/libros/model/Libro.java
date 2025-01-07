package com.aluracursos.libros.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String[] languages;
    private Integer cantidadDescargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.languages = datosLibro.idiomas().toArray(new String[0]);
        this.cantidadDescargas = datosLibro.descargas();
        this.autores = datosLibro.autores().stream().map(autor -> new Autor(autor, this)).toList();
    }

    @Override
    public String toString() {
        return  "Titulo: " + titulo + "\n" +
                "Idiomas: " + String.join(", ", languages) + "\n" +
                "Cantidad de descargas: " + cantidadDescargas + "\n" +
                "Autores: " + autores + "\n";

    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }
}
