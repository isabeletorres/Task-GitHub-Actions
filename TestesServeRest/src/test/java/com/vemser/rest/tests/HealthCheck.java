package com.vemser.rest.tests;

import com.vemser.rest.client.UsuarioClient;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import jdk.jfr.Label;
import org.junit.jupiter.api.Test;


public class HealthCheck {

    UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    @Story("HealthCheck da aplicação")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Endpoint de HealthCheck")
    @Label("HealthCheck")
    public void testHealthCheck(){

        usuarioClient.listarUsuarios()
        .then()
                .statusCode(200)
        ;
    }
}
