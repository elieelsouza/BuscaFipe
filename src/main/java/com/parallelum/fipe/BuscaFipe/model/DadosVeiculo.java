package com.parallelum.fipe.BuscaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(
        @JsonAlias("codigo") Integer codigoVeiculo,
        @JsonAlias("nome") String nomeVeiculo
) {
}
