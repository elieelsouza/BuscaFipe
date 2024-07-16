package com.parallelum.fipe.BuscaFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(String codigo, String nome) {

    @Override
    public String toString(){
        return ""+
                "Cód: "+ codigo + " Descrição: " + nome;
    }
}
