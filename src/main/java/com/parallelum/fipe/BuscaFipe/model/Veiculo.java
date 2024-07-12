package com.parallelum.fipe.BuscaFipe.model;

public class Veiculo {
    private Integer codigoVeiculo;
    private String nomeVeiculo;

    public Veiculo(DadosVeiculo dadosVeiculo){
        this.codigoVeiculo = dadosVeiculo.codigoVeiculo();
        this.nomeVeiculo = dadosVeiculo.nomeVeiculo();
    }

    public Integer getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Integer codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public String getNomeVeiculo() {
        return nomeVeiculo;
    }

    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }
}
