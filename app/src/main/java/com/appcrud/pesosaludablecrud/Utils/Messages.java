package com.appcrud.pesosaludablecrud.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.appcrud.pesosaludablecrud.R;

public class Messages {

    public static void mensajeError(Context context, String strMensaje){

        new AlertDialog.Builder(context)
                .setTitle("Info")
                .setMessage(strMensaje)
              //  .setIcon(R.drawable.advertencia)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();


    }


    public static void mensajeExito(Context context, String strMensaje){

        new AlertDialog.Builder(context)
                .setTitle("Info")
                .setMessage(strMensaje)
                //  .setIcon(R.drawable.advertencia)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();


    }

}
