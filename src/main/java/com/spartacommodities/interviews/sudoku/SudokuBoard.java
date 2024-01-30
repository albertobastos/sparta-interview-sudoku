package com.spartacommodities.interviews.sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {

    private static final int SUBBOARDS = 3;
    private final int[][] board;
    private final int boardLength;

    public SudokuBoard(int[][] board) {
        this.board = board;
        this.boardLength = board.length;
    }

    private boolean isFilled(int value) {
        return 1 <= value && value <= boardLength;
    }

    // inclusive bounds
    private boolean isSubsetSolved(int xFrom, int yFrom, int xTo, int yTo) {
        Set<Integer> seen = new HashSet<>();
        for (int x=xFrom; x<=xTo; x++){
            for (int y = yFrom; y<=yTo; y++){
                int val = board[x][y];
                if (!isFilled(val)) return false;
                if (!seen.add(val)) return false;
            }
        }
        return true;
    }

    public boolean isSolved() {
        if (boardLength < 1 || board[0].length != boardLength) return false; // invalid board, not square!
        if (boardLength % SUBBOARDS != 0) return false; // invalid board, cannot be divided as sub-boards

        // rows and cols
        for (int t = 0; t < boardLength; t++) {
            if (!isSubsetSolved(t, 0, t, boardLength-1)) return false; // row t
            if (!isSubsetSolved(0, t, boardLength-1, t)) return false; // col t
        }

        // sub-blocks
        int subLength = boardLength / SUBBOARDS;
        for (int i=0; i<SUBBOARDS; i++)
            for (int j=0; j<SUBBOARDS; j++)
                if (!isSubsetSolved(
                    i*subLength,
                    j*subLength,
                    i*subLength+subLength-1,
                    j*subLength+subLength-1
                )) return false;

        return true;
    }
}
