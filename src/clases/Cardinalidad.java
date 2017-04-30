package clases;

/**
 * Created by jeather on 4/29/17.
 */

public class Cardinalidad {

    String cardinalidad;
    String [] movimientos;
    public Cardinalidad(String [] movimientos,String cardinalidad){
        this.movimientos = movimientos;
        this.cardinalidad = cardinalidad;
    }

    public String [] getMovimientosRobot(){

        String movimientoReales [] = new String [movimientos.length];

        for (int i = 0; i < movimientoReales.length; i++) {
            if(cardinalidad.equals("Norte")){
                if(movimientos[i].equals("Izquierda")){
                    cardinalidad = "Oeste";

					/*Hacer movimiento Izquierda*/
                    movimientoReales[i] = "Izquierda";

                }else if(movimientos[i].equals("Derecha")){
                    cardinalidad = "Este";

					/*Hacer movimiento Derecha*/
                    movimientoReales[i] = "Derecha";

                }else if(movimientos[i].equals("Arriba")){

					/*Hacer movimiento Arriba*/
                    movimientoReales[i] = "Arriba";

                }else if(movimientos[i].equals("Abajo")){
                    cardinalidad = "Sur";
					/*Hacer movimiento Abajo*/

                    movimientoReales[i] = "Abajo";
                }
            }else if(cardinalidad.equals("Este")){

                if(movimientos[i].equals("Arriba")){
                    cardinalidad = "Norte";
					/*Hacer movimiento Izquierda*/
                    movimientoReales[i] = "Izquierda";

                }else if(movimientos[i].equals("Abajo")){
                    cardinalidad = "Sur";
					/*Hacer movimiento Derecha*/
                    movimientoReales[i] = "Derecha";

                }else if(movimientos[i].equals("Izquierda")){
                    cardinalidad = "Oeste";

					/*Hacer movimiento Abajo*/

                    movimientoReales[i] = "Abajo";
                }else if(movimientos[i].equals("Derecha")){

					/*Hacer movimiento Arriba*/

                    movimientoReales[i] = "Arriba";
                }
            }else if(cardinalidad.equals("Oeste")){

                if(movimientos[i].equals("Arriba")){
                    cardinalidad = "Norte";
					/*Hacer movimiento Derecha*/
                    movimientoReales[i] = "Derecha";

                }else if(movimientos[i].equals("Abajo")){
                    cardinalidad = "Sur";
					/*Hacer movimiento Izquierda*/
                    movimientoReales[i] = "Izquierda";

                }else if(movimientos[i].equals("Izquierda")){

					/*Hacer movimiento Arriba*/
                    movimientoReales[i] = "Arriba";

                }else if(movimientos[i].equals("Derecha")){
                    cardinalidad = "Este";
					/*Hacer movimiento Abajo*/
                    movimientoReales[i] = "Abajo";

                }
            }else if(cardinalidad.equals("Sur")){

                if(movimientos[i].equals("Arriba")){
                    cardinalidad = "Norte";
					/*Hacer movimiento Abajo*/
                    movimientoReales[i] = "Abajo";

                }else if(movimientos[i].equals("Abajo")){

					/*Hacer movimiento Arriba*/

                    movimientoReales[i] = "Arriba";
                }else if(movimientos[i].equals("Izquierda")){
                    cardinalidad = "Oeste";
					/*Hacer movimiento Derecha*/
                    movimientoReales[i] = "Derecha";

                }else if(movimientos[i].equals("Derecha")){
                    cardinalidad = "Este";
					/*Hacer movimiento Izquierda*/
                    movimientoReales[i] = "Izquierda";

                }
            }
        }
        return movimientoReales;
    }

    
}