package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Declaración de Variables
    private int jugadores;
    private int[] casilla;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciamos el array casillas que identifica cada casilla y la almacena en el array
         casilla = new int[9];

         casilla[0] = R.id.a1;
         casilla[1] = R.id.a2;
         casilla[2] = R.id.a3;
         casilla[3] = R.id.b1;
         casilla[4] = R.id.b2;
         casilla[5] = R.id.b3;
         casilla[6] = R.id.c1;
         casilla[7] = R.id.c2;
         casilla[8] = R.id.c3;

    }
    // Método aJugar que se inicia al pulsar en cualquier botón

    public void aJugar(View view){

        ImageView imagen;

        // Reseteamos las casillas para ponerlas todas en blanco
        for (int cadaCasilla:casilla){
            imagen =  findViewById(cadaCasilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        // Identificamos el tipo de juego; 1 ó 2 jugadores
        jugadores = 1;
        if(view.getId()== R.id.dosJugadores){
            jugadores=2;
        }

        // Identificamos la dificultad
        RadioGroup configDifi = findViewById(R.id.configDificultad);
        int idDificultad = configDifi.getCheckedRadioButtonId();

        if (idDificultad == R.id.facil){
            idDificultad=0;
        }else if (idDificultad == R.id.normal){
            idDificultad=1;
        }else if (idDificultad == R.id.dificil){
            idDificultad=2;
        }

        // Inhabilitamos los botones para no tocarlos durante la partida
            // Instanciamos la clase partida
        partida = new Partida (idDificultad);
        Button boton = findViewById(R.id.unJugador);
        Button boton2 = findViewById(R.id.dosJugadores);

        boton.setEnabled(false);
        boton2.setEnabled(false);
        configDifi.setAlpha(0);
    }


    // Método toque que se encarga de las acciones cuando tocamos sobre unas de las casillas del tablero
    public void toque(View view){

        // Comprobamos que se haya iniciado la partida, en caso contrario se saldrá del método
        if (partida==null){
            return;
        }

        // Definimos que casilla ha sido pulsada
        int casillaTocada = 0;

        for (int i=0;i<casilla.length;i++){
            if (casilla[i]==view.getId()){
                casillaTocada = i;
                break;
            }
        }

        // Pulsamos una casilla y la dibuja con un círculo
        if (!partida.comprobarCasilla(casillaTocada)){
            // Con return saldremos del método y no se ejecutarán las siguiente líneas del método.
            return;
        }
        marcarCasilla(casillaTocada);
            // Comprobamos que no haya victoria o empate. En caso negativo, damos turno al siguiente jugador
        int resultado = partida.turno();

        if(resultado > 0){
            termina(resultado);
            return;
        }


        // El programa escoge la casilla donde va a dibujar y dibuja.
        if (jugadores==1) {
            casillaTocada = partida.ia();


            // Comprobamos que la casilla no sea la misma que hemos tocado nosotros. En caso que sea la misma, que seleccione otra.

            while (!partida.comprobarCasilla(casillaTocada)) {
                casillaTocada = partida.ia();
            }
            marcarCasilla(casillaTocada);

            // Comprobamos de nuevo que no haya victoria o empate. En caso negativo, damos turno al siguiente jugador
            resultado = partida.turno();
            if (resultado > 0) {
                termina(resultado);
            }
        }
    }
    // Método que se encarga de definir el tipo de objeto a dibujar según el turno.
    private void marcarCasilla(int casillaNum){

        // Comprobamos de que jugador es el turno para poner un círculo o un aspa
        ImageView imagen = findViewById(casilla[casillaNum]);

        if(partida.jugador == 1){
            imagen.setImageResource(R.drawable.circulo);
        }else {
            imagen.setImageResource(R.drawable.aspa);
        }

    }
    // Método que se encarga de definir el mensaje de finalización de partida y lo muestra.
    private void termina (int resultado){
        String mensaje;

        if(resultado == 1){
            mensaje = "¡Ganan los círculos!";
        }else if (resultado == 2){
            mensaje = "¡Ganan las aspas!";
        }else {
            mensaje = "¡Empate!";
        }

        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        // Terminamos el juego y habilitamos de nuevo los botones.

        partida = null;
        ((Button) findViewById(R.id.unJugador)).setEnabled(true);
        ((Button) findViewById(R.id.dosJugadores)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.configDificultad)).setAlpha(1);

    }

}