package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumirAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=49827a15";

    private ConsumirAPI consumoApi = new ConsumirAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();


    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie de deseas buscar");
        //Busca los datos de las series
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = convierteDatos.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = convierteDatos.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);

        //Mostrar la temporada y el titulo de los episodios
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            System.out.println("Temporada :" + (i + 1) + " Cantidad de episoidos :" + episodiosTemporada.size());
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println("\t" + episodiosTemporada.get(j).titulo());
//            }
//        }
        //codigo anterior con lambda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones a una lista de tipo de DatosEpisodio
        /*List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());*/
        //Top 5 episodios

        /*System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);
*/
        //Convirtiendo  los datos  a una lista de tipo Episodio

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);

        //Busqueda por anio
        /*System.out.println("Introduzca el anio");
        String anio = teclado.nextLine();*/
        /*var fechaBuscada = LocalDate.of(Integer.parseInt(anio), 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBuscada))
                .forEach(e -> System.out.println(
                        "Temporada " + e.getTemporada() +
                                " - Episodio " + e.getTitulo() +
                                " - Fecha de Lanzamiento " + e.getFechaLanzamiento().format(dtf)
                ));*/


        //Buscar episodio por un pedazo de titulo
        /*System.out.println("Por favor introduzca el titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Los datos del episodio === " + episodioBuscado.get());
        } else {
            System.out.println("Episodio no encontrado");
        }*/

        Map<Integer, Double> evaluacionesPorTemporadas = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporadas);

        DoubleSummaryStatistics est  = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio:: getEvaluacion));
        System.out.println("Media de las evaluaciones: "+ est.getAverage());
        System.out.println("Episodio Mejor evaluado: "+ est.getMax());
        System.out.println("Episodio Peor evaluado:  "+ est.getMin());
    }
}
