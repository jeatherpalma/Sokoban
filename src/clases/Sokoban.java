package clases;

import java.util.Vector;

/**
 * Created by jeather on 4/24/17.
 */
public class Sokoban {

    //Tablero de entrada
    String [][] tablero;
    String tableroLineal="";
    String solucion="";

    //Objeto clase Movimientos
    Movimientos mv = new Movimientos();

    //tablero
    public Sokoban(int ancho, int alto, String [][] game){

        tablero = new String[ancho][alto];

        System.out.println("Tablero del nivel: ");
        for (int i=0; i<ancho; i++){
            for (int j=0; j<alto; j++){

                tableroLineal+=game[i][j];
                tablero[i][j]=game[i][j];
                System.out.print(tablero[i][j]+ " ");
            }
            System.out.println();
        }




        createSolution();
        mv.getPlayer(ancho,alto,tablero,solucion);

    }


    //Función que crea la solucion
    public void createSolution(){

        //Creando la solución
        for (int i=0; i<tableroLineal.length(); i++){
            if(tableroLineal.charAt(i)=='C' ||tableroLineal.charAt(i)=='P' ){
                solucion+=' ';
            }else if(tableroLineal.charAt(i)=='.'){
                solucion+="#";
            }else{
                solucion+=tableroLineal.charAt(i);
            }
        }
    }

}
