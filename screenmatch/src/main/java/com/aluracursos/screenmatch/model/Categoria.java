package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Categoria fromString(String texto) {
        for (Categoria categoria : Categoria.values())
            if (categoria.categoriaOmdb.equals(texto))
                return categoria;
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + texto);
    }

    public static Categoria fromEspanol(String texto) {
        for (Categoria categoria : Categoria.values())
            if (categoria.categoriaEspanol.equals(texto))
                return categoria;
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + texto);
    }
}
