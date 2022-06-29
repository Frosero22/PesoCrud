package com.appcrud.pesosaludablecrud.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Clientes;
import com.appcrud.pesosaludablecrud.API.Services;
import com.appcrud.pesosaludablecrud.Adapters.ClientesAdapter;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Messages;
import com.appcrud.pesosaludablecrud.Utils.ProgressDialog;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesController extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fcbCrearCliente;

    private ArrayList<Clientes> lsClientes;
    private ArrayList<Clientes> lsAuxiliar;

    private DatePickerDialog datePickerDialog;
    private ClientesAdapter clientesAdapter;

    protected Services service = ApiClient.getInstance();
    private ProgressDialog progressDialog = new ProgressDialog(ClientesController.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_activity);


        init();

    }

    public void init(){

        recyclerView = findViewById(R.id.lista_clientes_registrados);
        fcbCrearCliente = findViewById(R.id.fcbCrearCliente);
        fcbCrearCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Routes.crearClientesControoler(ClientesController.this);
            }
        });
        listarClientes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_buscar_cliente,menu);
        MenuItem buscar = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(buscar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscador(s);
                return false;
            }
        });
        return true;
    }

    public void buscador(String texto){

        if(texto.length() == 0){
            lsClientes.clear();
            lsClientes.addAll(lsAuxiliar);
        }else{

            lsClientes.clear();
            for(Clientes i : lsClientes){
                if(i.getPrimerNombre().toUpperCase().contains(texto) || i.getSegundoNombre().toLowerCase().contains(texto) ||
                        i.getPrimerApellido().toUpperCase().contains(texto) || i.getSegundoApellido().toUpperCase().contains(texto) ||
                        String.valueOf(i.getCodigoCliente()).toUpperCase().contains(texto)){
                    lsClientes.add(i);

                }
            }

        }
        clientesAdapter.notifyDataSetChanged();
    }


    public void listarClientes(){
        progressDialog.startLoadingDialog("Cargando Clientes...");
        Call<ArrayList<Clientes>> call = service.listarClientes();
        call.enqueue(new Callback<ArrayList<Clientes>>() {
            @Override
            public void onResponse(Call<ArrayList<Clientes>> call, Response<ArrayList<Clientes>> response) {

                try{

                    if(response.errorBody() == null){
                        if(response.body() != null){
                            if(response.code() == 200){
                                progressDialog.dismissDialog();
                                lsClientes = response.body();
                            }
                        }
                    }else{
                        progressDialog.dismissDialog();
                        Messages.mensajeError(ClientesController.this,response.errorBody().string());
                    }

                }catch (Exception e){
                    progressDialog.dismissDialog();
                    e.printStackTrace();
                    Messages.mensajeError(ClientesController.this,e.toString());
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Clientes>> call, Throwable t) {
                progressDialog.dismissDialog();
                Messages.mensajeError(ClientesController.this,t.getLocalizedMessage());
            }
        });
    }

}