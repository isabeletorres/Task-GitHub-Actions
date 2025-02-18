package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.Login;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;
import jdk.jfr.Label;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private LoginClient loginClient = new LoginClient();

    @Test
    @Story("Validar schema de usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de login está correto")
    @Label("Contrato")
    public void testValidarSchemaLoginComSucesso(){

        Login login = LoginDataFactory.loginValido();

        loginClient.loginUsuario(login)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/login.json"))
                .assertThat()
                .statusCode(200)
        ;
    }

    @Test
    @Story("Validar login com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o login está correto")
    @Label("Autenticação")
    public void testLoginComSucesso(){

        Login login = LoginDataFactory.loginValido();

        loginClient.loginUsuario(login)
        .then()
                .body("message", Matchers.equalTo("Login realizado com sucesso"))
                .body("authorization", Matchers.notNullValue())
                .statusCode(200)
        ;
    }

    @Test
    @Story("Validar login inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida tentativa de login com senha inválida")
    @Label("Autenticação")
    public void testLoginComSenhaInvalida(){

        Login login = LoginDataFactory.loginSenhaInvalida();

        loginClient.loginUsuario(login)
        .then()
                .body("message", Matchers.endsWithIgnoringCase("inválidos"))
                .statusCode(401)
        ;
    }

    @Test
    @Story("Validar login inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida tentativa de login com email inválido")
    @Label("Autenticação")
    public void testLoginComEmailInvalido(){

        Login login = LoginDataFactory.loginEmailInvalido();

        loginClient.loginUsuario(login)
        .then()
                .body("message", Matchers.endsWithIgnoringCase("inválidos"))
                .statusCode(401)
        ;
    }

}
