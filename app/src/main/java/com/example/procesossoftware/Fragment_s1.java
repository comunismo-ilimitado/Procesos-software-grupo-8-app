package com.example.procesossoftware;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Random;

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


        CreatePopUp(inflater,view);
        return view;
    }

    private void CreatePopUp(LayoutInflater inflater, View view) {


        View popUpView = inflater.inflate(R.layout.fragment_pop_up, null);

        TextView textView = popUpView.findViewById(R.id.randomAdvice);
        Consejos consejos = new Consejos();
        Random random = new Random();
        int result = random.nextInt(consejos.aConsejos.length);
        textView.setText(consejos.aConsejos[result]);





        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height =ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView,width,height, focusable);


        Button button = popUpView.findViewById(R.id.cerrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        view.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,300);
            }
        });


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