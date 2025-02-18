package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioResponse;
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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class BuscarUsuariosTest {

    UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @Story("Validar schema de listar usuários com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de listar usuários está correto")
    @Label("Contrato")
    public void testValidarSchemaListarTodosUsuariosSemFiltroComSucesso(){

        usuarioClient.listarUsuarios()
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/listar_usuarios.json"))
                .statusCode(200)
        ;
    }

    @Test
    @Story("Validar listar usuários com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar listar usuários com sucesso")
    @Label("Usuário")
    public void testListarTodosUsuariosSemFiltroComSucesso(){

        Response response =
        usuarioClient.listarUsuarios()
        .then()
                .statusCode(200)
                .extract()
                .response()
        ;
        assertThat("Quantidade usuários esperada",response.path("usuarios.size()"), equalTo(response.path("quantidade")));
    }

    @Test
    @Story("Validar listar usuários com parâmetro inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar listar usuários com email inválido")
    @Label("Usuário")
    public void testListarUsuariosPorEmailInvalido(){

        String email = UsuarioDataFactory.gerarEmailInvalido();

        usuarioClient.listarUsuariosPorEmail(email)
        .then()
                .body("email", Matchers.equalTo("email deve ser um email válido"))
                .statusCode(400)
        ;
    }

    @Test
    @Story("Validar listar usuários com parâmetro inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar listar usuários com ID inválido")
    @Label("Usuário")
    public void testListarUsuariosPorIDInvalido(){

        String id = UsuarioDataFactory.gerarIdInvalido();

        Response response =
        usuarioClient.listarUsuariosPorId(id)
        .then()
                .statusCode(200)
                .extract().response();
        ;
        assertThat("Quantidade usuarios esperada", response.path("usuarios.size()"), equalTo(response.path("quantidade")));
    }

    @Test
    @Story("Validar schema de buscar usuários por ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar contrato de busca de usuario por ID")
    @Label("Contrato")
    public void testValidarSchemaBuscarUsuariosPorIDValidoComSucesso(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
        ;
    }

    @Test
    @Story("Validar busca de usuários por ID com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar tentar busca de usuários por ID com sucesso")
    @Label("Usuário")
    public void testBuscarUsuariosPorIDValidoComSucesso(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        UsuarioResponse response =
        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .extract().as(UsuarioResponse.class)
        ;

        Assertions.assertAll("response",
                ()-> Assertions.assertEquals(UsuarioDataFactory.getUsuarioResponseValido().getNome(), response.getNome()),
                ()-> Assertions.assertEquals(UsuarioDataFactory.getUsuarioResponseValido().getEmail(), response.getEmail()),
                ()-> Assertions.assertEquals(UsuarioDataFactory.getUsuarioResponseValido().getPassword(), response.getPassword()),
                ()-> Assertions.assertEquals(UsuarioDataFactory.getUsuarioResponseValido().getAdministrador(), response.getAdministrador()),
                ()-> Assertions.assertEquals(UsuarioDataFactory.getUsuarioResponseValido().get_id(), response.get_id())
        );
    }

    @Test
    @Story("Validar busca de usuários por ID com dados inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar tentar busca de usuários por ID com ID inválido")
    @Label("Usuário")
    public void testBuscarUsuariosPorIDInvalido(){

        String idUsuario = UsuarioDataFactory.gerarIdInvalido();

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(400)
                .assertThat()
                .body("message", containsStringIgnoringCase("Usuário não encontrado"))
        ;
    }

    @Test
    @Story("Validar busca de usuários por ID com dados inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar tentar busca de usuários por ID inexistente")
    @Label("Usuário")
    public void testBuscarUsuariosPorIDInexistente(){

        String idUsuario = UsuarioDataFactory.gerarIdInexistente();

        Response response =
        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(400)
                .extract().response()
        ;
        assertThat(response.path("message"), containsStringIgnoringCase("Usuário não encontrado"));
    }
}
