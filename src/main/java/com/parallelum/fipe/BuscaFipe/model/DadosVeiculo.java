package com.parallelum.fipe.BuscaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(String codigo, String nome) {

    @Override
    public String toString(){
        return ""+
                "Cód: "+ codigo + " Descrição: " + nome;
    }
}
