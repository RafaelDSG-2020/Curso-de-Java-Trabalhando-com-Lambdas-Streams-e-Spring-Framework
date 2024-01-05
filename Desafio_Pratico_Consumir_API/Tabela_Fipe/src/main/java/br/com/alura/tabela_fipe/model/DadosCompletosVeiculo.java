package br.com.alura.tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosCompletosVeiculo(@JsonAlias("TipoVeiculo") int tipoVeiculo,
                                    @JsonAlias("Valor") String valor,
                                    @JsonAlias("Marca") String marca,
                                    @JsonAlias("Modelo") String modelo,
                                    @JsonAlias("AnoModelo") int anoModelo,
                                    @JsonAlias("Combustivel") String combustivel,
                                    @JsonAlias("CodigoFipe") String codigoFipe,
                                    @JsonAlias("MesReferencia") String mesReferencia,
                                    @JsonAlias("SiglaCombustivel") String siglaCombustivel) {
    public String toString() {
        return String.format(
                "Tipo Veículo: %d\nValor: %s\nMarca: %s\nModelo: %s\nAno Modelo: %d\nCombustível: %s\nCódigo Fipe: %s\nMês Referência: %s\nSigla Combustível: %s",
                tipoVeiculo, valor, marca, modelo, anoModelo, combustivel, codigoFipe, mesReferencia, siglaCombustivel
        );
    }



}
