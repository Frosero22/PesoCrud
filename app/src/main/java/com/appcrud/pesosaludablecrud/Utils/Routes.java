package com.appcrud.pesosaludablecrud.Utils;

import android.content.Context;
import android.content.Intent;

import com.appcrud.pesosaludablecrud.Controllers.ClientesController;
import com.appcrud.pesosaludablecrud.Controllers.DespachoController;
import com.appcrud.pesosaludablecrud.Controllers.GuiasController;
import com.appcrud.pesosaludablecrud.Controllers.LoginController;
import com.appcrud.pesosaludablecrud.Controllers.OrdenesPedidosController;
import com.appcrud.pesosaludablecrud.Controllers.PrincipalController;
import com.appcrud.pesosaludablecrud.Controllers.ProductosController;
import com.appcrud.pesosaludablecrud.Controllers.UsuariosController;
import com.appcrud.pesosaludablecrud.Controllers.VisitasController;
import com.appcrud.pesosaludablecrud.Parametrizaciones.CreacionClientesController;

public class Routes {

    public static void loginController(Context context){
        Intent intent = new Intent(context, LoginController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void principalController(Context context){
        Intent intent = new Intent(context, PrincipalController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void clientesController(Context context){
        Intent intent = new Intent(context, ClientesController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void productosController(Context context){
        Intent intent = new Intent(context, ProductosController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void ordenesController(Context context){
        Intent intent = new Intent(context, OrdenesPedidosController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void usuariosController(Context context){
        Intent intent = new Intent(context, UsuariosController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void visitasController(Context context){
        Intent intent = new Intent(context, VisitasController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void despachoController(Context context){
        Intent intent = new Intent(context, DespachoController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void guiasController(Context context){
        Intent intent = new Intent(context, GuiasController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void crearClientesControoler(Context context){
        Intent intent = new Intent(context, CreacionClientesController.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



}
