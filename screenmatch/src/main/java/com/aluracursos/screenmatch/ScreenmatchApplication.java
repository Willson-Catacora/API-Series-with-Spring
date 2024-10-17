package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.principal.EjemploStream;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumirAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();

        //EjemploStream lista = new EjemploStream();
        //lista.muestraStream();

        /*//System.out.println("Hola mundo desde spring...");
        var consumoApi = new ConsumirAPI();
        var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&y=2011&apikey=49827a15");
        System.out.println(json);

        ConvierteDatos conversor = new ConvierteDatos();
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=49827a15");
        var episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
        System.out.println(episodios);
*/

    }
}
