package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String texto) {
        for (Categoria categoria : Categoria.values())
            if (categoria.categoriaOmdb.equals(texto))
                return categoria;
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + texto);
    }
}
