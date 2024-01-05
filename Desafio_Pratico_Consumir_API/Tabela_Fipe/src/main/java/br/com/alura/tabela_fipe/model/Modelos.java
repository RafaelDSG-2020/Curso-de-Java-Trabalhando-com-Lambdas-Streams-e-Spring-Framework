package br.com.alura.tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos (List<DadosVeiculos> modelos) {
}
