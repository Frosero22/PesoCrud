package com.appcrud.pesosaludablecrud.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.appcrud.pesosaludablecrud.R;

public class ProgressDialog {

    private Activity activity;
    private AlertDialog dialog;

    public ProgressDialog(Activity activity){
        this.activity=activity;
    }

    public void startLoadingDialog(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        final View view = inflater.inflate(R.layout.loading_dialog, null);
        TextView txvVista = view.findViewById(R.id.tvProcesando);
        txvVista.setText(mensaje);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
