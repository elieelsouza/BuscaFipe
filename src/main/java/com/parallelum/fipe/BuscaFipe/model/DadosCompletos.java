package com.parallelum.fipe.BuscaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCompletos(@JsonAlias("Valor") String valor,
                             @JsonAlias("Marca") String marca,
                             @JsonAlias("Modelo") String modelo,
                             @JsonAlias("AnoModelo") String ano,
                             @JsonAlias("Combustivel") String combustivel) {

    @Override
    public String toString(){
        return marca + " " + modelo + " ano: " + ano + " valor: " + valor + " combustivel: " + combustivel;
    }
}
