package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.data.provider.ProdutoProvider;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class DeleteProdutoTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testValidarSchemaDeletarProdutoComSucesso(){

        produtoClient.deletarProduto(ProdutoDataFactory.getIdValido(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/excluir_produto.json"))
        ;
    }

    @Test
    public void testDeletarProdutoComSucesso(){

        produtoClient.deletarProduto(ProdutoDataFactory.getIdValido(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(200)
                .body("message", Matchers.equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testDeletarProdutoUsuarioNaoAdmin(){

        produtoClient.deletarProduto(ProdutoDataFactory.getIdValido(), LoginDataFactory.getAuthorizationKeyUsuario())
        .then()
                .statusCode(403)
                .body("message", Matchers.equalTo("Rota exclusiva para administradores"))
        ;
    }

    @Test
    public void testDeletarProdutoEmCarrinho(){

        produtoClient.deletarProduto(ProdutoDataFactory.getIdProdutoEmCarrinho(), LoginDataFactory.getAuthorizationKeyAdmin())
        .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Não é permitido excluir produto que faz parte de carrinho"))
        ;
    }
}
