package clases;

import clasesArboles.Arbol;
import clasesArboles.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    /********************************Zona de la Interfaz**************************/
    JFrame jFramePrincipal;
    Font fontTitutlo = new Font("Aril",Font.BOLD,20);
    JLabel jLabelTitutlo;
    JTextArea jTextAreaResultado,jTextAreaMovimientos;
    JScrollPane jScrollPaneREsultado, jScrollPaneMovimientos;
    JComboBox jComboBoxLevels;
    JButton jButtonStarSearch;

    //tablero
    public Sokoban(int ancho, int alto, String [][] game){

        //Creacion de la interfaz
        jFramePrincipal = new JFrame("Sokoban");
        jFramePrincipal.setLayout(null);
        jFramePrincipal.setSize(800,600);
        jFramePrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*Generaciónde de contenido de la ventana******************************/

        //Titulo
        jLabelTitutlo = new JLabel("Sokoban");
        jLabelTitutlo.setBounds(350, 10,180,30);
        jLabelTitutlo.setFont(fontTitutlo);

        //ComboBox de los niveles
        String levels [] = {"Level 1", "Level 2", "Level 3"};
        jComboBoxLevels = new JComboBox(levels);
        jComboBoxLevels.setSelectedIndex(0);
        jComboBoxLevels.setBounds(30,100,180,30);

        //Boton de inicio
        jButtonStarSearch = new JButton("Start search");
        jButtonStarSearch.setBounds(30,150,180,30);
        jButtonStarSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                            JOptionPane.showMessageDialog(null, "Vector vacio");
                        }
                    }
                    int contador=0;
                    while(aux!=null){

                        trayectoria.add(aux.getsokoban());
                        movi.add(aux.getMovimiento());
                        aux=aux.getProfundidad2(aux);
                        contador++;

                    }

                    for (int i=trayectoria.size()-1; i>=0; i--)
                    {
                        for (int j=0; j<ancho; j++){
                            for (int x=0; x<alto; x++){
                                jTextAreaResultado.append(trayectoria.get(i)[j][x]+" ");
                            }
                            jTextAreaResultado.append("\n");
                        }
                        jTextAreaResultado.append("\n");

                    }

                    for (int i=movi.size()-1; i>=0; i--){
                        jTextAreaMovimientos.append(movi.get(i));
                        jTextAreaMovimientos.append("\n");
                    }

                }catch (IndexOutOfBoundsException ie){
                    ie.printStackTrace();
                }
            }
        });

        //Area de resultados
        JLabel jLabelTextArea = new JLabel("Movimientos en sokoban");
        jLabelTextArea.setBounds(230,100,240,30);
        jLabelTextArea.setFont(new Font("Arial",Font.BOLD,15));
        jTextAreaResultado = new JTextArea();
        jScrollPaneREsultado = new JScrollPane(jTextAreaResultado);
        jScrollPaneREsultado.setBounds(230,140,300,400);

        //Area de movimientos
        //Area de resultados
        JLabel jLabelTextMovimientos = new JLabel("Movimientos");
        jLabelTextMovimientos.setBounds(560,100,180,30);
        jLabelTextMovimientos.setFont(new Font("Arial",Font.BOLD,15));
        jTextAreaMovimientos = new JTextArea();
        jScrollPaneMovimientos = new JScrollPane(jTextAreaMovimientos);
        jScrollPaneMovimientos.setBounds(560,140,200,400);


        /*******Agregamos contenido a la ventana*******/
        jFramePrincipal.add(jLabelTitutlo);
        jFramePrincipal.add(jComboBoxLevels);
        jFramePrincipal.add(jButtonStarSearch);
        jFramePrincipal.add(jScrollPaneREsultado);
        jFramePrincipal.add(jScrollPaneMovimientos);
        jFramePrincipal.add(jLabelTextArea);
        jFramePrincipal.add(jLabelTextMovimientos);

        /**********Hacemos visible la ventana y la posicionamos en el centro*********/
        jFramePrincipal.setLocationRelativeTo(null);
        jFramePrincipal.setVisible(true);




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
