package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumirAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=49827a15";

    private ConsumirAPI consumoApi = new ConsumirAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private List<Serie> series;
    private SerieRepository repositorio;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    break;
                default:
                    System.out.println("Opcion no valida....");
            }
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.println("*** Escriba el nombre de la serie que desee buscar ***");
        //Busca los datos de las series
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = convierteDatos.obtenerDatos(json, DatosSerie.class);
        //System.out.println(datos);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nomber de la serie de la cual desee ver los episodios");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY);
                var datosTemporadas = convierteDatos.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        //datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        //datosSeries.forEach(System.out::println);
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}

