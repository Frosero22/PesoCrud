package com.appcrud.pesosaludablecrud.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Usuario;
import com.appcrud.pesosaludablecrud.R;
import com.appcrud.pesosaludablecrud.Utils.Routes;
import com.appcrud.pesosaludablecrud.Utils.Sessions;

public class PrincipalController extends AppCompatActivity {


    private Usuario usuario = new Usuario();
    private Button btn_personal;
    private Button btn_productos;
    private Context context;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);
        init();
        validaRoles();
    }

    public void init(){
        context = PrincipalController.this;
        usuario = Sessions.obtenerUsuario(context);

        btn_personal = findViewById(R.id.btn_personal);
        btn_personal.setOnClickListener(view -> routes(1));

        btn_productos = findViewById(R.id.btn_productos);
        btn_productos.setOnClickListener(view -> routes(2));

        Button btn_visitas = findViewById(R.id.btn_visitas);
        btn_visitas.setOnClickListener(view -> routes(3));

        Button btn_clientes = findViewById(R.id.btn_clientes);
        btn_clientes.setOnClickListener(view -> routes(4));

        Button btn_pedidos = findViewById(R.id.btn_pedidos);
        btn_pedidos.setOnClickListener(view -> routes(5));

        Button btn_despacho = findViewById(R.id.btn_despacho);
        btn_despacho.setOnClickListener(view -> routes(6));

        Button btn_guias = findViewById(R.id.btn_guias);
        btn_guias.setOnClickListener(view -> routes(7));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_principal,menu);
        MenuItem cerrarSesion = menu.findItem(R.id.cerrarSesion);
        cerrarSesion.setOnMenuItemClickListener(menuItem -> {
            cerrarSesion();
            return false;
        });

        return true;
    }

    public void validaRoles(){
        if(!usuario.getEsAdministrador().equalsIgnoreCase("S")){
            btn_personal.setVisibility(View.GONE);
            btn_productos.setVisibility(View.GONE);
        }
    }

    public void routes(Integer routeCode){

        switch (routeCode){
            case 1:
                Routes.usuariosController(context);
            case 2:
                Routes.productosController(context);
            case 3:
                Routes.visitasController(context);
            case 4:
                Routes.clientesController(context);
            case 5:
                Routes.ordenesController(context);
            case 6:
                Routes.despachoController(context);
            case 7:
                Routes.guiasController(context);
            default:
                Toast.makeText(context, "Opción ingresada no existe", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            cerrarSesion();
            super.onBackPressed();
            return;

        } else {
            Toast.makeText(getBaseContext(), "VUELVA A PULSAR PARA CERRAR SESIÓN", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

       cerrarSesion();
    }

    public void cerrarSesion(){
        Sessions.borraUsuario(context);
        Routes.loginController(context);
    }

}