package com.example.procesossoftware;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_s2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grafico_cigarros, container, false);
        Registro r = getReg();
        setGrafica(view,r);

        return view;
    }

    public void setGrafica(View view, Registro reg){
        LineChart lineChart = view.findViewById(R.id.lineChart);

        // Configurar la descripción (título) del gráfico
        Description description = new Description();
        description.setText("Dias/Cigarros");
        description.setPosition(170f,30f);
        lineChart.setDescription(description);

        //Quita el eje y de la derecha
        lineChart.getAxisRight().setDrawLabels(false);

        List<String> xValues = Arrays.asList("7", "6", "5", "4", "3", "2", "1");

        //CONFIGURA EL EJE X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition((XAxis.XAxisPosition.BOTTOM));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        //CONFIGURA EJE X
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(20f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(8);
        yAxis.setDrawGridLines(false);
        List<Entry> entries1 = new ArrayList<>();
        if(reg.reg.size()<2){//si solo hay una semana guardada mostramos los dias que tenemos
            Integer[] semana = reg.reg.get(reg.reg.size()-1);
            int j = 0;
            for(int i = 1; i<=reg.lastDay;i++){
                entries1.add(new Entry(j, semana[i]));
                j++;
            }
        }
        else{ //si hay más de una semana ponemos lo sultimos 7 dias
            Integer[] ultimos7 = new Integer[8];
            Integer[] semana1 = reg.reg.get(reg.reg.size()-2);//semana anterior
            Integer[] semana2 = reg.reg.get(reg.reg.size()-1);
            Integer[] semana = semana2;
            int cont = reg.lastDay; //nos vamos al primero de los últimos 7 dias
            for(int i = 7;i>0;i--){
                ultimos7[i]=semana[cont];
                cont--;
                if(cont==0){
                    cont=7;
                    semana = semana1;
                }
            }
            for(int i = 1;i<=7;i++){
                entries1.add(new Entry(i,ultimos7[i]));
            }
        }



        LineDataSet dataSet = new LineDataSet(entries1, "Cigarros fumados los ultimos 7 dias");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        // Actualizar el gráfico
        lineChart.invalidate();
    }
    public Registro getReg(){ //ya se q deberia hacer un cargador de registro en vez de tener codigo duplicado
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("registro2", null);
        if (json != null) {
            Gson gson = new GsonBuilder().create();
            Registro regDev = gson.fromJson(json, Registro.class);
            return regDev;
        }
        return null;
    }
}