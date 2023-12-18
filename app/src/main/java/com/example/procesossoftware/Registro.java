package com.example.procesossoftware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Registro implements Serializable {
    int dailyCigaretteAverage;
    ArrayList<Integer[]> reg;
    int lastWeek;
    int lastDay;
    int numDias;
    public Registro() {
        Calendar calendar = Calendar.getInstance();
        int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        this.numDias = 0;
        this.reg = new ArrayList<>();
        Integer[] semana = new Integer[8];
        semana[0] = numeroSemana;
        for(int i = 1;i<8;i++){
            semana[i] = 0;
        }
        reg.add(semana);
        this.lastDay=diaSemana;
        this.lastWeek=numeroSemana;
        this.dailyCigaretteAverage = 8;  // default
    }
    public void SetDay(int i){
        this.lastDay = i;
    }
    public void SetWeek(int n){
        for(int i = lastWeek+1;i<=n;i++){
            Integer[] semana = new Integer[8];
            semana[0] = i;
            for(int j = 1;j<8;j++){
                semana[j] = 0;
            }
            this.reg.add(semana);
        }
        this.lastWeek=n;
    }
    public void setNumDias(int n){
        this.numDias = n;
    }
    public int getNumDias(){
        return this.numDias;
    }
    // Para cuando se haga que el usuario meta su media de cigarros fumados
    public int getDailyCigaretteAverage() {
        return dailyCigaretteAverage;
    }

    public void setDailyCigaretteAverage(int dailyCigaretteAverage) {
        this.dailyCigaretteAverage = dailyCigaretteAverage;
    }
}
