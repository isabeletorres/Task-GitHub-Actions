package com.vemser.rest.client;

import com.vemser.rest.model.Usuario;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UsuarioClient extends BaseClient {

    private final String ENDPOINT_USUARIOS = "/usuarios";

    public Response cadastrarUsuarios(Usuario usuario){
        return
            given()
                    .spec(super.set())
                    .body(usuario)
            .when()
                    .post(ENDPOINT_USUARIOS)
            ;
    }

    public Response listarUsuarios(){
        return
                given()
                        .spec(super.set())
                .when()
                        .get(ENDPOINT_USUARIOS)
                ;
    }

    public Response listarUsuariosPorId(String id){
        return
                given()
                        .spec(super.set())
                        .queryParam("_id", id)
                .when()
                        .get(ENDPOINT_USUARIOS)
                ;
    }

    public Response listarUsuariosPorEmail(String email){
        return
                given()
                        .spec(super.set())
                        .queryParam("email", email)
                .when()
                        .get(ENDPOINT_USUARIOS)
                ;
    }

    public Response buscarUsuariosPorId(String id){
        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(ENDPOINT_USUARIOS + "/" + "{_id}")
                ;
    }

    public Response atualizarUsuario(String id, Usuario usuario){
        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                        .body(usuario)
                .when()
                        .put(ENDPOINT_USUARIOS + "/" + "{_id}")
                ;
    }

    public Response deletarUsuario(String id){
        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .delete(ENDPOINT_USUARIOS + "/{_id}")
                ;
    }


}
