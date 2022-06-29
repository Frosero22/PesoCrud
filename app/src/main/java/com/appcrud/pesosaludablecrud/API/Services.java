package com.appcrud.pesosaludablecrud.API;

import com.appcrud.pesosaludablecrud.API.ApiModelsRequest.RequestGuardarCliente;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.ClienteGuardadoResponse;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Clientes;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    //SERVICIO QUE SE EJECUTA PARA LOGEARSE
    @GET("/PesoSaludableApp/ingresoUsuario")
    Call<Usuario> ingresoUsuario(@Query("arg0") String usuario, @Query("arg1") String password);

    @GET("/PesoSaludableApp/listarClientes")
    Call<ArrayList<Clientes>> listarClientes();

    @GET("/PesoSaludableApp/guardarCliente")
    Call<ClienteGuardadoResponse> guardarCliente(@Body RequestGuardarCliente requestGuardarCliente);


}
