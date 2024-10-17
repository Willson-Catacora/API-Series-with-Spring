package com.aluracursos.screenmatch.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStream {

    public void muestraStream (){
        List<String> furtas = Arrays.asList("manzana", "naranja", "durazno", "uva");

        furtas.stream()
                .sorted()
                .filter(n -> n.length() > 3)
                .map(n -> n.toLowerCase())
                .forEach(System.out::println);
    }
}
