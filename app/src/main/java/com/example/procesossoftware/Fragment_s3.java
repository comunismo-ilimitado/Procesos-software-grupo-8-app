package com.example.procesossoftware;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Fragment_s3 extends Fragment {
    private TextView textViewFragment;
    private AlertDialog alertDialog;
    private ArrayList<String> advices;
    private Button buttonChangeAdvice;
    private Button buttonAddAdvice;
    private Set<Integer> shownAdvices = new HashSet<>(); // Conjunto de índices de consejos mostrados


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s3, container, false);

        // Obtener referencia al TextView del fragmento
        textViewFragment = view.findViewById(R.id.textViewFragment);
        buttonChangeAdvice = view.findViewById(R.id.buttonC);
        buttonAddAdvice = view.findViewById(R.id.buttonAdd);



        // Cargar los consejos desde el archivo de texto en assets
        advices = loadAdvicesFromAsset();

        // Mostrar un consejo al iniciar
        showNextAdvice();

        // Configurar un listener para el botón de cambio de consejo
        buttonChangeAdvice.setOnClickListener(v -> showNextAdvice());
        buttonAddAdvice.setOnClickListener(v -> addAdvice());

        return view;
    }


    // Método para cargar los consejos desde el archivo de texto en assets
    private ArrayList<String> loadAdvicesFromAsset() {
        ArrayList<String> advicesList = new ArrayList<>();
        AssetManager assetManager = requireContext().getAssets();

        try {
            InputStream inputStream = assetManager.open("consejos.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                advicesList.add(line);
            }

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return advicesList;
    }

    // Método para mostrar el próximo consejo
    private void showNextAdvice() {
        if (advices != null && advices.size() > 0) {
            int currentAdviceIndex;
            do{
                currentAdviceIndex = new Random().nextInt(advices.size());
            } while (shownAdvices.contains(currentAdviceIndex));

            // Agrega el índice al conjunto de consejos mostrados
            shownAdvices.add(currentAdviceIndex);

            // Si todos los consejos han sido mostrados, reinicializa el conjunto
            if (shownAdvices.size() == advices.size()) {
                shownAdvices.clear();
            }

            String advice = advices.get(currentAdviceIndex);
            textViewFragment.setText(advice);
        }
    }
    private void addAdvice(){
        View popupView = getLayoutInflater().inflate(R.layout.addpopup, null);

        // Encuentra las vistas dentro del diseño inflado
        EditText textBox = popupView.findViewById(R.id.editTextId);
        Button btnAceptar = popupView.findViewById(R.id.guardar);
        Button btnCancelar = popupView.findViewById(R.id.cerrar);

        // Configura un clic para el botón "Aceptar"
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene el texto ingresado por el usuario
                String textoIngresado = textBox.getText().toString();
                addConsejo(textoIngresado);
                // Puedes hacer lo que desees con el texto (guardarlo en una variable, etc.)
                // ...

                // Cierra el popup
                alertDialog.dismiss();
            }
        });

        // Configura un clic para el botón "Cancelar"
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra el popup
                alertDialog.dismiss();
            }
        });

        // Crea un objeto AlertDialog con el diseño inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(popupView);

        // Muestra el popup
        alertDialog = builder.create();
        alertDialog.show();
    }
    // Método para guardar los consejos desde el archivo de texto en assets
    public void addConsejo(String consejo){

    }
}
