package com.appcrud.pesosaludablecrud.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;

public class Sessions {

    public  static void guardaUsuario(Usuario usuario, Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("primerNombre",usuario.getPrimerNombre());

        if(usuario.getSegundoNombre() != null){
            editor.putString("segundoNombre",usuario.getSegundoNombre());
        }

        editor.putString("primerApellido",usuario.getPrimerApellido());

        if(usuario.getSegundoApellido() != null){
            editor.putString("segundoApellido",usuario.getSegundoApellido());
        }

        editor.putString("esAdministrador",usuario.getEsAdministrador());
        editor.putString("esDistribuidor",usuario.getEsDistribudor());

        editor.putInt("secuenciaPersonal",usuario.getSecuenciaPersonal());


        editor.apply();

    }

    public static Usuario obtenerUsuario(Context context){

        Usuario usuario = new Usuario();
        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);

        usuario.setPrimerNombre(sharedPreferences.getString("primerNombre",""));
        usuario.setSegundoNombre(sharedPreferences.getString("segundoNombre",""));
        usuario.setPrimerApellido(sharedPreferences.getString("primerApellido",""));
        usuario.setSegundoApellido(sharedPreferences.getString("segundoApellido",""));
        usuario.setEsAdministrador(sharedPreferences.getString("esAdministrador",""));
        usuario.setEsDistribudor(sharedPreferences.getString("esDistribuidor",""));
        usuario.setSecuenciaPersonal(sharedPreferences.getInt("secuenciaPersonal",0));


        return usuario;

    }

    public static void borraUsuario(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
