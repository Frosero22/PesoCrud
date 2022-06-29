package com.appcrud.pesosaludablecrud.Parametrizaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.appcrud.pesosaludablecrud.R;

import java.util.ArrayList;
import java.util.List;

public class CreacionClientesController extends AppCompatActivity {

    private List<String> lsTiposIdentificacion = new ArrayList<>();
    private AutoCompleteTextView atTiposIdentificacion;


    private Integer codigoTipoIdentificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_clientes_activity);
        init();
        tiposIdentificacion();
    }

    public void init(){

    }

    public void tiposIdentificacion(){
        lsTiposIdentificacion.add("CEDULA");
        lsTiposIdentificacion.add("RUC");
        lsTiposIdentificacion.add("SIN IDENTIFICACION");

        ArrayAdapter<String> a = new ArrayAdapter<String>(CreacionClientesController.this,R.layout.option_item,lsTiposIdentificacion);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                atTiposIdentificacion.setAdapter(a);
                atTiposIdentificacion.setText(a.getItem(0).toString(),false);
                if(a.getItem(0).equalsIgnoreCase("CEDULA")){
                    codigoTipoIdentificacion = 1;
                }else if(a.getItem(0).equalsIgnoreCase("RUC")){
                    codigoTipoIdentificacion = 2;
                }else{
                    codigoTipoIdentificacion = 3;
                }
            }
        });
    }
}