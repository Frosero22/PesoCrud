package com.appcrud.pesosaludablecrud.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appcrud.pesosaludablecrud.API.ApiModelsResponse.Clientes;
import com.appcrud.pesosaludablecrud.R;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.HolderClientes>{

    private List<Clientes> lsCLientes;
    private Clientes clientes;
    private static Context context;

    public ClientesAdapter(List<Clientes> lsCLientes, Context context){
        this.lsCLientes = lsCLientes;
        this.context = context;
    }

    @Override
    public ClientesAdapter.HolderClientes onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientes_cardview,parent,false);
        v.setOnClickListener((View.OnClickListener) this);
        return new HolderClientes(v);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HolderClientes holder, int position) {
        holder.txvCodigoCliente.setText(lsCLientes.get(position).getCodigoCliente());
        String strNombreCliente = "";

        strNombreCliente = lsCLientes.get(position).getPrimerNombre()+" "+lsCLientes.get(position).getPrimerApellido();

        holder.txvNombreCliente.setText(strNombreCliente);
        holder.txvEdad.setText(edad(position));
        holder.btn_ver_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return lsCLientes.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String edad(Integer position){

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(lsCLientes.get(position).getFechaNacimiento(), fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);

        String strFechaNacimiento = "";

        if(periodo.getDays() < 1 && periodo.getMonths() < 1 && periodo.getYears() < 1 ){
            lsCLientes.get(position).setFechaNacimiento("Recien Nacido");
        }else{

            if (periodo.getYears() < 1) {

                if (periodo.getMonths() < 2) {
                    if(periodo.getMonths() < 1 ){
                        if(periodo.getDays() == 1 ){
                            strFechaNacimiento = periodo.getDays() + " DÍA";
                        }else{
                            strFechaNacimiento = periodo.getDays() + " DÍAS";
                        }
                    }else{
                        strFechaNacimiento = periodo.getMonths() + " MES";
                    }

                } else {
                    strFechaNacimiento = periodo.getMonths() + " MESES";
                }

            } else if(periodo.getYears() > 1 ) {

                if(periodo.getYears() > 1 && periodo.getMonths() < 2){
                    strFechaNacimiento = periodo.getYears()+" AÑOS y "+ periodo.getMonths() + " MES";
                }else if(periodo.getYears() > 1 && periodo.getMonths() > 1){
                    strFechaNacimiento = periodo.getYears()+" AÑOS y "+ periodo.getMonths() + " MESES";
                }

            }



        }
        return strFechaNacimiento;
    }

    public class HolderClientes extends RecyclerView.ViewHolder{
        TextView txvCodigoCliente;
        TextView txvNombreCliente;
        TextView txvEdad;
        Button btn_ver_informacion;


        public HolderClientes(@NonNull View itemView) {
            super(itemView);

            txvCodigoCliente = itemView.findViewById(R.id.txvCodigoCliente);
            txvNombreCliente = itemView.findViewById(R.id.txvNombreCliente);
            txvEdad = itemView.findViewById(R.id.txvEdad);
            btn_ver_informacion = itemView.findViewById(R.id.btn_ver_informacion);


    }
 }
}
