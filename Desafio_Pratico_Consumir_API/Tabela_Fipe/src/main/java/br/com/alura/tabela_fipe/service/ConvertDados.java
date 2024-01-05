package br.com.alura.tabela_fipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class ConvertDados implements IConsumeDados{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDadosModelos(String json, TypeReference<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterDados(String json, TypeReference<List<T>> tClass) {
        try {
            return  mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
