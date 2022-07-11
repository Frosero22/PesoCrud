package com.appcrud.pesosaludablecrud.API.ApiModelsResponse;

import com.appcrud.pesosaludablecrud.Models.PsoUsuario;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("usuario")
    public PsoUsuario usuario;

    @SerializedName("mensaje")
    public String mensaje;

    public PsoUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(PsoUsuario usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
