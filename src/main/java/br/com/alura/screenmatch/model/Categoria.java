package br.com.alura.screenmatch.model;

public enum Categoria {

    ACAO("Action", "Acao"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    TERROR("horror", "Terror");

    private String categoriaOmdb;

    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues)
    {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }
    public static Categoria fromString(String text){
        for (Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return  categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada para a serie");
    }

    public static Categoria fromPortugues(String text){
        for (Categoria categoria : Categoria.values()){
            if(categoria.categoriaPortugues.equalsIgnoreCase(text)){
                return  categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada para a serie");
    }
}
