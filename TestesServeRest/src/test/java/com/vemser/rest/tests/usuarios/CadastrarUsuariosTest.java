package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;
import jdk.jfr.Label;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CadastrarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @Story("Validar schema de cadastrar usuários com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de cadastrar usuários está correto")
    @Label("Contrato")
    public void testValidarSchemaCadastroComSucesso(){

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/cadastro_usuario.json"))
        ;
    }

    @Test
    @Story("Validar cadastro de usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar cadastro de usuário com sucesso")
    @Label("Autenticação")
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
    @Story("Validar cadastro de usuário com dados inválidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar cadastro de usuário com email duplicado")
    @Label("Autenticação")
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
    @Story("Validar cadastro de usuário com dados inválidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validar cadastro de usuário sem email")
    @Label("Autenticação")
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
