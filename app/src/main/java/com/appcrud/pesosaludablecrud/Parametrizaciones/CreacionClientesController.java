package com.appcrud.pesosaludablecrud.Parametrizaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModelsRequest.RequestGuardarCliente;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.ClienteGuardadoResponse;
import com.appcrud.pesosaludablecrud.API.Services;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Location;
import com.appcrud.pesosaludablecrud.Utils.Messages;
import com.appcrud.pesosaludablecrud.Utils.ProgressDialog;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private String latitud;
    private String longitud;
    protected Services service = ApiClient.getInstance();
    private ProgressDialog progressDialog = new ProgressDialog(CreacionClientesController.this);


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
        txtGeolocalizacion.setOnClickListener(view -> {
            Location location = new Location();
            location.getLocation();

            if(location.getLoc() != null){
                if(!location.getLoc().equalsIgnoreCase("")){
                    txtGeolocalizacion.setText(location.getLoc());
                    latitud = location.getLatitud();
                    longitud = location.getLongitud();
                }
            }else{
                Toast.makeText(this, "No se pudo obtener la ubicaciòn actual, por favor verifique el GPS y que la aplicaciòn tenga los permisos necesarios", Toast.LENGTH_SHORT).show();
            }

        });

        btnGuardar = findViewById(R.id.btGuardar);
        btnGuardar.setOnClickListener(view -> validaEstructura());





    }

    public void tiposIdentificacion(){
        lsTiposIdentificacion.add("CEDULA");
        lsTiposIdentificacion.add("RUC");
        lsTiposIdentificacion.add("SIN IDENTIFICACION");

        ArrayAdapter<String> a = new ArrayAdapter<String>(CreacionClientesController.this,R.layout.option_item,lsTiposIdentificacion);
        runOnUiThread(() -> {
            atTiposIdentificacion.setAdapter(a);
            atTiposIdentificacion.setText(a.getItem(0),false);
            if(a.getItem(0).equalsIgnoreCase("CEDULA")){
                codigoTipoIdentificacion = 1;
            }else if(a.getItem(0).equalsIgnoreCase("RUC")){
                codigoTipoIdentificacion = 2;
            }else{
                codigoTipoIdentificacion = 3;
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
            requestGuardarCliente.setPrimerNombre(txtPrimerNombre.getText().toString().toUpperCase());

            if(!txtSegundoNombre.getText().toString().equalsIgnoreCase("")){
                requestGuardarCliente.setSegundoNombre(txtSegundoNombre.getText().toString().toUpperCase());
            }

            requestGuardarCliente.setPrimerApellido(txtPrimerApellido.getText().toString().toUpperCase());

            if(!txtSegundoApellido.getText().toString().equalsIgnoreCase("")){
                requestGuardarCliente.setSegundoApellido(txtSegundoApellido.getText().toString().toUpperCase());
            }

            String strGenero = "";
            if(radMasculino.isSelected()){
                strGenero = "M";
            }else if(radFemenino.isSelected()){
                strGenero = "F";
            }
            requestGuardarCliente.setGenero(strGenero);
            requestGuardarCliente.setFechaNacimiento(txtFechaNacimiento.getText().toString());
            requestGuardarCliente.setEmail(txtCorreo.getText().toString());
            requestGuardarCliente.setTelefono(txtTelefono.getText().toString());
            requestGuardarCliente.setLatitud(latitud);
            requestGuardarCliente.setLongitud(longitud);

            guardarCliente(requestGuardarCliente);
        }

    }

    public void guardarCliente(RequestGuardarCliente requestGuardarCliente){
        progressDialog.startLoadingDialog("Guardando CLiente");
        Call<ClienteGuardadoResponse> call = service.guardarCliente(requestGuardarCliente);
        call.enqueue(new Callback<ClienteGuardadoResponse>() {
            @Override
            public void onResponse(Call<ClienteGuardadoResponse> call, Response<ClienteGuardadoResponse> response) {
                progressDialog.dismissDialog();
                if(response.code() == 200){
                    ClienteGuardadoResponse clienteGuardadoResponse = response.body();
                    Messages.mensajeExito(CreacionClientesController.this,clienteGuardadoResponse.getMensaje());
                    validaValoracionInicial(Integer.parseInt(clienteGuardadoResponse.getCodigoCliente()));
                }
            }

            @Override
            public void onFailure(Call<ClienteGuardadoResponse> call, Throwable t) {
                progressDialog.dismissDialog();
                t.printStackTrace();
                Messages.mensajeError(CreacionClientesController.this,"Error al guardar cliente, mensaje para sistemas: "+t.getLocalizedMessage());
            }
        });



    }

    public void validaValoracionInicial(Integer codigoCliente){
        new AlertDialog.Builder(CreacionClientesController.this)
                .setTitle("Transacción Exitosa")
                .setMessage("¿Desea realizar la valoración inicial?")
                .setPositiveButton("Aceptar",
                        (dialog, which) -> {
                            Routes.creacionCreacionValoracion(CreacionClientesController.this,codigoCliente);
                            finish();
                            dialog.dismiss();
                        }).setNegativeButton("Cancelar", (dialogInterface, i) -> Routes.clientesController(CreacionClientesController.this)).show();
    }


}