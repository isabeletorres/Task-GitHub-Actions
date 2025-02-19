package com.vemser.rest.client;

import com.vemser.rest.model.Produto;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ProdutoClient extends BaseClient{

    private String ENDPOINT_PRODUTOS = "/produtos";
    private String ENDPOINT_PRODUTOS_ID = "/produtos/{_id}";

    public Response cadastrarProduto(Produto produto, String token){
        return
        given()
                .spec(super.set())
                .header("Authorization", token)
                .body(produto)
        .when()
                .post(ENDPOINT_PRODUTOS)
        ;
    }

    public Response cadastrarProdutos(Produto produto, String expectedKey, String expectedResponse , Integer status, String token){
        return
        given()
                .spec(super.set())
                .header("Authorization", token)
                .body(produto)
        .when()
                .post(ENDPOINT_PRODUTOS)
        ;
    }

    public Response listarProduto(){
        return
        given()
                .spec(super.set())
        .when()
                .get(ENDPOINT_PRODUTOS)
        ;
    }

    public Response listarProdutoPorPreco(int preco){
        return
        given()
                .spec(super.set())
                .queryParam("preco", preco)
        .when()
                .get(ENDPOINT_PRODUTOS)
        ;
    }

    public Response listarProdutoPorQuantidade(int quantidade){
        return
        given()
                .spec(super.set())
                .queryParam("quantidade", quantidade)
        .when()
                .get(ENDPOINT_PRODUTOS)
        ;
    }

    public Response buscarProduto(String id){
        return
        given()
                 .spec(super.set())
                 .pathParam("_id", id)
        .when()
                 .get(ENDPOINT_PRODUTOS_ID)
        ;
    }

    public Response atualizarProduto(String id, Produto produto, String token){
        return
        given()
                .spec(super.set())
                .header("Authorization", token)
                .pathParam("_id", id)
                .body(produto)
        .when()
                .put(ENDPOINT_PRODUTOS_ID)
        ;
    }

    public Response deletarProduto(String id, String token){
        return
        given()
                .spec(super.set())
                .header("Authorization", token)
                .pathParam("_id", id)
        .when()
                .delete(ENDPOINT_PRODUTOS_ID)
        ;
    }
}
