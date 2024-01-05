package br.com.alura.tabela_fipe.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface IConsumeDados {
    <T> T obterDadosModelos(String json, TypeReference<T> typeReference);

    <T> List<T> obterDados(String json, TypeReference<List<T>> tClass);

}
