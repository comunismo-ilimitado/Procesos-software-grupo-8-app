package com.example.procesossoftware;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_s2 extends Fragment {

    private TextView textViewFragment;
    private String newTextPending;
    private int cont;
    private Button buttonC;
    private LineChart lineChart;
    private List<String> xValues;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grafico_cigarros, container, false);

        setGrafica(view);

        return view;
    }
    // Método público para cambiar el texto del TextView

    public TextView getTextViewFragment(){
        return textViewFragment;
    }
    public void setGrafica(View view){
        lineChart = view.findViewById(R.id.lineChart);

        // Configurar la descripción (título) del gráfico
        Description description = new Description();
        description.setText("Dias/Cigarros");
        description.setPosition(170f,30f);
        lineChart.setDescription(description);

        //Quita el eje y de la derecha
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("7","6","5","4","3","2","1");

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
        yAxis.setAxisMaximum(80f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(8);
        yAxis.setDrawGridLines(false);

        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 70f));
        entries1.add(new Entry(1, 65f));
        entries1.add(new Entry(2, 54f));
        entries1.add(new Entry(3, 47f));
        entries1.add(new Entry(4, 32f));
        entries1.add(new Entry(5, 23f));
        entries1.add(new Entry(6, 16f));


        LineDataSet dataSet = new LineDataSet(entries1, "Cigarros fumados los ultimos 7 dias");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        // Actualizar el gráfico
        lineChart.invalidate();
    }
}