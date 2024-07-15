package com.parallelum.fipe.BuscaFipe.principal;

import com.parallelum.fipe.BuscaFipe.model.DadosModelos;
import com.parallelum.fipe.BuscaFipe.model.DadosVeiculo;
import com.parallelum.fipe.BuscaFipe.service.ConsumoAPI;
import com.parallelum.fipe.BuscaFipe.service.ConverteDados;

import java.util.Comparator;
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
        System.out.println("Informe o tipo de veiculo (Carros | Motos | Caminhoes): ");
        var entrada = ENTRADA.nextLine();
        var endereco = urlVeciulo(entrada);
        String json = CONSUMO_API.obterDados(endereco);
        var marcas = CONVERSOR.obterDadosList(json, DadosVeiculo.class, false);

        marcas.stream()
                .sorted(Comparator.comparingInt(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca desejada:");
        var marca = ENTRADA.nextLine();
        var endMarca = endereco + String.format("/%s/modelos", marca);
        var jsonMarca = CONSUMO_API.obterDados(endMarca);
        System.out.println(jsonMarca);

        DadosModelos modelos = CONVERSOR.obterDados(jsonMarca, DadosModelos.class);
        modelos.modelos().stream()
                .sorted(Comparator.comparingInt(DadosVeiculo::codigo))
                .forEach(System.out::println);







    }

    private String urlVeciulo(String entrada) {
        String url = " ";
        if (entrada.toLowerCase().contains("carr")){
            url = URL_BASE + "/carros/marcas";
        } else if (entrada.toLowerCase().contains("mot")){
            url = URL_BASE + "/motos/marcas";
        } else {
            url = URL_BASE + "/caminhoes/marcas";
        }
        return url;
    }
}
