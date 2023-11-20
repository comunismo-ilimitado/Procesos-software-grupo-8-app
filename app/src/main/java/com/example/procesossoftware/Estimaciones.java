package com.example.procesossoftware;

public class Estimaciones {
    public static int ahorroSemanal(Registro registro){
        int cuenta = 0;
        Integer[] semana = registro.reg.get(registro.reg.size()-1);
        for (int i : semana){
            cuenta+=i;
        }
        return Math.max(8-cuenta,0);
    }
}
