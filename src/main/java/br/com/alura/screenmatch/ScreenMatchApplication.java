package br.com.alura.screenmatch;

import br.com.alura.screenmatch.main.Main;
import br.com.alura.screenmatch.model.Episodio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		Main main = new Main();
		main.exibirMenu();



//		// Consumir dados do Endereço do Epsodio
//
//		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=80c597c9");
//
//		// Converter dados Epsodio
//		DadosEpisodio dadosEpisodio = converte.obterDados(json, DadosEpisodio.class);
//		System.out.println(dadosEpisodio);
//
//
//		List<DadosTemporada> temporadas = new ArrayList<>();
//		for (int temporada = 1; temporada <= dadosSerie.totalTemporadas() ; temporada++)
//		{
//			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season="+ temporada +"&apikey=80c597c9");
//			DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}
//
//		temporadas.forEach(System.out::println);





	}
}
