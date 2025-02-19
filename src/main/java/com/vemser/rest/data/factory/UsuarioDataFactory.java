package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.model.UsuarioResponse;
import com.vemser.rest.utils.ConfigUtils;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class UsuarioDataFactory {

    static Faker faker = new Faker();
    static UsuarioClient usuarioClient = new UsuarioClient();

    public static Usuario usuarioValido(){
        return novoUsuario();
    }

    public static Usuario usuarioSemEmail(){
        Usuario usuario = novoUsuario();
        usuario.setEmail(StringUtils.EMPTY);
        return usuario;
    }

    public static Usuario usuarioEmailDuplicado(){
        Usuario usuario = novoUsuario();
        usuario.setEmail(getEmailValido());
        return usuario;
    }

    private static Usuario novoUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.passport().valid());
        usuario.setAdministrador(String.valueOf(faker.bool().bool()));

        return usuario;
    }

    public static String getEmailValido(){
        Response response =
        usuarioClient.listarUsuarios()
        .then()
                .extract().response()
                ;
        String emailCadastrado = response.path("usuarios[0].email");
        return emailCadastrado;
    }

    public static String getIdValido(){
        Response response =
        usuarioClient.listarUsuarios()
        .then()
                .extract().response()
                ;
        String idCadastrado = response.path("usuarios[0]._id");
        return idCadastrado;
    }


    public static UsuarioResponse getUsuarioResponseValido(){
        String id = getIdValido();

        UsuarioResponse response =
        usuarioClient.buscarUsuariosPorId(id)
        .then()
                .extract().as(UsuarioResponse.class)
                ;
        return response;
    }

    public static Usuario gerarUsuarioAdminValido(){
        Usuario usuario = novoUsuario();
        usuario.setAdministrador("true");
        return usuario;
    }

    public static String criarAdminValidoEPegarId(){

        Usuario usuario = gerarUsuarioAdminValido();

        Response response =
        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .extract().response()
        ;

        return response.path("_id");
    }

    public static Usuario gerarUsuarioApenasNome(){
        Usuario usuario = novoUsuario();
        usuario.setAdministrador(StringUtils.EMPTY);
        usuario.setEmail(StringUtils.EMPTY);
        usuario.setPassword(StringUtils.EMPTY);
        return usuario;
    }

    public static String gerarEmailInvalido(){
        return String.valueOf(faker.number());
    }

    public static String gerarIdInvalido(){
        return faker.bothify("##??##??##??#??##??");
    }

    public static String gerarIdInexistente(){
        return String.valueOf(faker.number().numberBetween(-100,-1));
    }

    public static String getIdUsuarioComCarrinho(){
        return ConfigUtils.getUserIdCart();
    }
}
