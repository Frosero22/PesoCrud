package com.appcrud.pesosaludablecrud.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;
import com.appcrud.pesosaludablecrud.Models.PsoUsuario;

public class Sessions {

    public  static void guardaUsuario(Usuario usuario,String user, Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("primerNombre",usuario.usuario.getPrimerNombre());

        if(usuario.usuario.getSegundoNombre() != null){
            editor.putString("segundoNombre",usuario.usuario.getSegundoNombre());
        }

        editor.putString("primerApellido",usuario.usuario.getPrimerApellido());

        if(usuario.usuario.getSegundoApellido() != null){
            editor.putString("segundoApellido",usuario.usuario.getSegundoApellido());
        }

        editor.putString("esAdministrador",usuario.usuario.getEsAdministrador());
        editor.putString("esDistribuidor",usuario.usuario.getEsDistribudor());

        editor.putInt("secuenciaPersonal",usuario.usuario.getSecuenciaPersonal());
        editor.putString("usuario",user);

        editor.apply();

    }

    public static Usuario obtenerUsuario(Context context){

        Usuario usuario = new Usuario();
        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);

        Log.e("","SECUENCIA "+sharedPreferences.getInt("secuenciaPersonal",0));

        if(sharedPreferences.getInt("secuenciaPersonal",0) != 0){

            PsoUsuario psoUsuario = new PsoUsuario();



            psoUsuario.setPrimerNombre(sharedPreferences.getString("primerNombre",""));
            psoUsuario.setSegundoNombre(sharedPreferences.getString("segundoNombre",""));
            psoUsuario.setPrimerApellido(sharedPreferences.getString("primerApellido",""));
            psoUsuario.setSegundoApellido(sharedPreferences.getString("segundoApellido",""));
            psoUsuario.setEsAdministrador(sharedPreferences.getString("esAdministrador",""));
            psoUsuario.setEsDistribudor(sharedPreferences.getString("esDistribuidor",""));
            psoUsuario.setSecuenciaPersonal(sharedPreferences.getInt("secuenciaPersonal",0));
            psoUsuario.setUsuario(sharedPreferences.getString("usuario",""));

            usuario.setUsuario(psoUsuario);
        }



        return usuario;

    }

    public static void borraUsuario(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
