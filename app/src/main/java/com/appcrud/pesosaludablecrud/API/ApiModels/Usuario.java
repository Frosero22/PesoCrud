package com.appcrud.pesosaludablecrud.API.ApiModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("primerNombre")
    private String primerNombre;

    @SerializedName("primerApellido")
    private String primerApellido;

    @SerializedName("segundoNombre")
    private String segundoNombre;

    @SerializedName("segundoApellido")
    private String segundoApellido;

    @SerializedName("secuenciaPersonal")
    private Integer secuenciaPersonal;

    @SerializedName("cuentaMail")
    private String cuentaMail;

    @SerializedName("esAdministrador")
    private String esAdministrador;

    @SerializedName("esDistribudor")
    private String esDistribudor;

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Integer getSecuenciaPersonal() {
        return secuenciaPersonal;
    }

    public void setSecuenciaPersonal(Integer secuenciaPersonal) {
        this.secuenciaPersonal = secuenciaPersonal;
    }

    public String getCuentaMail() {
        return cuentaMail;
    }

    public void setCuentaMail(String cuentaMail) {
        this.cuentaMail = cuentaMail;
    }

    public String getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(String esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public String getEsDistribudor() {
        return esDistribudor;
    }

    public void setEsDistribudor(String esDistribudor) {
        this.esDistribudor = esDistribudor;
    }
}
