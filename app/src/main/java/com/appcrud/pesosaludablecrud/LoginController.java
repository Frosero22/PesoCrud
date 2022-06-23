package com.appcrud.pesosaludablecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModels.Usuario;
import com.appcrud.pesosaludablecrud.API.Services;
import com.google.android.material.textfield.TextInputLayout;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        inicializaComponentes();
    }

    public void inicializaComponentes(){

        input_user = findViewById(R.id.input_user);
        input_password = findViewById(R.id.input_password);

        edit_user = findViewById(R.id.edit_user);
        edit_password = findViewById(R.id.edit_password);

        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validaComponentes()){
                    login(edit_user.getText().toString(),edit_password.getText().toString());
                }
            }
        });

        bt_forgot = findViewById(R.id.bt_forgot);
        bt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPass();
            }
        });

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
        try{

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    if(response.code() == 200){
                        ingresar();
                    }else{
                        
                    }

                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ingresar(){
        Intent intent = new Intent(LoginController.this,PrincipalController.class);
        startActivity(intent);
        finish();
    }

    public void forgotPass(){

    }

}