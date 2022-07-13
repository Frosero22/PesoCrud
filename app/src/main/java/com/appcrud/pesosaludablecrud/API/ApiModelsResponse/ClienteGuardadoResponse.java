package com.appcrud.pesosaludablecrud.API.ApiModelsResponse;

import com.google.gson.annotations.SerializedName;

public class ClienteGuardadoResponse {

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("causa")
    private String causa;

    @SerializedName("codigoCliente")
    private String codigoCliente;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
}
