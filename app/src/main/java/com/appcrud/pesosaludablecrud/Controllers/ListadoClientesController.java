package com.appcrud.pesosaludablecrud.Controllers;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.appcrud.pesosaludablecrud.API.ApiClient;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Clientes;
import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;
import com.appcrud.pesosaludablecrud.API.Services;
import com.appcrud.pesosaludablecrud.Adapters.ClientesAdapter;
import com.appcrud.pesosaludablecrud.Models.PsoClientes;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Messages;
import com.appcrud.pesosaludablecrud.Utils.ProgressDialog;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.appcrud.pesosaludablecrud.Utils.Sessions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoClientesController extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClientesAdapter clientesAdapter;
    private FloatingActionButton fcbCrearCliente;

    private ArrayList<PsoClientes> lsClientes = new ArrayList<>();
    private ArrayList<PsoClientes> lsAuxiliar;

    private Usuario usuario;

    private DatePickerDialog datePickerDialog;

    protected Services service = ApiClient.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_clientes_controller_activity);
        Log.e("LLEGO A ","CLIENTES");
        init();
    }

    public void init(){
        usuario = Sessions.obtenerUsuario(ListadoClientesController.this);



        progressDialog = new ProgressDialog(ListadoClientesController.this);
        recyclerView = findViewById(R.id.lista_clientes_registrados);


        registerForContextMenu(recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        lsClientes = new ArrayList<>();
        lsAuxiliar = new ArrayList<>();

        clientesAdapter = new ClientesAdapter(lsClientes,ListadoClientesController.this);
        recyclerView.setAdapter(clientesAdapter);

        fcbCrearCliente = findViewById(R.id.fcbCrearCliente);
        fcbCrearCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Routes.crearClientesControoler(ListadoClientesController.this);
            }
        });
        Log.e("LLEGO A ","CLIENTES");
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
            for(PsoClientes i : lsAuxiliar){
                if(i.getPrimerNombre().toUpperCase().contains(texto) || i.getPrimerNombre().toLowerCase().contains(texto) ||
                        i.getSegundoNombre().toLowerCase().contains(texto) ||
                        i.getSegundoNombre().toUpperCase().contains(texto) ||
                        i.getPrimerApellido().toUpperCase().contains(texto) ||
                        i.getPrimerApellido().toLowerCase().contains(texto) ||
                        i.getSegundoApellido().toUpperCase().contains(texto) ||
                        i.getSegundoApellido().toLowerCase().contains(texto) ||
                        String.valueOf(i.getCodigoCliente()).toUpperCase().contains(texto)){
                    lsClientes.add(i);

                }
            }

        }
        clientesAdapter.notifyDataSetChanged();
    }


    public void listarClientes(){
        Log.e("LLEGO A ","CLIENTES");
        progressDialog.startLoadingDialog("Cargando Clientes...");
        Call<Clientes> call = service.listarClientes(usuario.getUsuario().getUsuario(),usuario.getUsuario().getEsAdministrador());
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {

                try{

                    if(response.errorBody() == null){
                        if(response.body() != null){
                            if(response.code() == 200){


                                progressDialog.dismissDialog();
                                armaListaClientes(response.body());

                            }
                        }
                    }else{
                        progressDialog.dismissDialog();
                        Messages.mensajeError(ListadoClientesController.this,response.errorBody().string());
                    }

                }catch (Exception e){
                    progressDialog.dismissDialog();
                    e.printStackTrace();
                    Messages.mensajeError(ListadoClientesController.this,e.toString());
                }


            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                progressDialog.dismissDialog();
                t.printStackTrace();
                Messages.mensajeError(ListadoClientesController.this,t.getLocalizedMessage());
            }
        });
    }


    public void armaListaClientes(Clientes clientes){

        lsClientes.addAll(clientes.getClientes());
        lsAuxiliar.addAll(clientes.getClientes());

        clientesAdapter = new ClientesAdapter(lsClientes,ListadoClientesController.this);
        recyclerView.setAdapter(clientesAdapter);
        clientesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        Routes.principalController(ListadoClientesController.this);
        super.onBackPressed();
    }

}