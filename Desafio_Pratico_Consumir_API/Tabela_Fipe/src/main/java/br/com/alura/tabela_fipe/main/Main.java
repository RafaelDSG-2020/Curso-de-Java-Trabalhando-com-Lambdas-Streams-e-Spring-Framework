package br.com.alura.tabela_fipe.main;

import br.com.alura.tabela_fipe.model.DadosCompletosVeiculo;
import br.com.alura.tabela_fipe.model.DadosVeiculos;
import br.com.alura.tabela_fipe.model.Modelos;
import br.com.alura.tabela_fipe.service.ConsumoApi;
import br.com.alura.tabela_fipe.service.ConvertDados;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Scanner;

public class Main {

    ConsumoApi consumoApi = new ConsumoApi();
    ConvertDados converte = new ConvertDados();
    private Scanner entrada = new Scanner(System.in);
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";


    public void Menu(){


        System.out.println("Carros");
        System.out.println("Motos");
        System.out.println("Caminhões");
        System.out.println("Escolha o tipo do veiculo: ");
        String veiculo = entrada.nextLine();

        try {
            String json = consumoApi.obterDados(ENDERECO+ veiculo.toLowerCase()+ "/marcas" );
            List<DadosVeiculos> dadosCarros = converte.obterDados(json,new TypeReference<List<DadosVeiculos>>() {});
            dadosCarros.forEach(carros -> System.out.println("Código: " + carros.codigo() + ", Nome: " + carros.nome()));

//###########################################################################################################################################################################
            System.out.println("Escolha o codigo do Modelo listado acima :");
            int modelos = entrada.nextInt();
            json = consumoApi.obterDados(ENDERECO+ veiculo.toLowerCase()+"/marcas/"+modelos+"/modelos" );
            Modelos modelosVeiculos = converte.obterDadosModelos(json, new TypeReference<Modelos>() {});
            modelosVeiculos.modelos().forEach(dv -> System.out.println("Código: " + dv.codigo() + ", Nome: " + dv.nome()));

//#######################################################################################################################################################

            System.out.println("Escolha o codigo do Carro: ");
            int codigoCarro = entrada.nextInt();
            String endereco = ENDERECO+ veiculo.toLowerCase()+"/marcas/"+modelos+"/modelos/";
            json = consumoApi.obterDados(endereco+codigoCarro+"/anos");
            List<DadosVeiculos> listaVeiculos = converte.obterDados(json, new TypeReference<List<DadosVeiculos>>() {});
            listaVeiculos.forEach(dv -> System.out.println("Código: " + dv.codigo() + ", Nome: " + dv.nome()));
//#############################################################################################################################################

            System.out.println("Escolha o Ano do Veiculo: ");
            entrada.nextLine();
            String ano = entrada.nextLine();
            json = consumoApi.obterDados(endereco+codigoCarro+"/anos/"+ano);
            DadosCompletosVeiculo caracteristicas = converte.obterDadosModelos(json, new TypeReference<DadosCompletosVeiculo>(){} );
            System.out.println(caracteristicas);




         } catch (Exception e) {
            System.out.println("Escrita incorreta observe as opções listadas");
        }




    }
}
