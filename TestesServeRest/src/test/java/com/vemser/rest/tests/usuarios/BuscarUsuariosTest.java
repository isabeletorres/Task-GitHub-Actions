package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class BuscarUsuariosTest {

    UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testValidarSchemaListarTodosUsuariosSemFiltroComSucesso(){

        usuarioClient.listarUsuarios()
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/listar_usuarios.json"))
                .statusCode(200)
        ;
    }

    @Test
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
    public void testListarUsuariosPorEmailInvalido(){

        String email = UsuarioDataFactory.gerarEmailInvalido();

        usuarioClient.listarUsuariosPorEmail(email)
        .then()
                .body("email", Matchers.equalTo("email deve ser um email válido"))
                .statusCode(400)
        ;
    }

    @Test
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
    public void testValidarSchemaBuscarUsuariosPorIDValidoComSucesso(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        usuarioClient.buscarUsuariosPorId(idUsuario)
        .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
        ;
    }

    @Test
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
