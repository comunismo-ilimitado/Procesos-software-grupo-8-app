package com.example.procesossoftware;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fragment_s1 extends Fragment {

    private TextView textViewFragment;
    private String newTextPending;
    private int cont;
    private Integer[] semana;
    private int dia;
    private Registro r;
    private Button buttonC;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s1, container, false);

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

            r.SetDay(diaSemana);
            r.SetWeek(numeroSemana);
        }
        semana = r.reg.get(r.reg.size()-1);
        dia = r.lastDay;
        // Mostramos el numero de cigarros que llevamos


        textViewFragment.setText(""+semana[r.lastDay]);

        if (newTextPending != null) {
            newTextPending = null; // Resetea el texto pendiente
        }
        buttonC = view.findViewById(R.id.button);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semana[dia]++;
                changeText(semana[dia]+"");
                setReg(r); //guardamos en el dispositivo los cigarros que lleva
            }
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
    public TextView getTextViewFragment(){
        return textViewFragment;
    }
    public void setNumCigarros(int n){
        Context context = getContext();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

// Obtiene un editor para modificar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("nCigarros", n);
        editor.apply(); // Guarda los cambios
    }
    public int getNumCigarros(){
        Context context = getContext();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

// Carga un número desde SharedPreferences
        int nCigarros = sharedPreferences.getInt("nCigarros", 0);
        return nCigarros;
    }
    public Registro getReg(){
        Context context = getContext();
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
        Context context = getContext();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

// Obtiene un editor para modificar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(reg);
        editor.putString("registro2",json);
        editor.apply(); // Guarda los cambios
    }

}