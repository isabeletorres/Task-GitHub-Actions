package com.vemser.rest.data.factory;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.model.Login;
import com.vemser.rest.utils.ConfigUtils;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.Properties;

public class LoginDataFactory {

    static Faker faker = new Faker();
    static LoginClient loginClient = new LoginClient();

    public static Login loginValido(){
        return novoLoginAdmin();
    }

    public static Login loginSenhaInvalida(){
        Login login = novoLoginAdmin();
        login.setPassword(faker.internet().password());
        return login;
    }

    public static Login loginEmailInvalido(){
        Login login = novoLoginAdmin();
        login.setEmail(faker.internet().emailAddress());
        return login;
    }

    public static String getAuthorizationKeyAdmin(){
        Login login = novoLoginAdmin();
        Response response =
        loginClient.loginUsuario(login)
        .then()
                .extract()
                .response()
        ;
        return response.path("authorization");
    }

    public static String getAuthorizationKeyUsuario(){
        Login login = novoLoginUsuario();
        Response response =
        loginClient.loginUsuario(login)
        .then()
                .extract()
                .response()
        ;
        return response.path("authorization");
    }

    private static Login novoLoginAdmin(){
        Login login = new Login();

        Properties props = ConfigUtils.loadProperties();
        String email = props.getProperty("email");
        String password = props.getProperty("password");

        login.setEmail(email);
        login.setPassword(password);

        return login;
    }

    private static Login novoLoginUsuario(){
        Login login = new Login();

        Properties props = ConfigUtils.loadProperties();
        String email = props.getProperty("emailUsuario");
        String password = props.getProperty("passwordUsuario");

        login.setEmail(email);
        login.setPassword(password);

        return login;
    }
}
