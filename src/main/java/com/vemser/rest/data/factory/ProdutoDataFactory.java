package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.Produto;
import com.vemser.rest.model.ProdutoResponse;
import com.vemser.rest.utils.ConfigUtils;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class ProdutoDataFactory {

    static Faker faker = new Faker();
    static ProdutoClient produtoClient = new ProdutoClient();

    public static Produto produtoValido(){
        return novoProduto();
    }

    public static Produto produtoSemNome(){
        Produto produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        return produto;
    }

    public static Produto produtoSemPreco(){
        Produto produto = novoProduto();
        produto.setPreco(0);
        return produto;
    }

    public static Produto produtoSemDescricao(){
        Produto produto = novoProduto();
        produto.setDescricao(StringUtils.EMPTY);
        return produto;
    }

    public static Produto produtoSemQuantidade(){
        Produto produto = novoProduto();
        produto.setQuantidade(0);
        return produto;
    }

    public static Produto produtoComPrecoInvalido(){
        Produto produto = novoProduto();
        produto.setPreco(-1);
        return produto;
    }

    public static Produto produtoComQuantidadeInvalido(){
        Produto produto = novoProduto();
        produto.setQuantidade(-1);
        return produto;
    }

    public static Produto novoProduto(){
        Produto produto = new Produto();
        produto.setNome(faker.lorem().fixedString(20));
        produto.setDescricao(faker.lorem().fixedString(20));
        produto.setPreco(faker.number().numberBetween(50,1000));
        produto.setQuantidade(faker.number().numberBetween(10,99));
        return produto;
    }

    public static String getIdValido(){
        Response response =
                produtoClient.listarProduto()
                        .then()
                        .extract().response()
                ;
        return response.path("produtos[0]._id");
    }

    public static String getIdValidoAleatorio(){
        Response response =
                produtoClient.listarProduto()
                        .then()
                        .extract().response()
                ;
        int n = getQuantidadeTotalProdutosCadastrados() -1;
        System.out.println(n);
        return response.path("produtos[" + n +"]._id");
    }

    private static int getQuantidadeTotalProdutosCadastrados(){
        Response response =
                produtoClient.listarProduto()
                        .then()
                        .extract().response()
                ;
        int quantidade = response.path("quantidade");
        return quantidade;
    }

    public static ProdutoResponse getProdutoResponseValido(){
        String id = getIdValido();

        return
                produtoClient.buscarProduto(id)
                        .then()
                        .extract().as(ProdutoResponse.class)
                ;
    }

    public static String getIdVazio(){
        return " ";
    }

    public static String getIdInvalido(){
        return String.valueOf(faker.number().numberBetween(-100,-1));
    }

    public static String getIdProdutoEmCarrinho(){
        Properties prop =  ConfigUtils.loadProperties();
        return prop.getProperty("produtoNoCarrinhoId");
    }

    public static Produto getProdutoComNomeDuplicado(){
        Produto produto = produtoValido();
        produto.setNome(getProdutoResponseValido().getNome());
        return produto;
    }

}
