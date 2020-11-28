package com.example.tresenraya;

import android.view.View;

import java.util.Random;

public class Partida {

    // Declaración de variables
    public final int dificultad;
    public int jugador;
    public int [] casillas = new int[9];

    // Método constructor

    public Partida(int dificultad){
        jugador=1;
        this.dificultad=dificultad;
        // Igualamos todas la casillas a 0. 0 indica que la casilla está libre. 1 ó 2 indica que la casilla ya está elegida por ese jugador
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

    // Método que genera un número de casilla (0 al 8) aleatorio y devuelve el numero de casilla
    public int ia (){

        int casilla;
        Random casilla_aleatoria = new Random();
        casilla = casilla_aleatoria.nextInt(9);

        return casilla;
    }

    public void turno(){
        if (jugador==1){
            jugador=2;
        }else{
            jugador=1;
        }
    }

}
