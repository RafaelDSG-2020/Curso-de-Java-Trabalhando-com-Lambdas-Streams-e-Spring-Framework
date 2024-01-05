package br.com.alura.tabela_fipe;

import br.com.alura.tabela_fipe.main.Main;
import br.com.alura.tabela_fipe.model.DadosVeiculos;
import br.com.alura.tabela_fipe.service.ConsumoApi;
import br.com.alura.tabela_fipe.service.ConvertDados;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		new Main().Menu();


	}
}
