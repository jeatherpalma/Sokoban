package clases;

import clasesArboles.Node;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by jeather on 14/03/17.
 */
public class A_Estrella {

    public int  getExpandir(Vector<Node> nodosPosiblesAEXpandir, int ancho, int alto){

        int valor = calculoDeHeuristica(nodosPosiblesAEXpandir.get(0).getsokoban(), ancho, alto,nodosPosiblesAEXpandir.get(0));
        int posicion =0;

        for (int i = 1; i < nodosPosiblesAEXpandir.size(); i++) {
            if (valor > calculoDeHeuristica(nodosPosiblesAEXpandir.get(i).getsokoban(), ancho, alto, nodosPosiblesAEXpandir.get(i))) {
                valor = calculoDeHeuristica(nodosPosiblesAEXpandir.get(i).getsokoban(), ancho, alto,nodosPosiblesAEXpandir.get(i));
                posicion = i;
            } else {
            }
        }

        return posicion;
    }



    //Metodo que emplea una suma de movimientos para estar en la posición indicada (Manhattan)
    public int calculoDeHeuristica(String [][] game, int alto, int ancho, Node aux){

        int sumaDistanciaManhattan = 0;
        int auxiliarSuma = 1000;

        for (int i = 0; i<alto; i++){
            for (int j=0; j<ancho; j++){
                if(game[i][j].equals("C")){
                    for(int x=0; x<alto; x++){
                        for (int y=0; y<ancho; y++){
                            if(game[x][y].equals(".") || game[x][y].equals("P.")){
                                int distancia = Math.abs(i-x)+Math.abs(j-y);
                                auxiliarSuma = Menor(distancia,auxiliarSuma);

                            }
                        }
                    }
                    sumaDistanciaManhattan+=auxiliarSuma;
                    auxiliarSuma =1000;
                }
            }
        }
        auxiliarSuma =0;
        for (int i = 0; i<alto; i++){
            for (int j=0; j<ancho; j++){
                if(game[i][j].equals("P") || game[i][j].equals("P.")){
                    for(int x=0; x<alto; x++){
                        for (int y=0; y<ancho; y++){
                            if(game[x][y].equals("C")){
                                int distancia = Math.abs(i-x)+Math.abs(j-y);
                                auxiliarSuma = Mayor(distancia,auxiliarSuma);

                            }
                        }
                    }
                    sumaDistanciaManhattan+=auxiliarSuma;
                    auxiliarSuma =0;
                }
            }
        }


        //Suma del costo para llegar al nodo a manhattan
        sumaDistanciaManhattan=sumaDistanciaManhattan-1;
        while(aux!=null){
            sumaDistanciaManhattan += 1;
            aux=aux.getProfundidad2(aux);

        }
        if (detectaCajasEstancadas(game, ancho, alto)){
            sumaDistanciaManhattan+=1000;
        }
        return sumaDistanciaManhattan;
    }

    //Función para obtener la menor distancia hacia los objetivos
    public int Menor(int a, int b){
        if(a<b){
            return a;
        }else{
            return b;
        }
    }
    //Función para sacar la distancia mayor del player a la caja
    public int Mayor(int a, int b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }

    public boolean detectaCajasEstancadas(String game[][], int ancho, int alto){
        boolean ban = false;
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if(game[i][j]=="C"){
                    if(game[i][j-1]=="#" && game[i-1][j]=="#" || game[i][j-1]=="#" && game[i+1][j]=="#" ||game[i][j+1]=="#" && game[i-1][j]=="#"
                            ||game[i][j+1]=="#" && game[i+1][j]=="#"){
                        ban=true;
                    }
                }
            }
        }

        return ban;
    }
}




