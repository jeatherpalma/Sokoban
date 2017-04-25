package Test;

import clases.Sokoban;

/**
 * Created by jeather on 4/24/17.
 */
public class TestSokoban {
    public static void main(String[] args) {
        String [][]game = {{"#","#","#","#","#","#","#","#"},
                           {"#"," ",".",".","#","#","#","#"},
                           {"#"," ","C"," "," "," "," ","#"},
                           {"#"," "," ","#","C","#"," ","#"},
                           {"#"," ","P"," ",".","C"," ","#"},
                           {"#","#","#","#","#","#","#","#"}
        };
        Sokoban sk = new Sokoban(6,8,game);
    }
}
