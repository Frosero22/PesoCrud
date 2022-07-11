package com.appcrud.pesosaludablecrud.API.ApiModelsResponse;

import com.appcrud.pesosaludablecrud.Models.PsoClientes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Clientes {

    @SerializedName("clientes")
    public List<PsoClientes> clientes;

    public List<PsoClientes> getClientes() {
        return clientes;
    }

    public void setClientes(List<PsoClientes> clientes) {
        this.clientes = clientes;
    }
}
