package br.com.alura.screenmatch.model;


import br.com.alura.screenmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
@Data

@Entity
@Table(name = "series")

public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private String atores;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String poster;
    private String sinopse;
    private Double avaliacao;

    @OneToMany(mappedBy = "serie" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {}

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.poster = dadosSerie.poster();
        this.atores = dadosSerie.atores();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
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
                ", avaliacao=" + avaliacao+ '\'' +
                ", episodios=" + episodios ;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(episodio -> episodio.setSerie(this));
        this.episodios = episodios;
    }
}
