package com.example.procesossoftware;

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
}