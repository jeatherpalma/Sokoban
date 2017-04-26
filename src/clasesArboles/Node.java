package clasesArboles;


public class Node {
	Node nodo;
	String [][] sokoban;
	public int profundidad=0;
	
	/////////Crea un nuevo nodo raiz
	public Node(String [][]sokoban){
		this.sokoban = sokoban;
		this.nodo = null;
	}	
	/////////Crea un nuevo nodo con referencia al padre
	public Node(Node nodo, String[][] sokoban){
		this.nodo = nodo;
		this.sokoban = sokoban;
		
	}
	//Regresa el padre de cualquier nodo
	public Node getPadre(){
		return nodo;
	}
	//Obtiene la profundidad del nodo
	public int getProfundidad(Node n){
		
		if(n!=null){
			profundidad++;
			getProfundidad(n.getPadre());
		}
			return profundidad;
	}

	public Node getProfundidad2(Node n){
		if(n!=null){
			return n.getPadre();
		}
		return n;
	}
	
	
	//Regresa el sokoban del nodo
	public String [][] getsokoban(){
		return sokoban;
	}
	
	
}
