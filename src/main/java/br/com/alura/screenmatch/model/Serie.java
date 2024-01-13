package br.com.alura.screenmatch.model;


import lombok.Data;
import lombok.ToString;

import java.util.OptionalDouble;
@Data

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private String atores;
    private Categoria genero;
    private String poster;
    private String sinopse;
    private Double avaliacao;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.poster = dadosSerie.poster();
        this.sinopse = dadosSerie.sinopse();
        this.atores = dadosSerie.atores();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
    }

    @Override
    public String toString() {
        return
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", atores='" + atores + '\'' +
                ", genero=" + genero +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", avaliacao=" + avaliacao
                ;
    }
}
