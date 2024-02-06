package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import ch.qos.logback.core.net.SyslogOutputStream;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=80c597c9";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repository;

    private List<Serie> series= new ArrayList<>();

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Series Buscadas
                    4 - Buscar Serie por Titulo
                    5 - Buscar Series por Ator
                    6 - Top 5 Series
                    7 - Buscando Series por Categoria
                    8 - Buscando Series por Quantidade Maximas de Temporadas e Valor Minimo de Avaliação
                    9 - Buscando Epsodios por Trecho
                                        
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    listarAsCincoMelhoresSeries();
                    break;
                case 7:
                    buscandoSeriesPorCategoria();
                    break;
                case 8:
                    buscandoPorMaxTemporadaeAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }




    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repository.save(serie);
        //dadosSeries.add(dados);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {

        listarSeriesBuscadas();
        System.out.println("Escolha uma Serie Pelo Nome: ");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

//        Optional<Serie> serie = series.stream()
//                .filter(series -> series.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodioList = temporadas.stream()
                    .flatMap(dadosTemporada -> dadosTemporada.episodios().stream()
                            .map(e -> new Episodio(dadosTemporada.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodioList);
            repository.save(serieEncontrada);

        }
        else {

            System.out.println("Serie não foi Encontrada");
        }


    }

    private void listarSeriesBuscadas() {

        series = repository.findAll();
        series.stream()
                        .sorted(Comparator.comparing(Serie::getGenero))
                                .forEach(System.out::println);
        //dadosSeries.forEach(System.out::println);

    }
    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma Serie Pelo Nome: ");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()){
            System.out.println("Dados da série: " + serieBuscada.get());


        }else {
            System.out.println("Serie Não Encontrada!");
        }

    }
    private void buscarSeriesPorAtor() {
        System.out.println("Pesquise Pelo Nome do Ator: ");
        String nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de qual valor? : ");
        Double avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradasFeitasPorAtor = repository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor,avaliacao);
        seriesEncontradasFeitasPorAtor.forEach(serie ->
                System.out.println(serie.getTitulo() + "Avalaição: " +serie.getAvaliacao()));
    }

    private void listarAsCincoMelhoresSeries() {
        List<Serie> seriesTop = repository.findTop5ByOrderByAvaliacaoDesc();
        seriesTop.forEach(serie ->
                System.out.println(serie.getTitulo() + " |-> Avalaição: " +serie.getAvaliacao()));
    }
    private void buscandoSeriesPorCategoria() {

        System.out.println("Exemplos de Categoria: ");
        Categoria[] genero1 = Categoria.values();
        Arrays.stream(genero1).forEach(System.out::println);
        System.out.println("Digite o Genero das Series que Deseja buscar: ");
        String nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("Series da Categoria -> " + nomeGenero);
        seriesPorCategoria.forEach(serie ->
                System.out.println(serie.getTitulo() + " |-> Avalaição: " +serie.getAvaliacao()));

    }
    private void buscandoPorMaxTemporadaeAvaliacao() {
        System.out.println("Maxima top Temporadas e Minimo de Avaliação");
        int temporada = leitura.nextInt();
        double avaliacao = leitura.nextDouble();

        List<Serie> serieTemp = repository.seriesPorTemporadaEAvaliacao(temporada,avaliacao);
        serieTemp.forEach(serie ->
                System.out.println(serie.getTitulo() + " Total Temporadas: "+ serie.getTotalTemporadas() + " |-> Avalaição: " +serie.getAvaliacao()));

    }

    private void buscarEpisodioPorTrecho() {

        System.out.println("Qual o nome do episódio para a busca? ");
        String trechoEpisodio = leitura.nextLine();

        List<Episodio> epsodiosEncontrados = repository.episodiosPorTrecho(trechoEpisodio);

        epsodiosEncontrados.forEach(episodio ->
                System.out.printf("Serie: %s Temporada %s - Episodio %s \n",
                episodio.getSerie().getTitulo(), episodio.getTemporada(),episodio.getNumeroEpisodio(),
                episodio.getTitulo()));

    }

}
