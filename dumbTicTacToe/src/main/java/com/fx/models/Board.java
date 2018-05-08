package com.fx.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    char[][] boardEntries= new char[3][3];
    public List<Integer> availablePlaces = new ArrayList<Integer>();

    public Board(char[] gameState){
        int k=0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                boardEntries[i][j] = gameState[k];
                k++;
            }
        }
    }

    public boolean movesAvailable(){
        availablePlaces.clear();
        int k=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardEntries[i][j] == '*'){
                    availablePlaces.add(k);
                }
                k++;
            }
        }
        return availablePlaces.size()>0;
    }

    public String getGameState(){
        StringBuilder gameState = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameState.append(boardEntries[i][j]);
            }
        }
        return gameState.toString();
    }

    public boolean makeMove(int row,int column,boolean isMoveByComputer){
        if(!isSpaceOccupied(row,column)){
            boardEntries[row][column]= (isMoveByComputer==true?'0':'1');
            return true;
        }else{
            return false;
        }
    }

    private boolean isSpaceOccupied(int row,int column){
        if (boardEntries[row][column]=='0' || boardEntries[row][column]=='1'){
            return true;
        }else{
            return false;
        }

    }

    public char[][] getBoardEntries() {
        return boardEntries;
    }

    public String getBoardInASCII(char oppCharacter){
        StringBuilder op = new StringBuilder();
        char[] keys = new char[]{'A','B','C'};
        op.append("  A B C\n");
        int k=0;
        for (int i = 0; i < 3; i++){
            op.append(keys[k] + "|");
            for (int j = 0; j < 3; j++) {
                if(boardEntries[i][j]=='0'){
                    op.append("X|");
                }else if(boardEntries[i][j]=='1'){
                    op.append(oppCharacter+"|");
                }else {
                    op.append(" |");
                }
            }
            op.append("\n");
            k++;
        }
        return op.toString();
    }
}
