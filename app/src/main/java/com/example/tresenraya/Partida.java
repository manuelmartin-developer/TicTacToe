package com.example.tresenraya;

import android.view.View;

import java.util.Random;

public class Partida {

    // Declaración de variables
    public final int dificultad;
    public int jugador;
    public int [] casillas = new int[9];
    private final int [][] COMBINACIONES = {{0,1,2},{3,4,5},{6,7,8},{0,3,6,},{1,4,7},{2,5,8},{0,4,8},{2,4,6,}};


    // Método constructor

    public Partida(int dificultad){
        jugador=1;
        this.dificultad=dificultad;
        // Igualamos todas la casillas a 0 => 0 indica que la casilla está libre. 1 ó 2 indica que la casilla ya está elegida por ese jugador
        for (int i=0; i<9;i++){
            casillas[i]=0;
        }
    }

    public Boolean comprobarCasilla(int casilla){
        if (casillas[casilla] != 0){
           return false;
        }else{
            casillas[casilla]=jugador;
        }
        return true;
    }

    public int dosEnRaya(int jugador_en_turno){
        int casilla = -1;
        int cuantas_lleva = 0;

        for (int i=0;i<COMBINACIONES.length;i++){
            for(int pos:COMBINACIONES[i]){

                if(casillas[pos] == jugador_en_turno){
                    cuantas_lleva++;
                }

                if(casillas[pos] == 0){
                    casilla=pos;
                }

                }
            if(cuantas_lleva == 2 && casilla!=-1){
                return casilla;
            }

            // Reseteamos la variable
            casilla=-1;
            cuantas_lleva=0;
                }
        return -1;
        }



    // Método que genera un número de casilla (0 al 8) aleatorio y devuelve el numero de casilla
    public int ia (){


        int casilla;

        casilla = dosEnRaya(2);
        // Nivel fácil
        if(casilla!=-1) return casilla;

        // Nivel normal. Este método impide la posibilidad de 3 en raya para el jugador
        if (dificultad>0){
            casilla = dosEnRaya(1);
            if (casilla != -1) return casilla;
        }

        // Nivel imposible. Marcará una esquina, si no hay posibilidad que el jugador 1 haga 3 en raya. De este modo, es imposible ganar

        if(dificultad == 2){
            if(casillas[0]==0)return 0;
            if(casillas[2]==0) return 2;
            if(casillas[6]==0) return 6;
            if(casillas[8]==0) return 8;


        }
        Random casilla_aleatoria = new Random();
        casilla = casilla_aleatoria.nextInt(9);

        return casilla;
    }

    public int turno(){

        Boolean empate = true;

        Boolean ultimoMoviviento = true;

        // Comprobaremos, antes de cambiar el turno, el estado de la partida:
        // 0 => todavía en juego
        // 1 => ganan los circulos
        // 2 => ganas las aspas
        // 3 => empate
        for (int i=0;i<COMBINACIONES.length;i++){
            for(int pos:COMBINACIONES[i]){
            // Comprobamos que aun queden movimientos

                if(casillas[pos] != jugador){
                    ultimoMoviviento = false;
                }
            // Si hay algún elemento en su estado inicial es que no ha sido marcado, por lo que no podrá haber empate.
                if (casillas[pos] == 0){
                    empate=false;
                }
            } // Cierre del for anidado
            if (ultimoMoviviento) return jugador;
            ultimoMoviviento=true;
        } // Cierre del for principal
        // Si se ha empatado, devolvemos un 3
        if(empate){
            return 3;
        }
        // Cambiamos el turno del jugador
        if (jugador==1){
            jugador=2;
        }else{
            jugador=1;
        }
        // Devolvemos un 0 en caso que no haya ganado aun nadie, ni haya habido empate
        return 0;
    }


}
