package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.data.provider.ProdutoProvider;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class AtualizarProdutoTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testValidarSchemaAtualizarProdutoComSucesso(){

        produtoClient.atualizarProduto(ProdutoDataFactory.getIdValido(), ProdutoDataFactory.produtoValido(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/editar_produto.json"))
        ;
    }

    @Test
    public void testAtualizarProdutoComSucesso(){

        produtoClient.atualizarProduto(ProdutoDataFactory.getIdValido(), ProdutoDataFactory.produtoValido(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("message", Matchers.equalTo("Registro alterado com sucesso"))
        ;
    }


    @Test
    public void testAtualizarProdutoComNomeDuplicado(){
        produtoClient.atualizarProduto(ProdutoDataFactory.getIdValidoAleatorio(), ProdutoDataFactory.getProdutoComNomeDuplicado(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(400)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("message", Matchers.equalTo("Já existe produto com esse nome"))
        ;
    }

    @Test
    public void testAtualizarProdutoComUsuarioNaoAdmin(){

        produtoClient.atualizarProduto(ProdutoDataFactory.getIdValido(), ProdutoDataFactory.produtoValido(), LoginDataFactory.getAuthorizationKeyUsuario())
        .then()
                .statusCode(403)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("message", Matchers.equalTo("Rota exclusiva para administradores"))
        ;
    }
}
