package com.appcrud.pesosaludablecrud.API;

import com.appcrud.pesosaludablecrud.API.ApiModelsRequest.RequestGuardarCliente;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.ClienteGuardadoResponse;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Clientes;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Services {

    //SERVICIO QUE SE EJECUTA PARA LOGEARSE
    @GET("PsoWar/PesoSaludableApp/ingresoUsuario")
    Call<Usuario> ingresoUsuario(@Query("arg0") String usuario, @Query("arg1") String password);

    @GET("PsoWar/PesoSaludableApp/listarClientes")
    Call<Clientes> listarClientes(@Query("arg0") String usuario, @Query("arg1") String strEsAdmin);

    @POST("PsoWar/PesoSaludableApp/guardarCliente")
    Call<ClienteGuardadoResponse> guardarCliente(@Body RequestGuardarCliente requestGuardarCliente);

    @POST("PsoWar/PesoSaludableApp/actualizaCliente")
    Call<ClienteGuardadoResponse> actualizaCliente(@Body RequestGuardarCliente requestGuardarCliente);


}
