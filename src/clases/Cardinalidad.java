package clases;

import java.util.Vector;

/**
 * Created by jeather on 4/29/17.
 */

public class Cardinalidad {

    String cardinalidad;
    Vector<String> movimientos;
    boolean ban = false;
    public Cardinalidad(Vector<String> movimientos, String cardinalidad){
        this.movimientos = movimientos;
        this.cardinalidad = cardinalidad;
    }

    public char [] getMovimientosRobot(){

        char movimientoReales [] = new char [movimientos.size()];
        String movimiento ="";
        for (int i = 0; i < movimientoReales.length; i++) {
            movimiento = movimientos.get(i);
            if(movimiento.contains("Caja")){
                movimiento=movimiento.replace("Caja","");

            }
            movimientoReales[i]= getMov(movimiento);
            

        }
      return movimientoReales;
    }



public char getMov(String movimiento) {
    char a=' ';
    if(movimiento.equals("AbajoForzoso")){
        if(cardinalidad.equals("Norte")){
            cardinalidad = "Sur";
        }else if(cardinalidad.equals("Sur")){
            cardinalidad = "Norte";
        }else if(cardinalidad.equals("Este")){
            cardinalidad = "Oeste";
        }else if(cardinalidad.equals("Oeste")){
            cardinalidad = "Este";
        }
        a='A';
    }else
    if(cardinalidad.equals("Norte")){
        if(movimiento.equals("Izquierda")){
            cardinalidad = "Oeste";

					/*Hacer movimiento Izquierda*/
            a= 'I';

        }else if(movimiento.equals("Derecha")){
            cardinalidad = "Este";
					/*Hacer movimiento Derecha*/
            a= 'D';

        }else if(movimiento.equals("Arriba")){

					/*Hacer movimiento Arriba*/
            a= 'T';

        }else if(movimiento.equals("Abajo")){
            cardinalidad = "Sur";
					/*Hacer movimiento Abajo*/

            a= 'A';
        }
    }else if(cardinalidad.equals("Este")){

        if(movimiento.equals("Arriba")){
            cardinalidad = "Norte";
					/*Hacer movimiento Izquierda*/
            a= 'I';

        }else if(movimiento.equals("Abajo")){
            cardinalidad = "Sur";
					/*Hacer movimiento Derecha*/
            a= 'D';

        }else if(movimiento.equals("Izquierda")){
            cardinalidad = "Oeste";

					/*Hacer movimiento Abajo*/

            a= 'A';
        }else if(movimiento.equals("Derecha")){

					/*Hacer movimiento Arriba*/

            a= 'T';
        }
    }else if(cardinalidad.equals("Oeste")){

        if(movimiento.equals("Arriba")){
            cardinalidad = "Norte";
					/*Hacer movimiento Derecha*/
            a= 'D';

        }else if(movimiento.equals("Abajo")){
            cardinalidad = "Sur";
					/*Hacer movimiento Izquierda*/
            a= 'I';

        }else if(movimiento.equals("Izquierda")){

					/*Hacer movimiento Arriba*/
            a= 'T';

        }else if(movimiento.equals("Derecha")){
            cardinalidad = "Este";
					/*Hacer movimiento Abajo*/
            a= 'A';

        }
    }else if(cardinalidad.equals("Sur")){

        if(movimiento.equals("Arriba")){
            cardinalidad = "Norte";
					/*Hacer movimiento Abajo*/
            a= 'A';

        }else if(movimiento.equals("Abajo")){

					/*Hacer movimiento Arriba*/

            a= 'T';
        }else if(movimiento.equals("Izquierda")){
            cardinalidad = "Oeste";
					/*Hacer movimiento Derecha*/
            a= 'D';

        }else if(movimiento.equals("Derecha")){
            cardinalidad = "Este";
					/*Hacer movimiento Izquierda*/
            a= 'I';

        }
    }
      return a;
    


}

}