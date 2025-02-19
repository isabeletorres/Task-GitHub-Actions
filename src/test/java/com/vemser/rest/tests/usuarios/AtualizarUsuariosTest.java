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
import net.datafaker.Faker;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class AtualizarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();
    Faker faker = new Faker();

    @Test
    @Story("Validar schema de atualizar usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de atualizar usuários está correto")
    @Label("Contrato")
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
    @Story("Validar atualização de usuário com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar atualização de usuário com sucesso")
    @Label("Usuário")
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
    @Story("Validar atualização de usuário com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar atualização de usuário com sucesso sem autenticação de administrador")
    @Label("Usuário")
    public void testDeveAtualizarUsuarioAlterandoAdminStatusSemAutenticacao(){

        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        Usuario usuarioAtualizado = UsuarioDataFactory.gerarUsuarioAdminValido();

        usuarioClient.atualizarUsuario(idUsuario, usuarioAtualizado)
        .then()
                .body("message", Matchers.equalTo("Registro alterado com sucesso"))
                .statusCode(200)
        ;
    }
}
