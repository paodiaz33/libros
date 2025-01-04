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
    //private final String API_KEY = "TU-APIKEY-OMDB";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;
    private List<Libro> Libros;
    private Optional<Libro> LibroBuscada;

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
                    buscarEpisodioPorLibro();
                    break;
                case 3:
                    mostrarLibrosBuscadas();
                    break;
                case 4:
                    buscarLibrosPorTitulo();
                    break;
                case 5:
                    buscarTop5Libros();
                    break;
                case 6:
                    buscarLibrosPorCategoria();
                    break;
                case 7:
                    filtrarLibrosPorTemporadaYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
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
    private void buscarEpisodioPorLibro() {
        mostrarLibrosBuscadas();
        System.out.println("Escribe el nombre de la Libro de la cual quieres ver los episodios");
        var nombreLibro = teclado.nextLine();

        Optional<Libro> Libro = Libros.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

        if(Libro.isPresent()){
            var LibroEncontrada = Libro.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            // for (int i = 1; i <= LibroEncontrada.getTotalTemporadas(); i++) {
            //     var json = consumoApi.obtenerDatos(URL_BASE + LibroEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            //     DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            //     temporadas.add(datosTemporada);
            // }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            LibroEncontrada.setEpisodios(episodios);
            repositorio.save(LibroEncontrada);
        }



    }
    private void buscarLibroWeb() {
        DatosLibros datos = getDatosLibro();
        // Libro Libro = new Libro(datos);
        // repositorio.save(Libro);
        //datosLibros.add(datos);
        System.out.println(datos);
    }

    private void mostrarLibrosBuscadas() {
        Libros = repositorio.findAll();

        Libros.stream()
                .sorted(Comparator.comparing(Libro::getGenero))
                .forEach(System.out::println);
    }

    private void buscarLibrosPorTitulo(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        LibroBuscada = repositorio.findByTituloContainsIgnoreCase(nombreLibro);

        if(LibroBuscada.isPresent()){
            System.out.println("El libro buscado es: " + LibroBuscada.get());
        } else {
            System.out.println("Libor no encontrado");
        }

    }
    private void buscarTop5Libros(){
        List<Libro> topLibros = repositorio.findTop5ByOrderByEvaluacionDesc();
        topLibros.forEach(s ->
                System.out.println("Libro: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()) );
    }

    private void buscarLibrosPorCategoria(){
        System.out.println("Escriba el genero/categoría de la Libro que desea buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Libro> LibrosPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las Libros de la categoría " + genero);
        LibrosPorCategoria.forEach(System.out::println);
    }
    public void filtrarLibrosPorTemporadaYEvaluacion(){
        System.out.println("¿Filtrar séries con cuántas temporadas? ");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("¿Com evaluación apartir de cuál valor? ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Libro> filtroLibros = repositorio.LibrosPorTemparadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("*** Libros filtradas ***");
        filtroLibros.forEach(s ->
                System.out.println(s.getTitulo() + "  - evaluacion: " + s.getEvaluacion()));
    }

    private void  buscarEpisodiosPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        // episodiosEncontrados.forEach(e ->
                // System.out.printf("Libro: %s Temporada %s Episodio %s Evaluación %s\n",
                //         e.getLibro().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));

    }

    private void buscarTop5Episodios(){
        buscarLibrosPorTitulo();
        if(LibroBuscada.isPresent()){
            Libro Libro = LibroBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(Libro);
            // topEpisodios.forEach(e ->
                    // System.out.printf("Libro: %s - Temporada %s - Episodio %s - Evaluación %s\n",
                    //         e.getLibro().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));

        }
    }
}

