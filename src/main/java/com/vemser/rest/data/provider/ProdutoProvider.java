package com.vemser.rest.data.provider;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static com.vemser.rest.data.factory.ProdutoDataFactory.*;

public class ProdutoProvider {

    public static Stream<Arguments> produtosProvider(){
        return Stream.of(
                Arguments.of(produtoValido(), "message", 201, "Cadastro realizado com sucesso"),
                Arguments.of(produtoSemNome(), "nome", 400, "nome não pode ficar em branco"),
                Arguments.of(produtoSemPreco(), "preco", 400,"preco deve ser um número positivo"),
                Arguments.of(produtoSemDescricao(), "descricao", 400,"descricao não pode ficar em branco"),
                Arguments.of(produtoSemQuantidade(), "message", 201, "Cadastro realizado com sucesso")
        );
    }




}
