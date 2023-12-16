package com.example.procesossoftware;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fragment_s1 extends Fragment {

    private TextView textViewFragment;
    private TextView cont;
    private String newTextPending;
    private Integer[] semana;
    private int dia;
    private Registro r;
    private boolean flag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s1, container, false);
        flag=true;

        // Obtener referencia al ImageButton
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        // Obtener referencia al TextView del fragmento
        textViewFragment = view.findViewById(R.id.textViewFragment);
        cont = view.findViewById(R.id.textView);

        //recuperamos la información de cigarros
        r = getReg();
        if(r==null){ //no se ha creado todavia
            r = new Registro();
            setReg(r);
            flag=true;
        }
        else{ //actualizamos el registro al dia y la fecha actual
            Calendar calendar = Calendar.getInstance();
            int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            diaSemana--;
            if(diaSemana==0){
                diaSemana=7;
            }
            if(diaSemana!=r.lastDay || numeroSemana!=r.lastWeek) flag = true;
            changeCont(diaSemana,numeroSemana);
            r.SetDay(diaSemana);
            r.SetWeek(numeroSemana);
            setReg(r);
        }
        semana = r.reg.get(r.reg.size()-1);
        dia = r.lastDay;

        // Mostramos el numero de cigarros que llevamos
        //textViewFragment.setText(getString(R.string.cigarros_fumados, semana[r.lastDay]));
        textViewFragment.setText("  Cigarros fumados   \n \n " + semana[r.lastDay]+"\n");
        cont.setText("  Dias sin fumar   \n \n"+r.numDias + "\n");

        if (newTextPending != null) {
            newTextPending = null; // Resetea el texto pendiente
        }

        // Configurar un OnClickListener para el ImageButton
        imageButton.setOnClickListener(v -> {
            // Manejar la acción cuando se toca el ImageButton
            semana[dia]++;
            changeText("  Cigarros fumados   \n \n " + semana[dia]+"\n");
            r.setNumDias(0);
            cont.setText("  Dias sin fumar   \n \n"+r.numDias + "\n");
            setReg(r); // Guardar cambios en las preferencias compartidas
        });

        if(flag){
            CreatePopUp(inflater,view);
        }

        return view;
    }

    private void changeCont(int diaSemana, int numeroSemana) {
        if(numeroSemana == r.lastWeek){
            r.setNumDias(r.numDias+(diaSemana-r.lastDay));
        }
        else{
            int dif = (numeroSemana - r.lastWeek)*7;
            if (r.lastDay < diaSemana ){
                dif += (diaSemana - r.lastDay);
            }
            else{
                dif -= (r.lastDay-diaSemana);
            }
            r.setNumDias(dif);

        }
    }

    private void CreatePopUp(LayoutInflater inflater, View view) {
        View popUpView = inflater.inflate(R.layout.fragment_pop_up, null);
        TextView textView = popUpView.findViewById(R.id.randomAdvice);

        // Leer consejos desde el archivo de texto
        List<String> consejosList = readAdvicesFromFile("consejos.txt");

        // Obtener un consejo aleatorio
        if (!consejosList.isEmpty()) {
            Random random = new Random();
            int result = random.nextInt(consejosList.size());
            textView.setText(consejosList.get(result));
        }
        // Crea un objeto AlertDialog con el diseño inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(popUpView);

        // Muestra el popup
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button button = popUpView.findViewById(R.id.cerrar);
        button.setOnClickListener(v -> alertDialog.dismiss());
    }

    private List<String> readAdvicesFromFile(String filename) {
        List<String> consejosList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().getAssets().open(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                consejosList.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consejosList;
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