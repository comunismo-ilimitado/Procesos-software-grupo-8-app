package com.example.procesossoftware;

public class Estimaciones {
    // Supongamos que el costo promedio de un cigarro es de 0.25 euros
    private static final double COSTO_CIGARRO = 0.25;
    public static double ahorroSemanal(Registro registro){
        // Calcula la cantidad total de cigarros fumados en la última semana
        Integer[] semana = Ultimos(registro);

        int totalCigarros = 0;
        for (int i = 1; i <= 7 ; i++){
            totalCigarros += semana[i];
        }
        // Calcula el ahorro semanal en euros
        int mediaCigarros = registro.getDailyCigaretteAverage() * semana.length;  //.length por si el usuario lleva menos de una semana en la app
        double ahorroSemanal = (mediaCigarros - totalCigarros) * COSTO_CIGARRO;

        return ahorroSemanal;
    }

    private static Integer[] Ultimos(Registro r){
        Integer[] ultimosDias;
        if(r.reg.size() >= 2){  //si hay más de una semana ponemos lo sultimos 7 dias
            ultimosDias = new Integer[7];
            Integer[] semana1 = r.reg.get(r.reg.size()-2);//semana anterior
            Integer[] semana2 = r.reg.get(r.reg.size()-1);
            Integer[] semana = semana2;
            int cont = r.lastDay; //nos vamos al primero de los últimos 7 dias
            for(int i = 7; i > 0 ; i--){
                ultimosDias[i] = semana[cont];
                cont--;
                if(cont == 0){
                    cont = 7;
                    semana = semana1;
                }
            }
        } else {  //si solo hay una semana guardada mostramos los dias que tenemos
            ultimosDias = r.reg.get(0);
        }
        return ultimosDias;
    }
}
