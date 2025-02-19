package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;
import jdk.jfr.Label;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class DeleteUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @Story("Validar schema de deletar usuários com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o contrato de deletar usuários está correto")
    @Label("Contrato")
    public void testValidarSchemaDeletarUsuarioComSucesso(){
        String idUsuario = UsuarioDataFactory.getUsuarioResponseValido().get_id();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/excluir_usuario.json"))
                .statusCode(200)
        ;
    }

    @Test
    @Story("Validar deleção de usuário com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar deleção de usuário com sucesso")
    @Label("Usuário")
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
    @Story("Validar deleção de usuário com dados inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar deleção de usuário com conta sem autenticação de administrador")
    @Label("Usuário")
    public void testDeveDeletarUsuarioAdministradorSemEstarAutenticado(){
        String idUsuario = UsuarioDataFactory.criarAdminValidoEPegarId();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body("message", Matchers.equalTo("Registro excluído com sucesso"))
                .statusCode(200)
        ;
    }

    @Test
    @Story("Validar deleção de usuário com dados inválidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validar deleção de usuário com carrinho cadastrado")
    @Label("Usuário")
    public void testDeveDeletarUsuarioComCarrinho(){
        String idUsuario = UsuarioDataFactory.getIdUsuarioComCarrinho();

        usuarioClient.deletarUsuario(idUsuario)
        .then()
                .body("message", Matchers.equalTo("Não é permitido excluir usuário com carrinho cadastrado"))
                .statusCode(400)
        ;
    }
}
