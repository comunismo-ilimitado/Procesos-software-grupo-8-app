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

public class Fragment_s1 extends Fragment {

    private TextView textViewFragment;
    private String newTextPending;
    private int cont;
    private Button buttonC;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s1, container, false);

        // Obtener referencia al TextView del fragmento
        textViewFragment = view.findViewById(R.id.textViewFragment);
        // Mostramos el numero de cigarros que llevamos
        textViewFragment.setText(""+getNumCigarros());
        cont=getNumCigarros();

        if (newTextPending != null) {
            newTextPending = null; // Resetea el texto pendiente
        }
        buttonC = view.findViewById(R.id.button);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont++;
                changeText(cont+"");
                setNumCigarros(cont); //guardamos en el dispositivo los cigarros que lleva
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
}