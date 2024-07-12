package com.parallelum.fipe.BuscaFipe.service;

public interface IConverteDados {
 <T> T obterDados(String json, Class<T> classe);
}
