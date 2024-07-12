package com.parallelum.fipe.BuscaFipe.principal;

import com.parallelum.fipe.BuscaFipe.model.DadosVeiculo;
import com.parallelum.fipe.BuscaFipe.model.Veiculo;
import com.parallelum.fipe.BuscaFipe.service.ConsumoAPI;
import com.parallelum.fipe.BuscaFipe.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    private static final ConsumoAPI CONSUMO_API =  new ConsumoAPI();
    private static final ConverteDados CONVERSOR =  new ConverteDados();


    public void exibeMenu(){
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("++++BUSCADOR DE PRECOS FIPE++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("Informe o tipo de veiculo (Carro | Moto | Caminhao): ");
        var tpVeiculo = ENTRADA.nextLine();
        var urlVeiculo = URL_BASE + "/" + tpVeiculo.toLowerCase().replaceAll(" ", "+") + "/marcas";
        var json = CONSUMO_API.obterDados(urlVeiculo);
        DadosVeiculo veiculo = CONVERSOR.obterDados(json, DadosVeiculo.class);
        System.out.println(veiculo.toString());



    }


}
