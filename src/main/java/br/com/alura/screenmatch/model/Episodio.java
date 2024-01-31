package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;


@Data
@ToString(of = {"temporada", "titulo", "numeroEpisodio", "avaliacao", "dataLancamento"}) // Especifique os campos a serem incluídos na representação

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;


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

    public Episodio() {}


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
