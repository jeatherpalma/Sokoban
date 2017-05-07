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
                       ImprimeTablero(ancho,alto,trayectoria.get(i));
                        for (int j=0; j<ancho; j++){
                            for (int x=0; x<alto; x++){
                                if(trayectoria.get(i)[j][x].equals(" ")){
                                    jTextAreaResultado.append("[ ]");
                                }else
                                jTextAreaResultado.append(trayectoria.get(i)[j][x]+" ");

                            }

                            jTextAreaResultado.append("\n");
                        }
                        jTextAreaResultado.append("\n");


                    }
                    String movimientosSokoban [] = new String[movi.size()];
                    for (int i=movi.size()-1; i>=0; i--){
                        movimientosSokoban[i]=movi.get(i);

                    }

                    for(int i=movimientosSokoban.length-1; i>=0; i--){
                        jTextAreaMovimientos.append(movimientosSokoban[i]+"\n");
                    }
                    jTextAreaMovimientos.append("\n");


                    boolean bandAbajo = false;

                    Vector <String> movimientoConSur = new Vector<String>();

                    for(int i=movimientosSokoban.length-2; i>=0; i--){


                        try{
                            if(movimientosSokoban[i].contains("Caja")){

                                   if(i>0){
                                       if(movimientosSokoban[i-1].equals(movimientosSokoban[i])){
                                           movimientoConSur.add(movimientosSokoban[i]);
                                       }else{
                                           if(movimientoConSur.contains("AbajoForzoso")){
                                               movimientoConSur.add(movimientosSokoban[i]);
                                           }
                                           movimientoConSur.add(movimientosSokoban[i]);
                                           movimientoConSur.add("AbajoForzoso");
                                       }

                                   }else{
                                       if(movimientoConSur.contains("AbajoForzoso")){
                                           movimientoConSur.add(movimientosSokoban[i]);
                                       }
                                       movimientoConSur.add(movimientosSokoban[i]);
                                       movimientoConSur.add("AbajoForzoso");
                                   }
                            }else {
                                movimientoConSur.add(movimientosSokoban[i]);
                            }
                        }catch (Exception es){

                        }


                    }

                    for(int i=0; i<movimientoConSur.size()-1; i++){
                        System.out.println(movimientoConSur.get(i));
                    }




                    char [] movimientosRCX = null;
                    Cardinalidad cardinalidad = new Cardinalidad(movimientoConSur,"Norte");
                    movimientosRCX = cardinalidad.getMovimientosRobot();

                    System.out.println("Movimientos reales");
                    System.out.print("{");
                    for(int i=0; i<movimientosRCX.length-1; i++){
                        if (i==movimientosRCX.length-1){
                            System.out.print("'"+movimientosRCX[i]+"'");
                        }else
                        System.out.print("'"+movimientosRCX[i]+"',");
                    }
                    System.out.println("}");


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
        jTextAreaResultado.setLineWrap(true);
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

            if(tableroLineal.charAt(i)=='C' ||tableroLineal.charAt(i)=='P' ||tableroLineal.charAt(i)==' '){

            }else if(tableroLineal.charAt(i)=='.'){
                solucion+="X";
            }else{
                solucion+=tableroLineal.charAt(i);
            }
        }
        System.out.println(solucion);
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
