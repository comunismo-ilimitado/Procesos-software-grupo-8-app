package com.example.procesossoftware;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fragment_s1 extends Fragment {

    private TextView textViewFragment;
    private String newTextPending;
    private Integer[] semana;
    private int dia;
    private Registro r;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s1, container, false);

        // Obtener referencia al ImageButton
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        // Obtener referencia al TextView del fragmento
        textViewFragment = view.findViewById(R.id.textViewFragment);

        //recuperamos la información de cigarros
        r = getReg();
        if(r==null){ //no se ha creado todavia
            r = new Registro();
            setReg(r);
        }
        else{ //actualizamos el registro al dia y la fecha actual
            Calendar calendar = Calendar.getInstance();
            int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            diaSemana--;
            if(diaSemana==0){
                diaSemana=7;
            }

            r.SetDay(diaSemana);
            r.SetWeek(numeroSemana);
            setReg(r);
        }
        semana = r.reg.get(r.reg.size()-1);
        dia = r.lastDay;

        // Mostramos el numero de cigarros que llevamos
        textViewFragment.setText(getString(R.string.cigarros_fumados, semana[r.lastDay]));

        if (newTextPending != null) {
            newTextPending = null; // Resetea el texto pendiente
        }

        // Configurar un OnClickListener para el ImageButton
        imageButton.setOnClickListener(v -> {
            // Manejar la acción cuando se toca el ImageButton
            semana[dia]++;
            changeText("Cigarros fumados: " + semana[dia]);
            setReg(r); // Guardar cambios en las preferencias compartidas
        });

        return view;
    }
    // Método público para cambiar el texto del TextView
    public void changeText(String newText) {
        if (textViewFragment != null) {
            textViewFragment.setText(newText);
        }
        else {
            newTextPending = newText; // Almacena el nuevo texto si el fragmento no se ha inflado aún
        }
    }

    public Registro getReg(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("registro2", null);
        if (json != null) {
            Gson gson = new GsonBuilder().create();
            Registro regDev = gson.fromJson(json, Registro.class);
            return regDev;
        }
        return null;
    }
    public void setReg(Registro reg){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

// Obtiene un editor para modificar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(reg);
        editor.putString("registro2",json);
        editor.apply(); // Guarda los cambios
    }

}