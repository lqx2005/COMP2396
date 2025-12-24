/**
 * GameLogic class handles the logic of the Tic-Tac-Toe game.
 * @author Li Qixuan
 * @version 1.0
 * @since 2025-12-06
 */
public class GameLogic {
    public enum Cell {EMPTY, X, O}
    private Cell[][] board;
    /**
     * Constructor to initialize the board.
     */
    public GameLogic() {
        board = new Cell[3][3];
        resetBoard();
    }
    private Cell getCellFromChar(char c) {
        if(c == 'X') return Cell.X;
        else if(c == 'O') return Cell.O;
        else return Cell.EMPTY;
    }
    /**
     * Reset the board to the empty state.
     */
    public void resetBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }
    /**
     * Check whether the move at (row, col) is valid.
     * @param row The row of the move.
     * @param col The column of the move.
     * @return true if the move is valid and false otherwise.
     */
    public boolean checkValid(int row, int col) {
        return board[row][col] == Cell.EMPTY;
    }
    /**
     * Set the mark at (row, col) to the markChar.
     * @param row The row of the move.
     * @param col The column of the move.
     * @param markChar The character which presents the mark.
     */
    public void setMark(int row, int col, char markChar) {
        Cell mark = getCellFromChar(markChar);
        board[row][col] = mark;
    }
    /**
     * Check whether the player who uses markChar has already won the game.
     * @param markChar The character which is used by the player.
     * @return true if the player has already won and false otherwise.
     */
    public boolean checkWinner(char markChar) {
        Cell mark = getCellFromChar(markChar);
        for(int i = 0; i < 3; i++) {
            boolean checkRow = true, checkCol = true;
            for(int j = 0; j < 3; j++) {
                if(board[i][j] != mark) checkRow = false;
                if(board[j][i] != mark) checkCol = false;
            }
            if(checkRow || checkCol) return true;
        }
        boolean checkDiag1 = true, checkDiag2 = true;
        for(int i = 0; i < 3; i++) {
            if(board[i][i] != mark) checkDiag1 = false;
            if(board[i][2 - i] != mark) checkDiag2 = false;
        }
        if(checkDiag1 || checkDiag2) return true;
        return false;
    }
    /**
     * Check whether the board is full.
     * @return true if the board is full and false otherwise.
     */
    public boolean isBoardFull() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}