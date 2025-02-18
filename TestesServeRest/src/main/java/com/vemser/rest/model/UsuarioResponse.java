package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioResponse {

    private String nome;
    private String email;
    private String password;
    private String administrador;
    @JsonProperty("_id")
    private String _id;

    public UsuarioResponse() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "UsuarioResponse{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", administrador='" + administrador + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
