package sample;
import java.util.Random;

import static sample.Main.*;

class Move {

    byte first_row,first_col,sec_row,sec_col;

    void setFirstMove(byte r,byte c) {
        first_row = r;
        first_col = c;
    }

    void setSecMove(byte r,byte c) {
        sec_row = r;
        sec_col = c;
    }
}

public class Ai {

    // for 8*8 players
    private static int pieceSquareTable[][] =
    {

        {-80, -25, -20, -20, -20, -20, -25, -80},
        {-25,  10,  10,  10,  10,  10,  10,  -25},
        {-20,  10,  25,  25,  25,  25,  10,  -20},
        {-20,  10,  25,  50,  50,  25,  10,  -20},
        {-20,  10,  25,  50,  50,  25,  10,  -20},
        {-20,  10,  25,  25,  25,  25,  10,  -20},
        {-25,  10,  10,  10,  10,  10,  10,  -25},
        {-80, -25, -20, -20, -20, -20, -25, -80}

    };

//    function minimax(node, depth, isMaximizingPlayer, alpha, beta):
//
//            if node is a leaf node :
//            return value of the node
//
//    if isMaximizingPlayer :
//    bestVal = -INFINITY
//        for each child node :
//    value = minimax(node, depth+1, false, alpha, beta)
//    bestVal = max( bestVal, value)
//    alpha = max( alpha, bestVal)
//            if beta <= alpha:
//            break
//            return bestVal
//
//    else :
//    bestVal = +INFINITY
//        for each child node :
//    value = minimax(node, depth+1, true, alpha, beta)
//    bestVal = min( bestVal, value)
//    beta = min( beta, bestVal)
//            if beta <= alpha:
//            break
//            return bestVal

    public static Move getRandomMove() {
        Ai.selectAiCell();
        Move move = new Move();
        move.setFirstMove(row,col);
        Ai.makeMove();
        move.setSecMove(row,col);
        return move;

    }



    private static byte row,col;
    private static byte[] rowArr = new byte[boardSize*2];
    private static byte[] colArr = new byte[boardSize*2];
    private static int pieceLeft;
    private static byte[][] temp_states = new byte[boardSize][boardSize];

    private static double getPieceTableValue() {
        double ret = 0.0;
        for(int i=0;i<boardSize;i++) {
            for(int j=0;j<boardSize;j++) {
                if(temp_states[i][j]==WHITE) {
                    ret += pieceSquareTable[i][j];
                }
            }
        }
        return  ret;
    }



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
        boolean[][] available = destinations(states,row,col);

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
