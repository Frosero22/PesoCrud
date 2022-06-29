package com.appcrud.pesosaludablecrud.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;
import com.appcrud.pesosaludablecrud.API.Services;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Messages;
import com.appcrud.pesosaludablecrud.Utils.ProgressDialog;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.appcrud.pesosaludablecrud.Utils.Sessions;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController extends AppCompatActivity {

    private TextInputLayout input_user;
    private EditText edit_user;

    private TextInputLayout input_password;
    private EditText edit_password;

    private Button bt_login;
    private Button bt_forgot;

    protected Services service = ApiClient.getInstance();

    private ProgressDialog progressDialog;
    private Usuario usuario = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        inicializaComponentes();
    }

    public void inicializaComponentes(){

        usuario = Sessions.obtenerUsuario(LoginController.this);

        if(usuario.getEsAdministrador() != null){
            Routes.principalController(LoginController.this);
        }

        input_user = findViewById(R.id.input_user);
        input_password = findViewById(R.id.input_password);

        edit_user = findViewById(R.id.edit_user);
        edit_password = findViewById(R.id.edit_password);

        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(view -> {
            if(validaComponentes()){
                login(edit_user.getText().toString(),edit_password.getText().toString());
            }
        });

        bt_forgot = findViewById(R.id.bt_forgot);
        bt_forgot.setOnClickListener(view -> forgotPass());

        progressDialog = new ProgressDialog(LoginController.this);

    }

    public boolean validaComponentes(){

        if(edit_user.getText().toString().equals("")){
            input_user.setErrorEnabled(true);
            input_user.setError("Campo Obligatorio");
            return false;
        }

        if(edit_password.getText().toString().equals("")){
            input_password.setErrorEnabled(true);
            input_password.setError("Campo Obligatorio");
            return false;
        }


        return true;
    }

    public void login(String strUsuario, String strPass){

        Call<Usuario> call = service.ingresoUsuario(strUsuario,strPass);
        progressDialog.startLoadingDialog("Iniciando Sesión, Un momento...");
        try{

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(@NotNull Call<Usuario> call, @NotNull Response<Usuario> response) {
                    try{

                        Usuario usuario = response.body();

                        if(response.code() == 200){
                            iniciarApp(usuario);
                        }else{
                            progressDialog.dismissDialog();
                            Messages.mensajeError(LoginController.this,response.errorBody().string());
                        }

                    }catch (Exception e){
                        progressDialog.dismissDialog();
                        e.printStackTrace();
                        Messages.mensajeError(LoginController.this,e.toString());
                    }

                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    progressDialog.dismissDialog();
                    t.printStackTrace();
                    Messages.mensajeError(LoginController.this,t.toString());
                }
            });


        }catch (Exception e){
            progressDialog.dismissDialog();
            e.printStackTrace();
            Messages.mensajeError(LoginController.this,e.toString());
        }

    }



    public void iniciarApp(Usuario usuario){
        Sessions.guardaUsuario(usuario,LoginController.this);

        progressDialog.dismissDialog();
        Routes.principalController(LoginController.this);
    }



    public void forgotPass(){

    }

}