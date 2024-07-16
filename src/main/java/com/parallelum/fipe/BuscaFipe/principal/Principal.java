package com.parallelum.fipe.BuscaFipe.principal;

import com.parallelum.fipe.BuscaFipe.model.DadosCompletos;
import com.parallelum.fipe.BuscaFipe.model.DadosModelos;
import com.parallelum.fipe.BuscaFipe.model.DadosVeiculo;
import com.parallelum.fipe.BuscaFipe.service.ConsumoAPI;
import com.parallelum.fipe.BuscaFipe.service.ConverteDados;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    private static final ConsumoAPI consumoAPI =  new ConsumoAPI();
    private static final ConverteDados conversor =  new ConverteDados();


    public void exibeMenu(){
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("++++BUSCADOR DE PRECOS FIPE++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("Informe o tipo de veiculo (Carros | Motos | Caminhoes): ");
        var entrada = Principal.scanner.nextLine();
        var endereco = urlVeciulo(entrada);
        String json = consumoAPI.obterDados(endereco);
        var marcas = conversor.obterDadosList(json, DadosVeiculo.class, false);

        marcas.stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca desejada:");
        var marca = Principal.scanner.nextLine();
        endereco = endereco + String.format("/%s/modelos", marca);
        var jsonMarca = consumoAPI.obterDados(endereco);

        DadosModelos modelos = conversor.obterDados(jsonMarca, DadosModelos.class);
        modelos.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::codigo))
                .forEach(System.out::println);

        List<DadosVeiculo> veiculos = modelos.modelos().stream()
                        .collect(Collectors.toList());

        System.out.println("Informe o trecho do nome do veiculo para consulta: ");
        var trechoVeiculo = scanner.nextLine();

        veiculos.stream()
                .filter(e ->  e.nome().toLowerCase().contains(trechoVeiculo.toLowerCase()))
                .forEach(System.out::println);

        System.out.println("Informe o codigo de um carro para consultar: ");
        var cdCarro = scanner.nextLine();

        endereco = endereco + String.format("/%s/anos", cdCarro);
        var jsonDesCompletaVeiculo = consumoAPI.obterDados(endereco);
        var modelosAnosPorCarro = conversor.obterDadosList(jsonDesCompletaVeiculo, DadosVeiculo.class, false);

        List<DadosCompletos> modeloPorAno = new ArrayList<>();
        for (DadosVeiculo dados: modelosAnosPorCarro){
            var dadoConsulta = consumoAPI.obterDados(endereco + "/" + dados.codigo());
            modeloPorAno.add(conversor.obterDados(dadoConsulta, DadosCompletos.class));
        }
        System.out.println("Todos os veiculos filtrados com avaliacoes por ano:");
        modeloPorAno.forEach(System.out::println);
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
