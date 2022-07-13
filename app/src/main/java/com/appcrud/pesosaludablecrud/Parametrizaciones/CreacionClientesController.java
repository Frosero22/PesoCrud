package com.appcrud.pesosaludablecrud.Parametrizaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModelsRequest.RequestGuardarCliente;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.ClienteGuardadoResponse;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;
import com.appcrud.pesosaludablecrud.API.Services;
import com.appcrud.pesosaludablecrud.Controllers.PrincipalController;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Location;
import com.appcrud.pesosaludablecrud.Utils.Messages;
import com.appcrud.pesosaludablecrud.Utils.ProgressDialog;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.appcrud.pesosaludablecrud.Utils.Sessions;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CreacionClientesController extends AppCompatActivity {

    private List<String> lsTiposIdentificacion = new ArrayList<>();
    private List<String> lsGeneros = new ArrayList<>();
    private AutoCompleteTextView atTiposIdentificacion;
    private AutoCompleteTextView atGeneros;

    private Usuario usuario = new Usuario();

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
    private Integer codigoCliente;
    private String latitud;
    private String longitud;
    private String esEdicion;
    private String genero;
    protected Services service = ApiClient.getInstance();
    private ProgressDialog progressDialog = new ProgressDialog(CreacionClientesController.this);

    private DatePickerDialog datePickerDialog;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;

    private final int PLACE_PIKER = 1;

    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    private LocationManager locationManager;
    private android.location.Location loc;

    private double latitude;
    private double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;//1 Metro
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;//1 minuto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_clientes_activity);
        init();
        llenaCombos();
        validaEsEdicion();
    }

    public void init(){
        initDatePicker();
        Bundle bundle = this.getIntent().getExtras();
        usuario = Sessions.obtenerUsuario(CreacionClientesController.this);
//        esEdicion = bundle.getString("esEdicion","");

        atTiposIdentificacion = findViewById(R.id.atTiposIdentificacion);
        atTiposIdentificacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(lsTiposIdentificacion.get(i).equalsIgnoreCase("CEDULA")){
                    codigoTipoIdentificacion = 1;
                }else if(lsTiposIdentificacion.get(i).equalsIgnoreCase("RUC")){

                    codigoTipoIdentificacion = 2;
                }else{
                    codigoTipoIdentificacion = 3;
                }

            }
        });

        atGeneros = findViewById(R.id.atGeneros);
        atGeneros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(lsGeneros.get(i).equalsIgnoreCase("MASCULINO")){
                    genero = "M";
                }else if(lsGeneros.get(i).equalsIgnoreCase("FEMENINO")){
                    genero = "F";
                }else{
                    genero = "I";
                }

            }
        });

        inptIdentificacion = findViewById(R.id.inptIdentifcacion);
        txtIdentificacion = findViewById(R.id.txtIdentificacion);

        inptPrimerNombre = findViewById(R.id.txtInpPrimerNombre);
        txtPrimerNombre = findViewById(R.id.txtPrimerNombre);

        inptPrimerApellido = findViewById(R.id.txtInpPrimerApellido);
        txtPrimerApellido = findViewById(R.id.txtPrimerApellido);

        inptSegundoNombre = findViewById(R.id.txtInpSegundoNombre);
        txtSegundoNombre = findViewById(R.id.txtSegundoNombre);

        inptSegundoApellido = findViewById(R.id.txtInpSegundoApellido);
        txtSegundoApellido = findViewById(R.id.txtSegundoApellido);

        inptFechaNacimiento = findViewById(R.id.txtInpFechaNacimiento);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);

        inptCorreo = findViewById(R.id.txtInpCorreo);
        txtCorreo = findViewById(R.id.txtCorreo);

        inptTelefono = findViewById(R.id.txtInptTelefono);
        txtTelefono = findViewById(R.id.txtTelefono);


        inptGeolocalizacion = findViewById(R.id.inptGeolocalizacion);
        txtGeolocalizacion = findViewById(R.id.txtGeolocalizacion);
        txtGeolocalizacion.setOnClickListener(view -> {
            solicitaPermisos();
            Location location = new Location();
            location.getLocation(CreacionClientesController.this);

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

        validaEsEdicion();

        if(esEdicion != null){
            if(esEdicion.equalsIgnoreCase("S")){
                btnGuardar.setText("Actualizar Cliente");
            }
        }


        btnGuardar.setOnClickListener(view -> validaEstructura());
        btnFechaNacimiento = findViewById(R.id.btnTomarFechaNacimiento);
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


    }

    private void solicitaPermisos() {
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");

        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;

            String mes = null;
            if(month < 10){
                mes = "0"+month;
            }

            String date;
            if(mes != null){

                date = day+"/"+mes+"/"+year;

            }else{

                date = makeDateString(day, month, year);

            }
            txtFechaNacimiento.setText(date);


        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);


    }

    private String makeDateString(int day, int month, int year)
    {
        return day+"/"+month+"/"+year;
    }

    public void llenaCombos(){

        if(esEdicion != null){
            if(esEdicion.equalsIgnoreCase("S")){
                if(codigoTipoIdentificacion == 1){
                    lsTiposIdentificacion.add("CEDULA");
                    lsTiposIdentificacion.add("RUC");
                    lsTiposIdentificacion.add("SIN IDENTIFICACION");
                }else if(codigoTipoIdentificacion == 2){
                    lsTiposIdentificacion.add("RUC");
                    lsTiposIdentificacion.add("CEDULA");
                    lsTiposIdentificacion.add("SIN IDENTIFICACION");
                }else{
                    lsTiposIdentificacion.add("SIN IDENTIFICACION");
                    lsTiposIdentificacion.add("CEDULA");
                    lsTiposIdentificacion.add("RUC");
                }



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




                if(genero.equalsIgnoreCase("M")){
                    lsGeneros.add("MASCULINO");
                    lsGeneros.add("FEMENINO");
                }else if(genero.equalsIgnoreCase("F")){
                    lsGeneros.add("FEMENINO");
                    lsGeneros.add("MASCULINO");
                }


                ArrayAdapter<String> b = new ArrayAdapter<String>(CreacionClientesController.this,R.layout.option_item,lsGeneros);
                runOnUiThread(() -> {
                    atGeneros.setAdapter(b);
                    atGeneros.setText(b.getItem(0),false);
                    if(b.getItem(0).equalsIgnoreCase("MASCULINO")){
                        genero = "MASCULINO";
                    }else if(b.getItem(0).equalsIgnoreCase("FEMENINO")){
                        genero = "FEMENINO";
                    }else{
                        genero = "INDIFERENTE";
                    }
                });
            }
        }else{
            lsTiposIdentificacion.add("CEDULA");
            lsTiposIdentificacion.add("RUC");
            lsTiposIdentificacion.add("SIN IDENTIFICACION");

            lsGeneros.add("MASCULINO");
            lsGeneros.add("FEMENINO");

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

            ArrayAdapter<String> b = new ArrayAdapter<String>(CreacionClientesController.this,R.layout.option_item,lsGeneros);
            runOnUiThread(() -> {
                atGeneros.setAdapter(b);
                atGeneros.setText(b.getItem(0),false);
                if(b.getItem(0).equalsIgnoreCase("MASCULINO")){
                    genero = "MASCULINO";
                }else if(b.getItem(0).equalsIgnoreCase("FEMENINO")){
                    genero = "FEMENINO";
                }else{
                    genero = "INDIFERENTE";
                }
            });
        }







    }



    public void validaEsEdicion(){

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            esEdicion = bundle.getString("esEdicion","");


            if(esEdicion != null){
                codigoTipoIdentificacion = bundle.getInt("tipoIdentificacion",0);
                codigoCliente = bundle.getInt("codigoCliente",0);



                String identificacion = bundle.getString("identificacion","");
                String primerNombre = bundle.getString("primerNombre","");
                String segundoNombre = bundle.getString("segundoNombre","");
                String primerApellido = bundle.getString("primerApellido","");
                String segundoApellido = bundle.getString("segundoApellido","");
                String correo = bundle.getString("correo","");
                String telefono = bundle.getString("telefono","");
                String genero = bundle.getString("genero","");
                String fechaNacimiento = bundle.getString("fechaNacimiento","");

                Log.e("","GENERO "+genero);

                if(genero.equalsIgnoreCase("M")){
                    this.genero = "M";
                }else if(genero.equalsIgnoreCase("F")){
                    this.genero = "F";
                }

                latitud = bundle.getString("latitud","");
                longitud = bundle.getString("longitud","");

                String geoLocalizacion = latitud+" ; "+longitud;

                txtIdentificacion.setText(identificacion);
                txtPrimerNombre.setText(primerNombre);
                txtSegundoNombre.setText(segundoNombre);
                txtPrimerApellido.setText(primerApellido);
                txtSegundoApellido.setText(segundoApellido);
                txtCorreo.setText(correo);
                txtTelefono.setText(telefono);
                txtGeolocalizacion.setText(geoLocalizacion);
                txtFechaNacimiento.setText(fechaNacimiento);
            }
        }

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

        if(genero == null){
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


            requestGuardarCliente.setGenero(genero);
            requestGuardarCliente.setFechaNacimiento(txtFechaNacimiento.getText().toString());
            requestGuardarCliente.setEmail(txtCorreo.getText().toString());
            requestGuardarCliente.setTelefono(txtTelefono.getText().toString());
            requestGuardarCliente.setLatitud(latitud);
            requestGuardarCliente.setLongitud(longitud);
            requestGuardarCliente.setEsActivo("S");
            requestGuardarCliente.setUsuarioIngreso(usuario.usuario.getUsuario());

            if(esEdicion != null){
                if(esEdicion.equalsIgnoreCase("S")){
                    requestGuardarCliente.setCodigoCliente(codigoCliente);
                    actualizaCliente(requestGuardarCliente);
                }
            }else{
                guardarCliente(requestGuardarCliente);
            }



        }

    }


    public void actualizaCliente(RequestGuardarCliente requestGuardarCliente){
        progressDialog.startLoadingDialog("Actualización CLiente");
        Call<ClienteGuardadoResponse> call = service.actualizaCliente(requestGuardarCliente);
        call.enqueue(new Callback<ClienteGuardadoResponse>() {
            @Override
            public void onResponse(Call<ClienteGuardadoResponse> call, Response<ClienteGuardadoResponse> response) {
                progressDialog.dismissDialog();
                ClienteGuardadoResponse clienteGuardadoResponse = response.body();
                if(response.code() == 200){


                    Toast.makeText(CreacionClientesController.this, "Cliente actualizad correctamente", Toast.LENGTH_SHORT).show();
                    Routes.clientesController(CreacionClientesController.this);
                   // validaValoracionInicial(Integer.parseInt(clienteGuardadoResponse.getCodigoCliente()));
                }else{

                    Messages.mensajeError(CreacionClientesController.this,"Error al guardar cliente, mensaje para sistemas: "+clienteGuardadoResponse.getCausa());
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
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Routes.clientesController(CreacionClientesController.this);
            }
        }).show();
    }


}