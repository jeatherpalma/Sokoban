package clases;

import clasesArboles.Arbol;
import clasesArboles.Node;

import javax.swing.*;
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
    //Pila de nodos a expandir
    Vector<Node> pilaDeNodosExpandir = new Vector<>();

    //Onjetos de la clase arbol y nodo
    Node nodoGenerado = null;
    Arbol arb;

    //Objeto de la heuristica
    A_Estrella aEstrella = new A_Estrella();

    //Vector de matrices a expandir
    Vector<String [][]> trayectoria = new Vector<>();
    Vector<String> movi = new Vector<>();

    //tablero
    public Sokoban(int ancho, int alto, String [][] game){

        tablero = game;
        mv.banderaGeneral = false;
        mv.contador=0;
        String resul = createSolution(getTableroLineal(ancho,alto,game));


        pilaDeNodosExpandir.clear();
        mv.historial2.clear();
        mv.movimientos.clear();
        trayectoria.clear();
        Node aux =null;

        //Se agrega el juego
        nodoGenerado = Arbol.nuevoArbol(null,tablero,"Inicio");
        mv.historial2.put(mv.convierteMatrizString(game, alto,ancho),mv.convierteMatrizString(game, alto,ancho));
        pilaDeNodosExpandir.addElement(nodoGenerado);
        try {
            while (mv.banderaGeneral==false) {
                if (!pilaDeNodosExpandir.isEmpty()) {
                    aux = pilaDeNodosExpandir.remove(aEstrella.getExpandir(pilaDeNodosExpandir, ancho, alto));
                    Vector<String[][]> matricesExpandir = mv.getPlayer(ancho, alto, aux.getsokoban(), resul);
                    ImprimeTablero(ancho,alto,aux.getsokoban());
                    if (mv.banderaGeneral) {

                        break;
                    }

                    for (int i = 0; i < matricesExpandir.size(); i++) {

                        nodoGenerado = Arbol.nuevoArbol(aux, matricesExpandir.get(i), mv.movimientos.get(i));
                        String puzzleExpandido = mv.convierteMatrizString(matricesExpandir.get(i), alto, ancho);
                        mv.historial2.put(puzzleExpandido, puzzleExpandido);
                        pilaDeNodosExpandir.addElement(nodoGenerado);
                        arb = new Arbol(nodoGenerado);

                    }
                    mv.movimientos.clear();

                }
                else {
                    JOptionPane.showMessageDialog(null, "Seacabo");
                }
            }
            int contador=0;
            while(aux!=null){

                trayectoria.add(aux.getsokoban());
                movi.add(aux.getMovimiento());
                aux=aux.getProfundidad2(aux);
                contador++;

            }

            for (int i=movi.size()-1; i>=0; i--){
                System.out.println(movi.get(i));
            }
            for (int i=trayectoria.size()-1;i>=0; i--){
                ImprimeTablero(ancho,alto,trayectoria.get(i));
            }
        }catch (IndexOutOfBoundsException ie){
                ie.printStackTrace();
        }




    }


    //Función que crea la solucion
    public String createSolution(String tableroLineal){
        String solucion="";
        //Creando la solución
        for (int i=0; i<tableroLineal.length(); i++){

            if(tableroLineal.charAt(i)=='C' ||tableroLineal.charAt(i)=='P' || tableroLineal.charAt(i)==' ' ){

            }else if(tableroLineal.charAt(i)=='.'){
                solucion+="C.";
            }else{
                solucion+=tableroLineal.charAt(i);
            }
        }
        return solucion;
    }
   public String getTableroLineal(int ancho, int alto, String game [][]){
       String tableroLineal="";
       for (int i=0; i<ancho; i++){
           for (int j=0; j<alto; j++){
               tableroLineal+=game[i][j];
           }

       }
       return tableroLineal;
   }
    //Imprime tablero
    public void ImprimeTablero(int ancho, int alto, String game [][]){
        System.out.println("Tablero del nivel: ");
        for (int i=0; i<ancho; i++){
            for (int j=0; j<alto; j++){
                System.out.print(game[i][j]+ " ");
            }
            System.out.println();
        }
    }


}
