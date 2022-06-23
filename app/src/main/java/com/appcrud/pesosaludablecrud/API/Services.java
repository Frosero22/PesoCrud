package com.appcrud.pesosaludablecrud.API;

import com.appcrud.pesosaludablecrud.API.ApiModels.Orden;
import com.appcrud.pesosaludablecrud.API.ApiModels.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    //SERVICIO QUE SE EJECUTA PARA LOGEARSE
    @GET("/PesoSaludableApp/ingresoUsuario")
    Call<Usuario> ingresoUsuario(@Query("arg0") String usuario, @Query("arg1") String password);


}
