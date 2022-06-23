package com.appcrud.pesosaludablecrud.API;

public interface Services {

    //SECCION DE DESPACHO
    @GET("api/ordenes/despacho")
    Call<List<Orden>> listOrdenesDespacho(@Header("Authorization") String authHeader);

}
