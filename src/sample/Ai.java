package sample;
import java.util.Random;

import static sample.Main.*;


public class Ai {

    private static byte row,col;
    private static byte[] rowArr = new byte[boardSize*2];
    private static byte[] colArr = new byte[boardSize*2];
    private static int pieceLeft;

    static void update() {
        pieceLeft = 0;
        for(byte r=0;r<boardSize;r++) {
            for(byte c=0;c<boardSize;c++) {

                if(states[r][c]==WHITE) {
                    rowArr[pieceLeft] = r;
                    colArr[pieceLeft] = c;
                    pieceLeft++;
                }

            }
        }
    }

    static void selectAiCell() {
        
        update();
        
        int random = new Random().nextInt(pieceLeft);
        row = rowArr[random];
        col = colArr[random];
    }

    static byte getRow() {
        return row;
    }

    static byte getCol() {
        return col;
    }

    static void makeMove() {
        boolean[][] available = destinations();

        for(byte r=0;r<boardSize;r++) {
            for(byte c=0;c<boardSize;c++) {
                if(available[r][c]==true) {
                    row = r;
                    col = c;
                    System.out.println(row+"."+col+" ekhane move marbo");
                    return;
                }
            }
        }
    }

}
