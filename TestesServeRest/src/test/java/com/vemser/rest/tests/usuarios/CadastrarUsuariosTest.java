package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CadastrarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testValidarSchemaCadastroComSucesso(){

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cadastro_usuario.json"))
        ;
    }

    @Test
    public void testDeveCadastrarUsuarioComSucesso(){

        Usuario usuairo = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuairo)
        .then()
                .statusCode(201)
                .body("message", Matchers.equalTo("Cadastro realizado com sucesso"))
                .header("Date", Matchers.notNullValue())
                .body("_id", Matchers.notNullValue())
                .body("_id", Matchers.hasLength(16))
        ;
    }

    @Test
    public void testDeveCadastrarUsuarioComEmailDuplicado(){

        Usuario usuario = UsuarioDataFactory.usuarioEmailDuplicado();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .body("message", Matchers.equalTo("Este email já está sendo usado"))
                .header("Date", Matchers.notNullValue())
                .statusCode(400)
        ;
    }

    @Test
    public void testDeveCadastrarUsuarioSemEmail(){

        Usuario usuario = UsuarioDataFactory.usuarioSemEmail();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .body("email", (Matchers.equalTo("email não pode ficar em branco")))
                .header("Date", Matchers.notNullValue())
                .statusCode(400)
        ;
    }

}
