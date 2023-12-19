package com.example.procesossoftware;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_s2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_general = inflater.inflate(R.layout.fragment_s2, container, false);
        View view = inflater.inflate(R.layout.grafico_cigarros, (ViewGroup) view_general);
        Registro r = getReg();
        setAhorro(view_general,r);
        setGrafica(view,r);
        //((ViewGroup)view_general).addView(view);


        return view_general;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Actualiza la gráfica cada vez que el fragmento se vuelve visible
        Registro reg = getReg();
        setGrafica(getView(), reg);  // Le pasa la view de fragment2.xml, pero funciona igual
        setAhorro(getView(), reg);
    }

    public void setAhorro(View viewgroup, Registro registro){
        TextView text = viewgroup.findViewById(R.id.ahorro_texto);

        Double balance = Estimaciones.ahorroSemanal(registro);

        if (balance >= 0) {
            String strBalance = String.format("%.02f", balance);
            text.setText("Esta semana has ahorrado " + strBalance + "€");
        } else {
            String strBalance = String.format("%.02f", -balance);
            text.setText("Esta semana has gastado " +  strBalance + "€");
        }
    }

    public void setGrafica(View view, Registro reg){
        // Cuando accede desde el onResume, view no es la de graifco_cigarros.xml y aún así encuentra barChart
        BarChart barChart = view.findViewById(R.id.barChart);

        // Configurar la descripción (título) del gráfico
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        //Quita el eje y de la derecha
        barChart.getAxisRight().setDrawLabels(false);

        List<String> xValues = Arrays.asList("7", "6", "5", "4", "3", "2", "1");

        //CONFIGURA EL EJE X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition((XAxis.XAxisPosition.BOTTOM));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        //CONFIGURA EJE Y
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(20f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(8);
        yAxis.setDrawGridLines(false);
        List<BarEntry> entries1 = new ArrayList<>();
        if(reg.reg.size()<2){//si solo hay una semana guardada mostramos los dias que tenemos
            Integer[] semana = reg.reg.get(reg.reg.size()-1);
            int j = 0;
            for(int i = 1; i<=reg.lastDay;i++){
                entries1.add(new BarEntry(j, semana[i]+4));
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
                entries1.add(new BarEntry(i,ultimos7[i]));
            }
        }



        BarDataSet dataSet = new BarDataSet(entries1, "Cigarros fumados");
        dataSet.setColor(Color.BLUE);
        //dataSet.setCircleColor(Color.RED);
        BarData lineData = new BarData(dataSet);

        barChart.setData(lineData);
        // Actualizar el gráfico
        barChart.invalidate();
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