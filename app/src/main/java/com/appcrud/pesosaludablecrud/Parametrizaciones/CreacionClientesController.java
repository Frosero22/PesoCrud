package com.appcrud.pesosaludablecrud.Parametrizaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.appcrud.pesosaludablecrud.API.ApiModelsRequest.RequestGuardarCliente;
import com.appcrud.pesosaludablecrud.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CreacionClientesController extends AppCompatActivity {

    private List<String> lsTiposIdentificacion = new ArrayList<>();
    private AutoCompleteTextView atTiposIdentificacion;



    private RadioButton radMasculino;
    private RadioButton radFemenino;

    private TextInputLayout inptIdentificacion;
    private EditText txtIdentificacion;

    private TextInputLayout inptPrimerApellido;
    private EditText txtPrimerApellido;

    private TextInputLayout inptSegundoNombre;
    private EditText txtSegundoNombre;

    private TextInputLayout inptSegundoApellido;
    private EditText txtSegundoApellido;

    private TextInputLayout inptFechaNacimiento;
    private EditText txtFechaNacimiento;
    private Button btnFechaNacimiento;

    private TextInputLayout inptCorreo;
    private EditText txtCorreo;

    private TextInputLayout inptTelefono;
    private EditText txtTelefono;

    private TextInputLayout inptPrimerNombre;
    private EditText txtPrimerNombre;

    private TextInputLayout inptGeolocalizacion;
    private EditText txtGeolocalizacion;

    private Button btnGuardar;

    private Integer codigoTipoIdentificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_clientes_activity);
        init();
        tiposIdentificacion();
    }

    public void init(){

        inptIdentificacion = findViewById(R.id.inptIdentifcacion);
        txtIdentificacion = findViewById(R.id.txtIdentificacion);

        inptPrimerNombre = findViewById(R.id.txtInpPrimerNombre);
        txtPrimerNombre = findViewById(R.id.txtPrimerNombre);

        inptPrimerApellido = findViewById(R.id.txtInpPrimerApellido);
        txtPrimerApellido = findViewById(R.id.txtPrimerApellido);

        inptSegundoNombre = findViewById(R.id.txtInpSegundoNombre);
        txtSegundoApellido = findViewById(R.id.txtSegundoNombre);

        inptSegundoApellido = findViewById(R.id.txtInpSegundoApellido);
        txtSegundoApellido = findViewById(R.id.txtSegundoApellido);

        inptFechaNacimiento = findViewById(R.id.txtInpFechaNacimiento);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);

        inptCorreo = findViewById(R.id.txtInpCorreo);
        txtCorreo = findViewById(R.id.txtCorreo);

        inptTelefono = findViewById(R.id.txtInptTelefono);
        txtTelefono = findViewById(R.id.txtTelefono);

        radMasculino = findViewById(R.id.radMasculino);
        radFemenino = findViewById(R.id.radFemenino);

        inptGeolocalizacion = findViewById(R.id.inptGeolocalizacion);
        txtGeolocalizacion = findViewById(R.id.txtGeolocalizacion);
        txtGeolocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnGuardar = findViewById(R.id.btGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaEstructura();
            }
        });





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

    public boolean valida(){

        if(codigoTipoIdentificacion == null){
            Toast.makeText(this, "Por favor seleccióne un tipo de identificación", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtIdentificacion.getText().toString().equalsIgnoreCase("")) {
            inptIdentificacion.setErrorEnabled(true);
            inptIdentificacion.setError("El Numero de Identificación es obligatorio");
            Toast.makeText(this, "El Numero de Identificación es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptIdentificacion.setErrorEnabled(false);

        }

        if (txtPrimerNombre.getText().toString().equalsIgnoreCase("")) {
            inptPrimerNombre.setErrorEnabled(true);
            inptPrimerNombre.setError("El Primero Nombre es obligatorio");
            Toast.makeText(this, "El Primero Nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptPrimerNombre.setErrorEnabled(false);

        }

        if (txtPrimerApellido.getText().toString().equalsIgnoreCase("")) {
            inptPrimerApellido.setErrorEnabled(true);
            inptPrimerApellido.setError("El Primer Apellido es obligatorio");
            Toast.makeText(this, "El Primer Apellido es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptPrimerApellido.setErrorEnabled(false);

        }

        if(!radFemenino.isSelected() && !radMasculino.isSelected()){
            Toast.makeText(this, "Seleccióne un Genero", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtFechaNacimiento.getText().toString().equalsIgnoreCase("")) {
            inptFechaNacimiento.setErrorEnabled(true);
            inptFechaNacimiento.setError("La fecha de nacimiento es obligatoria");
            Toast.makeText(this, "La fecha de nacimiento es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptFechaNacimiento.setErrorEnabled(false);

        }

        if (txtCorreo.getText().toString().equalsIgnoreCase("")) {
            inptCorreo.setErrorEnabled(true);
            inptCorreo.setError("El correo del cliente es obligatorio");
            Toast.makeText(this, "El correo del cliente es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptCorreo.setErrorEnabled(false);

        }

        if (txtTelefono.getText().toString().equalsIgnoreCase("")) {
            inptTelefono.setErrorEnabled(true);
            inptTelefono.setError("El telefono del cliente es obligatorio");
            Toast.makeText(this, "El telefono del cliente es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptTelefono.setErrorEnabled(false);

        }

        if (txtGeolocalizacion.getText().toString().equalsIgnoreCase("")) {
            inptGeolocalizacion.setErrorEnabled(true);
            inptGeolocalizacion.setError("La Geolocalización es obligatoria");
            Toast.makeText(this, "La Geolocalización es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            inptGeolocalizacion.setErrorEnabled(false);

        }


        return true;
    }

    public void validaEstructura(){
        if(valida()){
            RequestGuardarCliente requestGuardarCliente = new RequestGuardarCliente();
            requestGuardarCliente.setCodigoTipoIdentificacion(codigoTipoIdentificacion);
            requestGuardarCliente.setIdentificacion(txtIdentificacion.getText().toString());
        }

    }

    public void guardarCliente(RequestGuardarCliente requestGuardarCliente){



    }

}