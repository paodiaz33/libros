package com.aluracursos.libros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;
    @ManyToOne
    private Libro libro;

    public Autor(Author author, Libro Libro){
        this.nombre = author.name();
        this.anioNacimiento = author.birthYear();
        this.anioFallecimiento = author.deathYear();
        this.libro = Libro;
    }

    public Autor(){}
    
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Año de nacimiento: " + anioNacimiento + "\n" +
                "Año de fallecimiento: " + anioFallecimiento + "\n";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    

}
