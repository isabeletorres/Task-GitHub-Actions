package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CadastrarProdutoTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testValidarSchemaCadastrarProdutoComSucesso() {

        Produto produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProduto(produto, LoginDataFactory.getAuthorizationKeyAdmin())
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cadastro_produto.json"))
        ;
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#produtosProvider")
    public void testCadastroProdutos(Produto produto, String expectedKey, int statusCode, String expectedResponse){

        produtoClient.cadastrarProdutos(produto, expectedKey, expectedResponse, statusCode,  LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(statusCode)
                .body(expectedKey, Matchers.equalTo(expectedResponse))
        ;
    }

    @Test
    public void testCadastroProdutoUsuarioNaoAdmin(){
        Produto produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProduto(produto, LoginDataFactory.getAuthorizationKeyUsuario())
        .then()
                .statusCode(403)
                .body("message", Matchers.equalTo("Rota exclusiva para administradores"))
        ;
    }
}
