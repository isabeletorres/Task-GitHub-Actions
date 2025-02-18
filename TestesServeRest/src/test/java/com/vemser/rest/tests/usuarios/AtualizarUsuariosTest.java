package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.datafaker.Faker;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtualizarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    Faker faker = new Faker();

    @Test
    public void testValidarSchemaAtualizarUsuarioComSucesso(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        Usuario usuarioAtualizado = UsuarioDataFactory.usuarioValido();

        usuarioClient.atualizarUsuario(idUsuario, usuarioAtualizado)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/editar_usuario.json"))
                .statusCode(200)
        ;
    }

    @Test
    public void testDeveAtualizarUsuarioComSucesso(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        Usuario usuarioAtualizado = UsuarioDataFactory.usuarioValido();

        usuarioClient.atualizarUsuario(idUsuario, usuarioAtualizado)
        .then()
                .statusCode(200)
                .body("message", Matchers.equalTo("Registro alterado com sucesso"))
        ;
    }

    @Test
    public void testDeveAtualizarUsuarioAlterandoAdminStatusSemAutenticacao(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        Usuario usuarioAtualizado = UsuarioDataFactory.gerarUsuarioAdminValido();

        usuarioClient.atualizarUsuario(idUsuario, usuarioAtualizado)
        .then()
                .body("message", Matchers.equalTo("Registro alterado com sucesso"))
                .statusCode(200)
        ;
    }

    @Test
    public void testDeveAtualizarUsuarioAlterandoApenasNome(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        Usuario usuarioAtualizado = UsuarioDataFactory.gerarUsuarioApenasNome();

        UsuarioResponse response =
        usuarioClient.atualizarUsuario(idUsuario, usuarioAtualizado)
        .then()
                .statusCode(400)
                .extract()
                    .as(UsuarioResponse.class)
        ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals("email não pode ficar em branco", response.getEmail()),
                () -> Assertions.assertEquals("password não pode ficar em branco", response.getPassword()),
                () -> Assertions.assertEquals("administrador deve ser 'true' ou 'false'", response.getAdministrador())
                );
    }


}
