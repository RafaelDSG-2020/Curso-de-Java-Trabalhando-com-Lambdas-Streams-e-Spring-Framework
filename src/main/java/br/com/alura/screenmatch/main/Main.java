package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import ch.qos.logback.core.net.SyslogOutputStream;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    ConsumoApi consumo = new ConsumoApi();
    ConverteDados converte = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String SEASON = "&season=";
    private final String API_KEY = "&apikey=80c597c9";
    public void exibirMenu(){
        System.out.println("Digite o Nome da Série  para Busca: ");
        String nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO+nomeSerie.replace(" ","+")+API_KEY);
        DadosSerie dadosSerie = converte.obterDados(json, DadosSerie.class); // O Record que tem os dados da serie
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int temporada = 1; temporada <= dadosSerie.totalTemporadas() ; temporada++)
		{

			json = consumo.obterDados(ENDERECO+nomeSerie.replace(" ","+")+SEASON+temporada+API_KEY);
			DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
//        for (int temporada = 0; temporada < dadosSerie.totalTemporadas(); temporada++) {
//
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(temporada).episodios();
//            for (int epsodio = 0; epsodio < episodiosTemporada.size(); epsodio++) {
//                System.out.println(episodiosTemporada.get(epsodio).titulo());
//
//            }
//        }
//        temporadas.forEach(temporada -> temporada.episodios()
//                  .forEach(epsodio -> System.out.println(epsodio.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(temporada -> temporada.episodios().stream())
                .toList(); // Com tolist não pode alterar.
                //.collect(Collectors.toList()); faz uma lista que pode adicionar no fim
//***************************************************************************************************
//        System.out.println("\n Top 10 Episódios ");
//        dadosEpisodios.stream()
//                .filter(epsodio -> !epsodio.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(epsodio -> System.out.println("Primeiro filtro(N/A)"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(epsodio -> System.out.println("Ordenação " + epsodio))
//                .limit(10)
//                .peek(epsodio -> System.out.println("Limite " + epsodio))
//                .map(epsodio -> epsodio.titulo().toUpperCase())
//                .peek(epsodio -> System.out.println("Mapeamento " + epsodio))
//                .forEach(System.out::println);
//
// *******************************************************************************************************

        List<Episodio> episodios = temporadas.stream()
                .flatMap(temporada -> temporada.episodios().stream()
                        .map(dadosEpisodio -> new Episodio(temporada.numero(), dadosEpisodio))
                ).collect(Collectors.toList());

//        episodios.forEach(System.out::println);
//
//        System.out.println("Digite um Trecho do Título do Episódio que Você está Procurando !!");
//
//        String trechoTitulo = leitura.nextLine();
//        Optional<Episodio> epsodioBuscado = episodios.stream()
//                .filter(episodio -> episodio.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (epsodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado !");
//            System.out.println("Temporada: "+ epsodioBuscado.get().getTemporada());
//        }else {
//            System.out.println("Episodio Nao Encontrado !");
//        }


//**********************************************************************************************************
//
//        System.out.println("A partir de que ano você deseja ver os episódios?");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano , 1, 1);
//
//
//        DateTimeFormatter formatadorDataBrasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(episodio -> episodio.getDataLancamento() != null && episodio.getDataLancamento().isAfter(dataBusca))
//                .forEach(episodio -> System.out.println(
//                        "Temporada: "+ episodio.getTemporada() +
//                                "Episodio: "+ episodio.getTitulo() +
//                                "Data de Lançamento" + episodio.getDataLancamento().format(formatadorDataBrasil)
//                ));
//**************************************************************************************************************

        System.out.println("Avaliação da Serie Por Temporada");
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(episodio -> episodio.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics estatisticas = episodios.stream()
                .filter(episodio -> episodio.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        DecimalFormat formatador = new DecimalFormat("#.##");
        System.out.println("Media: " + formatador.format(estatisticas.getAverage()));
    }
}
