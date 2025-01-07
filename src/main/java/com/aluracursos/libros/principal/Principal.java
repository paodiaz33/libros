package com.aluracursos.libros.principal;

import java.util.*;
import java.util.stream.Collectors;

import com.aluracursos.libros.model.*;
import com.aluracursos.libros.repository.LibroRepository;
import com.aluracursos.libros.service.ConsumoAPI;
import com.aluracursos.libros.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> Libros;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscadas();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosAño();
                    break;
                case 5:
                    mostrarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibros getDatosLibro() {
        System.out.println("Escribe el nombre de la Libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "search="+nombreLibro.replace(" ", "+"));
        //System.out.println(json);
        DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
        return datos;
    }

    private void buscarLibroWeb() {
        DatosLibros datos = getDatosLibro();
        Libros = datos.results().stream()
                .map(Libro::new)
                .collect(Collectors.toList());
        repositorio.saveAll(Libros);
        System.out.println(datos);
    }

    private void mostrarLibrosBuscadas() {
        Libros = repositorio.findAll();

        Libros.stream()
                 .sorted(Comparator.comparing(Libro::getCantidadDescargas).reversed())
                 .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        Libros = repositorio.findAll();

        List<Autor> auotores = Libros.stream()
                .flatMap(s -> s.getAutores().stream())
                .collect(Collectors.toList());
        System.out.println("Autores registrados");
        auotores.forEach(System.out::println);
    }

    private void autoresVivosAño() {
        System.out.println("Escribe el año que deseas buscar");
        var año = teclado.nextInt();
        teclado.nextLine();
        Libros = repositorio.findAll();

        List<Autor> autores = Libros.stream()
                .flatMap(s -> s.getAutores().stream())
                .filter(s -> s.getAnioNacimiento() <= año && s.getAnioFallecimiento() >= año)
                .collect(Collectors.toList());
        System.out.println("Autores vivos en el año " + año);
        autores.forEach(System.out::println);
    }

    private void mostrarLibrosIdioma() {
        System.out.println("Escribe el idioma que deseas buscar");
        var idioma = teclado.nextLine();
        Libros = repositorio.findAll();

        List<Libro> libros = Libros.stream()
                .filter(s -> Arrays.asList(s.getLanguages()).contains(idioma))
                .collect(Collectors.toList());
        System.out.println("Libros en idioma " + idioma);
        libros.forEach(System.out::println);
    }
    
}

