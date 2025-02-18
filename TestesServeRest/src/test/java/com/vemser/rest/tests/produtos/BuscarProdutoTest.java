package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.data.provider.ProdutoProvider;
import com.vemser.rest.model.Produto;
import com.vemser.rest.model.ProdutoResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import jdk.jfr.Label;
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
    @Story("Validar schema de listar produtos com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de listagem de produtos está correto")
    @Label("Contrato")
    public void testValidarSchemaListarProdutosComSucesso(){

        produtoClient.listarProduto()
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/listar_produtos.json"))
        ;
    }

    @Test
    @Story("Validar listagem de produtos com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida listagem de produtos com sucesso.")
    @Label("Produto")
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
    @Story("Validar busca de produto inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida busca de produto com preço inválido")
    @Label("Produto")
    public void testListarProdutoComPrecoInvalido(){

        Produto produto = ProdutoDataFactory.produtoComPrecoInvalido();

        produtoClient.listarProdutoPorPreco(produto.getPreco())
        .then()
                .statusCode(400)
                .body("preco", Matchers.equalTo("preco deve ser um número positivo"))
        ;
    }

    @Test
    @Story("Validar busca de produto inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida busca de produto com quantidade inválida")
    @Label("Produto")
    public void testListarProdutoComQuantidadeInvalida(){

        Produto produto = ProdutoDataFactory.produtoComQuantidadeInvalido();

        produtoClient.listarProdutoPorQuantidade(produto.getQuantidade())
                .then()
                .statusCode(400)
                .body("quantidade", Matchers.equalTo("quantidade deve ser maior ou igual a 0"))
        ;
    }

    @Test
    @Story("Validar schema de busca de produto por ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar contrato de busca de produto por ID")
    @Label("Contrato")
    public void testValidarSchemaBuscarPorIdComSucesso(){

        ProdutoResponse produtoResponse = ProdutoDataFactory.getProdutoResponseValido();

        produtoClient.buscarProduto(produtoResponse.getId())
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/buscar_produtos_por_id.json"))
        ;
    }


    @Test
    @Story("Validar busca de produto por ID com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar busca de produto por ID com sucesso")
    @Label("Produto")
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
    @Story("Validar busca de produto por ID inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar busca de produto por ID vazio")
    @Label("Produto")
    public void testBuscarPorIdVazio(){

        produtoClient.buscarProduto(ProdutoDataFactory.getIdVazio())
        .then()
                .statusCode(400)
                .body("message", Matchers.containsStringIgnoringCase("não encontrado"))
        ;
    }

    @Test
    @Story("Validar busca de produto por ID inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar busca de produto por ID inválido")
    @Label("Produto")
    public void testBuscarPorIdInvalido(){

        produtoClient.buscarProduto(ProdutoDataFactory.getIdInvalido())
        .then()
                .statusCode(400)
                .body("message", Matchers.containsStringIgnoringCase("não encontrado"))
        ;
    }
}
