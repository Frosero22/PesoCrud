package com.appcrud.pesosaludablecrud.API.ApiModelsResponse;

import com.google.gson.annotations.SerializedName;

public class ClienteGuardadoResponse {

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("codigoCliente")
    private String codigoCliente;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
}
