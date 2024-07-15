package com.parallelum.fipe.BuscaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterDadosList(String json, Class<T> classe, boolean bidimensional) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);

        try{
            if (!bidimensional)
                return mapper.readValue(json, lista);
            else {
                CollectionType listBi = mapper.getTypeFactory()
                        .constructCollectionType(List.class, lista);
                return mapper.readValue(json, listBi);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
