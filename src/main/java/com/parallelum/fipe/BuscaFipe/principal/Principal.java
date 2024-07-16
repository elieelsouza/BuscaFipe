package com.parallelum.fipe.BuscaFipe.principal;

import com.parallelum.fipe.BuscaFipe.model.Veiculo;
import com.parallelum.fipe.BuscaFipe.model.Modelos;
import com.parallelum.fipe.BuscaFipe.model.Dados;
import com.parallelum.fipe.BuscaFipe.service.ConsumoAPI;
import com.parallelum.fipe.BuscaFipe.service.ConverteDados;

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
        var marcas = conversor.obterDadosList(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca desejada:");
        var cdMarca = Principal.scanner.nextLine();

        endereco = endereco + String.format("/%s/modelos", cdMarca);
        json = consumoAPI.obterDados(endereco);
        Modelos modelos = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca:");
        modelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o trecho do nome do veiculo para consulta: ");
        var nmVeiculo = scanner.nextLine();

        modelos.modelos().stream()
                .collect(Collectors.toList()).stream()
                .filter(m -> m.nome().toLowerCase().contains(nmVeiculo.toLowerCase()))
                .forEach(System.out::println);

        System.out.println("Informe o codigo de um carro para consultar: ");
        var cdCarro = scanner.nextLine();

        endereco = endereco + String.format("/%s/anos", cdCarro);
        json = consumoAPI.obterDados(endereco);
        List<Dados> anos = conversor.obterDadosList(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (Dados ano: anos){
            json = consumoAPI.obterDados(endereco + "/" + ano.codigo());
            veiculos.add(conversor.obterDados(json, Veiculo.class));
        }
        System.out.println("Todos os veiculos filtrados com avaliacoes por ano:");
        veiculos.forEach(System.out::println);
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
