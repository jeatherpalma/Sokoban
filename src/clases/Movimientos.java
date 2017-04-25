package clases;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by jeather on 4/24/17.
 */
public class Movimientos {

    //Bandera de llegada al objetivo
    public boolean banderaGeneral=false;
    //Vector de matrices a expandir representan un movimiento en el puzzle
    Vector<int[][]> matExpandor = new Vector<>();
    //Map de los nodos expandidos
    public Map<String,String> historial2 = new HashMap<>();
    //Contador de nodos expandidos
    public int contador = 0;

    public void getPlayer(int ancho, int alto, String [][] juego, String solucion){
        contador++;
        if(solucion.equals(convierteMatrizStringEvitaPlayer(juego, ancho, alto))){
             banderaGeneral = true;
        }else {

            int posicionX = 0, posicionY = 0;

            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    if (juego[i][j] == "P") {
                        posicionX = i;
                        posicionY = j;

                        i = ancho;
                        j = alto;
                    }
                }

            }

            //Copia de matrices dependiendo del giro
            String[][] matDerecha = nuevaMatriz(juego, alto, ancho);
            String[][] matAbajo = nuevaMatriz(juego, alto, ancho);
            String[][] matArriba = nuevaMatriz(juego, alto, ancho);
            String[][] matIzquierda = nuevaMatriz(juego, alto, ancho);

            //Matrices voletadas dependiendo del gito
            String matGiradaDerecha;
            String matGiradaAbajo;
            String matGiradaArriba;
            String matGiradaIzquierda;



            if (juego[posicionX][posicionY - 1] == " ") {
                matIzquierda = mueveIzquierda(matIzquierda, alto, ancho);
                matGiradaIzquierda = convierteMatrizString(matIzquierda, alto, ancho);
                System.out.println("paso Izquierda");
            }
            if (juego[posicionX][posicionY + 1] == " ") {
                matDerecha = mueveDerecha(matDerecha, alto, ancho);
                matGiradaDerecha = convierteMatrizString(matDerecha, alto, ancho);
                System.out.println("paso Derecha");
            }
            if (juego[posicionX + 1][posicionY] == " ") {
                matAbajo = mueveAbajo(matAbajo, alto, ancho);
                matGiradaAbajo = convierteMatrizString(matAbajo, alto, ancho);
                System.out.println("paso Abajo");
            }

            if (juego[posicionX - 1][posicionY] == " ") {
                matArriba = mueveArriba(matArriba, alto, ancho);
                matGiradaArriba = convierteMatrizString(matArriba, alto, ancho);
                System.out.println("paso Arriba");
            }
            //Mueve la caja arriba
            if (juego[posicionX - 1][posicionY] == "C") {
               if(juego[posicionX-2][posicionY]!="#"){
                   if(juego[posicionX-2][posicionY]=="."){
                     matArriba = mueveCajaArriba(matArriba, alto, ancho);
                   }
               }
            }
           //Mueve la caja abajo
            if (juego[posicionX +1][posicionY] == "C") {
                if(juego[posicionX+2][posicionY]!="#"){
                    if(juego[posicionX-2][posicionY]=="."){
                        matAbajo = mueveCajaAbajo(matAbajo, alto, ancho);
                    }
                }
            }

            //Mueve la caja derecha
            if (juego[posicionX][posicionY + 1] == "C") {
                if(juego[posicionX][posicionY + 1]!="#"){
                    matDerecha = mueveCajaDerecha(matDerecha, alto, ancho);
                }
            }

            //Mueve la caja izquierda
            if (juego[posicionX][posicionY - 1] == "C") {
                if(juego[posicionX][posicionY - 1]!="#"){
                    matIzquierda = mueveCajaIzquierda(matIzquierda, alto, ancho);
                }
            }


        }
    }//Fin metodo getPlayer

    //Regresa una copia de la matriz orignial
    public String [][] nuevaMatriz(String mat[][], int ancho, int alto){

        String [][] matNew = new String[alto][ancho];

        for (int i = 0; i <alto; i++) {
            for (int j = 0; j < ancho; j++) {
                matNew[i][j] = mat[i][j];

            }
        }

        return matNew;
    }
    //Mueve hacia la Izquierda
    public String [][] mueveIzquierda(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = mat[i][j-1];
                    mat[i][j-1]="P";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }

    public String [][] mueveDerecha(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = mat[i][j+1];
                    mat[i][j+1]="P";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    public String [][] mueveArriba(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = mat[i-1][j];
                    mat[i-1][j]="P";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    public String [][] mueveAbajo(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = mat[i+1][j];
                    mat[i+1][j]="P";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    //Convierte la matriz a string
    public String convierteMatrizString(String mat[][], int ancho, int alto){
        String puzzleConcertido ="";
        for (int i=0; i<alto; i++){
            for (int j=0; j<ancho; j++){
                puzzleConcertido +=mat[i][j];
            }
        }

        return puzzleConcertido;
    }
    public String convierteMatrizStringEvitaPlayer(String mat[][], int ancho, int alto){
        String puzzleConcertido ="";
        for (int i=0; i<ancho; i++){
            for (int j=0; j<alto; j++){
                if(mat[i][j]=="P"){
                    puzzleConcertido+=" ";
                }else{
                puzzleConcertido +=mat[i][j];
                }
            }
        }
        return puzzleConcertido;
    }
    //Regresa un boolean si se encuentra el harray
    public boolean busquedaNodos(String key) {
        if(historial2.containsKey(key)){
            return false;
        }
        else
            return true;
    }

    public String [][] mueveCajaArriba(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = " ";
                    mat[i-1][j]="P";
                    mat[i-2][j]="#";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    public String [][] mueveCajaAbajo(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = " ";
                    mat[i+1][j]="P";
                    mat[i+2][j]="#";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    public String [][] mueveCajaIzquierda(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = " ";
                    mat[i][j-1]="P";
                    mat[i][j-2]="#";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
    public String [][] mueveCajaDerecha(String mat[][], int ancho, int alto){

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(mat[i][j]=="P"){
                    mat[i][j] = " ";
                    mat[i][j+1]="P";
                    mat[i][j+2]="#";
                    i=alto;
                    break;
                }
            }
        }
        return mat;
    }
}