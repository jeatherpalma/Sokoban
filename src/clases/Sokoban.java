package clases;

import clasesArboles.Arbol;
import clasesArboles.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    JTextArea jTextAreaResultado,jTextAreaMovimientos,jTextAreaMovimientosRCX;
    JScrollPane jScrollPaneREsultado, jScrollPaneMovimientos,jScrollPaneMovimientosRCX;
    JComboBox jComboBoxLevels;
    JButton jButtonStarSearch;
    ImageIcon imageIcon = new ImageIcon("src/clases/tablero1.png");
    JLabel jLabelImagen, jLabelImagen2, jLabelImagen3, jLabelImagen4, jLabelImagen5 , jLabelImagen6;
    int alto=0,ancho=0;

    //tablero
    public Sokoban(){

        //Creacion de la interfaz
        jFramePrincipal = new JFrame("Sokoban");
        jFramePrincipal.setLayout(null);
        jFramePrincipal.setSize(800,620);
        jFramePrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*Generaciónde de contenido de la ventana******************************/

        //Titulo
        jLabelTitutlo = new JLabel("Sokoban");
        jLabelTitutlo.setBounds(350, 10,180,30);
        jLabelTitutlo.setFont(fontTitutlo);

        //ComboBox de los niveles
        String levels [] = {"Level 1", "Level 2", "Level 3","Level 4", "Level 5", "Level 6"};
        jComboBoxLevels = new JComboBox(levels);
        jComboBoxLevels.setSelectedIndex(0);
        jComboBoxLevels.setBounds(30,100,180,30);
        jComboBoxLevels.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {

                int seleccion = jComboBoxLevels.getSelectedIndex();
                if(seleccion==1){
                    jLabelImagen.setVisible(false);
                    jLabelImagen3.setVisible(false);
                    jLabelImagen5.setVisible(false);
                    jLabelImagen4.setVisible(false);
                    jLabelImagen6.setVisible(false);
                    jLabelImagen2.setVisible(true);
                    jFramePrincipal.repaint();
                }else if(seleccion==0){
                    jLabelImagen.setVisible(true);
                    jLabelImagen3.setVisible(false);
                    jLabelImagen5.setVisible(false);
                    jLabelImagen4.setVisible(false);
                    jLabelImagen6.setVisible(false);
                    jLabelImagen2.setVisible(false);
                    jFramePrincipal.repaint();
                }else if(seleccion==2){
                    jLabelImagen.setVisible(false);
                    jLabelImagen3.setVisible(true);
                    jLabelImagen5.setVisible(false);
                    jLabelImagen4.setVisible(false);
                    jLabelImagen6.setVisible(false);
                    jLabelImagen2.setVisible(false);
                    jFramePrincipal.repaint();
                }else if(seleccion==3){
                    jLabelImagen.setVisible(false);
                    jLabelImagen3.setVisible(false);
                    jLabelImagen5.setVisible(false);
                    jLabelImagen4.setVisible(true);
                    jLabelImagen6.setVisible(false);
                    jLabelImagen2.setVisible(false);
                    jFramePrincipal.repaint();
                }else if(seleccion==4){
                    jLabelImagen.setVisible(false);
                    jLabelImagen3.setVisible(false);
                    jLabelImagen5.setVisible(true);
                    jLabelImagen4.setVisible(false);
                    jLabelImagen6.setVisible(false);
                    jLabelImagen2.setVisible(false);
                    jFramePrincipal.repaint();
                }else if(seleccion==5){
                    jLabelImagen.setVisible(false);
                    jLabelImagen3.setVisible(false);
                    jLabelImagen5.setVisible(false);
                    jLabelImagen4.setVisible(false);
                    jLabelImagen6.setVisible(true);
                    jLabelImagen2.setVisible(false);
                    jFramePrincipal.repaint();
                }
                cargarJuego(seleccion);


            }
        });

        //Boton de inicio
        jButtonStarSearch = new JButton("Start search");
        jButtonStarSearch.setBounds(30,150,180,30);
        jButtonStarSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mv.banderaGeneral = false;
                mv.contador=0;
                String resul = createSolution(getTableroLineal(ancho,alto,tablero));
                pilaDeNodosExpandir.clear();
                mv.historial2.clear();
                mv.movimientos.clear();
                trayectoria.clear();
                Node aux =null;
                jTextAreaMovimientosRCX.setText("");
                jTextAreaMovimientos.setText("");
                jTextAreaResultado.setText("");

                //Se agrega el juego
                nodoGenerado = Arbol.nuevoArbol(null,tablero,"Inicio");
                mv.historial2.put(mv.convierteMatrizString(tablero, alto,ancho),mv.convierteMatrizString(tablero, alto,ancho));
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
                            JOptionPane.showMessageDialog(null, "No se pudo encontrar slución");
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

                    char [] movimientosRCX = null;
                    //Se manda a llamar la clase Cardinalidad que es la que modifica los movimientos reales
                    Cardinalidad cardinalidad = new Cardinalidad(movimientoConSur,"Norte");
                    movimientosRCX = cardinalidad.getMovimientosRobot();
                    jTextAreaMovimientosRCX.append("T=Arriba\nA=Abajo\nD=Derecha\nI=Izquierda\n====\n\n");
                    for(int i=0; i<movimientosRCX.length-1; i++){
                        jTextAreaMovimientosRCX.append(String.valueOf(movimientosRCX[i])+"\n");

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
        jTextAreaResultado.setLineWrap(true);
        jScrollPaneREsultado = new JScrollPane(jTextAreaResultado);
        jScrollPaneREsultado.setBounds(230,140,300,445);

        //Area de resultados reales en SOKOBAN
        JLabel jLabelTextMovimientos = new JLabel("Movimientos Reales");
        jLabelTextMovimientos.setBounds(580,100,180,30);
        jLabelTextMovimientos.setFont(new Font("Arial",Font.BOLD,15));
        jTextAreaMovimientos = new JTextArea();
        jScrollPaneMovimientos = new JScrollPane(jTextAreaMovimientos);
        jScrollPaneMovimientos.setBounds(580,140,200,200);

        /**Zona de movimientos en RCX*/
        JLabel jLabelMovimientosRCX = new JLabel("Movimientos en RCX");
        jLabelMovimientosRCX.setBounds(580,350,180,30);
        jLabelMovimientosRCX.setFont(new Font("Arial",Font.BOLD,15));
        jTextAreaMovimientosRCX = new JTextArea();
        jScrollPaneMovimientosRCX = new JScrollPane(jTextAreaMovimientosRCX);
        jScrollPaneMovimientosRCX.setBounds(580,390,200,200);

        /*Zona de las imagenes*/
        agregarImagenes();
        cargarJuego(0);
        /*******Agregamos contenido a la ventana*******/
        jFramePrincipal.add(jLabelTitutlo);
        jFramePrincipal.add(jComboBoxLevels);
        jFramePrincipal.add(jButtonStarSearch);
        jFramePrincipal.add(jScrollPaneREsultado);
        jFramePrincipal.add(jScrollPaneMovimientos);
        jFramePrincipal.add(jLabelTextArea);
        jFramePrincipal.add(jLabelTextMovimientos);
        jFramePrincipal.add(jLabelMovimientosRCX);
        jFramePrincipal.add(jScrollPaneMovimientosRCX);

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

    public void agregarImagenes(){
        jLabelImagen = new JLabel();
        jLabelImagen.setBounds(50,240,100,100);
        Icon imf = new ImageIcon(imageIcon.getImage().getScaledInstance(jLabelImagen.getHeight(),jLabelImagen.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen.setIcon(imf);
        jFramePrincipal.add(jLabelImagen);

        ImageIcon imageIcon2 = new ImageIcon("src/clases/tablero2.png");
        jLabelImagen2 = new JLabel();
        jLabelImagen2.setBounds(50,240,100,100);
        Icon imf2 = new ImageIcon(imageIcon2.getImage().getScaledInstance(jLabelImagen2.getHeight(),jLabelImagen2.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen2.setIcon(imf2);
        jLabelImagen2.setVisible(false);
        jFramePrincipal.add(jLabelImagen2);

        ImageIcon imageIcon3 = new ImageIcon("src/clases/tablero3.png");
        jLabelImagen3 = new JLabel();
        jLabelImagen3.setBounds(50,240,100,100);
        Icon imf3 = new ImageIcon(imageIcon3.getImage().getScaledInstance(jLabelImagen3.getHeight(),jLabelImagen3.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen3.setIcon(imf3);
        jLabelImagen3.setVisible(false);
        jFramePrincipal.add(jLabelImagen3);

        ImageIcon imageIcon4 = new ImageIcon("src/clases/tablero4.png");
        jLabelImagen4 = new JLabel();
        jLabelImagen4.setBounds(50,240,100,100);
        Icon imf4 = new ImageIcon(imageIcon4.getImage().getScaledInstance(jLabelImagen4.getHeight(),jLabelImagen4.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen4.setIcon(imf4);
        jLabelImagen4.setVisible(false);
        jFramePrincipal.add(jLabelImagen4);

        ImageIcon imageIcon5 = new ImageIcon("src/clases/tablero5.png");
        jLabelImagen5 = new JLabel();
        jLabelImagen5.setBounds(50,240,100,100);
        Icon imf5 = new ImageIcon(imageIcon5.getImage().getScaledInstance(jLabelImagen5.getHeight(),jLabelImagen5.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen5.setIcon(imf5);
        jLabelImagen5.setVisible(false);
        jFramePrincipal.add(jLabelImagen5);

        ImageIcon imageIcon6 = new ImageIcon("src/clases/tablero6.png");
        jLabelImagen6 = new JLabel();
        jLabelImagen6.setBounds(50,240,100,100);
        Icon imf6 = new ImageIcon(imageIcon6.getImage().getScaledInstance(jLabelImagen6.getHeight(),jLabelImagen6.getWidth(), Image.SCALE_SMOOTH));
        jLabelImagen6.setIcon(imf6);
        jLabelImagen6.setVisible(false);
        jFramePrincipal.add(jLabelImagen6);
    }

    public void cargarJuego(int numeroGame){
        String [][]game6 = {{"#","#","#","#","#","#","#","#"},
                {"#"," ",".",".","#","#","#","#"},
                {"#"," ","C"," "," "," "," ","#"},
                {"#"," "," ","#","C","#"," ","#"},
                {"#"," ","P"," ",".","C"," ","#"},
                {"#","#","#","#","#","#","#","#"}
        };
        String game1 [][]={{"#","#","#","#"},
                {"#",".",".","#"},
                {"#","C","C","#"},
                {"#"," "," ","#"},
                {"#","#"," ","#"},
                {"#","P"," ","#"},
                {"#","#","#","#"}};

        String game2 [][]={
                {"#","#","#","#","#","#"},
                {"#","#"," "," ","#","#"},
                {"#","P","C"," ","#","#"},
                {"#","#","C"," ","#","#"},
                {"#","#"," ","C"," ","#"},
                {"#",".","C"," "," ","#"},
                {"#",".",".","X",".","#"},
                {"#","#","#","#","#","#"},
        };

        String game3 [][]={{"#","#","#","#","#","#","#","#"},
                {"#",".","."," ","P"," "," ","#"},
                {"#","C"," ","#"," "," "," ","#"},
                {"#"," "," ","#","#","C"," ","#"},
                {"#"," "," ","C"," "," ",".","#"},
                {"#","#","#","#","#","#","#","#"}};

        String game5 [][]={{"#","#","#","#","#","#"},
                {"#","."," "," ",".","#"},
                {"#"," ","C","C"," ","#"},
                {"#","#"," ","C"," ","#"},
                {"#","P"," "," ",".","#"},
                {"#","#","#","#","#","#"}};
        if(numeroGame==0){
            tablero = game1;
            alto=4;
            ancho=7;
        }else if(numeroGame==1){
            tablero = game2;
            alto=6;
            ancho=8;
        }else if(numeroGame==2){
            tablero = game3;
            alto=8;
            ancho=6;
        }else if(numeroGame==3){
            tablero = game3;
            alto=8;
            ancho=6;
        }else if(numeroGame==4){
            tablero = game5;
            alto=6;
            ancho=6;
        }else if(numeroGame==5){
            tablero = game6;
            alto=8;
            ancho=6;
        }
    }


}
