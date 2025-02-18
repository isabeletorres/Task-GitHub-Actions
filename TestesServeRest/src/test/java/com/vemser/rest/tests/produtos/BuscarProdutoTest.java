package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.data.provider.ProdutoProvider;
import com.vemser.rest.model.Produto;
import com.vemser.rest.model.ProdutoResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BuscarProdutoTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testValidarSchemaListarProdutosComSucesso(){

        produtoClient.listarProduto()
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/listar_produtos.json"))
        ;
    }

    @Test
    public void testListarProdutosComSucesso(){

        Response response =
        produtoClient.listarProduto()
        .then()
                .statusCode(200)
                .extract().response()
        ;
        assertThat("Quantidade de produtos correta", response.path("produtos.size()"), equalTo(response.path("quantidade")));
    }

    @Test
    public void testListarProdutoComPrecoInvalido(){

        Produto produto = ProdutoDataFactory.produtoComPrecoInvalido();

        produtoClient.listarProdutoPorPreco(produto.getPreco())
        .then()
                .statusCode(400)
                .body("preco", Matchers.equalTo("preco deve ser um número positivo"))
        ;
    }

    @Test
    public void testListarProdutoComQuantidadeInvalida(){

        Produto produto = ProdutoDataFactory.produtoComQuantidadeInvalido();

        produtoClient.listarProdutoPorQuantidade(produto.getQuantidade())
                .then()
                .statusCode(400)
                .body("quantidade", Matchers.equalTo("quantidade deve ser maior ou igual a 0"))
        ;
    }

    @Test
    public void testValidarSchemaBuscarPorIdComSucesso(){

        ProdutoResponse produtoResponse = ProdutoDataFactory.getProdutoResponseValido();

        produtoClient.buscarProduto(produtoResponse.getId())
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/buscar_produtos_por_id.json"))
        ;
    }


    @Test
    public void testBuscarPorIdComSucesso(){

        ProdutoResponse produtoResponse = ProdutoDataFactory.getProdutoResponseValido();

        ProdutoResponse response =
        produtoClient.buscarProduto(produtoResponse.getId())
        .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .extract().as(ProdutoResponse.class)
        ;

        Assertions.assertAll("response",
                ()-> Assertions.assertEquals(produtoResponse.getId(), response.getId()),
                ()-> Assertions.assertEquals(produtoResponse.getNome(), response.getNome()),
                ()-> Assertions.assertEquals(produtoResponse.getDescricao(), response.getDescricao()),
                ()-> Assertions.assertEquals(produtoResponse.getPreco(), response.getPreco()),
                ()-> Assertions.assertEquals(produtoResponse.getQuantidade(), response.getQuantidade())
                );
    }

    @Test
    public void testBuscarPorIdVazio(){

        produtoClient.buscarProduto(ProdutoDataFactory.getIdVazio())
        .then()
                .statusCode(400)
                .body("message", Matchers.containsStringIgnoringCase("não encontrado"))
        ;
    }

    @Test
    public void testBuscarPorIdInvalido(){

        produtoClient.buscarProduto(ProdutoDataFactory.getIdInvalido())
        .then()
                .statusCode(400)
                .body("message", Matchers.containsStringIgnoringCase("não encontrado"))
        ;
    }
}
