package com.appcrud.pesosaludablecrud.API;

import com.appcrud.pesosaludablecrud.API.ApiModels.Orden;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Services {

    //SERVICIO QUE SE EJECUTA PARA LOGEARSE
    @GET("api/ordenes/despacho")
    Call<List<Orden>> listOrdenesDespacho(@Query("arg0") String usuario,@Query("arg1") String password);


}
