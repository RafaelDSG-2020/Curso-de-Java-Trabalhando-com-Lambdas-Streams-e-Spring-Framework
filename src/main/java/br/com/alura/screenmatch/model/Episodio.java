package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;





@Data
@ToString(of = {"temporada", "titulo", "numeroEpisodio", "avaliacao", "dataLancamento"}) // Especifique os campos a serem incluídos na representação
public class Episodio {

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

//    public LocalDate getDataLancamento() {
//        return dataLancamento;
//    }

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio)
    {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();

        try {
            this.avaliacao = Double.valueOf( dadosEpisodio.avaliacao());
        }catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataDeLancamento());
        }catch (DateTimeParseException ex){
            this.dataLancamento = null;
        }


    }

    public Episodio() {

    }


//    @Override
//    public String toString() {
//        return "Episodio{" +
//                "temporada=" + temporada +
//                ", titulo='" + titulo + '\'' +
//                ", numeroEpisodio=" + numeroEpisodio +
//                ", avaliacao=" + avaliacao +
//                ", dataLancamento=" + dataLancamento +
//                '}';
//    }
}
