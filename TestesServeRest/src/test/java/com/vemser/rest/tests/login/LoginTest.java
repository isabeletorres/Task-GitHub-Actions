package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.Login;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private LoginClient loginClient = new LoginClient();

    @Test
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
    public void testLoginComSenhaInvalida(){

        Login login = LoginDataFactory.loginSenhaInvalida();

        loginClient.loginUsuario(login)
        .then()
                .body("message", Matchers.endsWithIgnoringCase("inválidos"))
                .statusCode(401)
        ;
    }

    @Test
    public void testLoginComEmailInvalido(){

        Login login = LoginDataFactory.loginEmailInvalido();

        loginClient.loginUsuario(login)
        .then()
                .body("message", Matchers.endsWithIgnoringCase("inválidos"))
                .statusCode(401)
        ;
    }

}
