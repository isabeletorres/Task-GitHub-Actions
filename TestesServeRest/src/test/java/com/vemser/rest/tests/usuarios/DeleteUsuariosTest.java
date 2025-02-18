package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class DeleteUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testValidarSchemaDeletarUsuarioComSucesso(){
        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/excluir_usuario.json"))
                .statusCode(200)
        ;
    }

    @Test
    public void testDeveDeletarUsuarioComSucesso(){
        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/excluir_usuario.json"))
                .body("message", Matchers.equalTo("Registro excluído com sucesso"))
                .statusCode(200)
        ;
    }

    @Test
    public void testDeveDeletarUsuarioAdministradorSemEstarAutenticado(){
        String idUsuario = UsuarioDataFactory.criarAdminValidoEPegarId();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body("message", Matchers.equalTo("Registro excluído com sucesso"))
                .statusCode(200)
        ;
    }

    @Test
    public void testDeveDeletarUsuarioComCarrinho(){
        String idUsuario = UsuarioDataFactory.getIdUsuarioComCarrinho();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body("message", Matchers.equalTo("Não é permitido excluir usuário com carrinho cadastrado"))
                .statusCode(400)
        ;
    }
}
