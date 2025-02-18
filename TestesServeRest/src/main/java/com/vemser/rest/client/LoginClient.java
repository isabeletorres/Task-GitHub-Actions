package com.vemser.rest.client;

import com.vemser.rest.model.Login;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class LoginClient extends BaseClient{

    private final String ENDPOINT_LOGIN = "/login";

    public Response loginUsuario(Login login){

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(ENDPOINT_LOGIN)
                ;

    }
}
