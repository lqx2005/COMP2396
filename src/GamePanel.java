import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * The GamePanel class extends JPanel class and constructs the interactive game board for Tic Tac Toe.
 * It handles user interactions, game logic, and computer moves.
 * @author Li Qixuan
 * @version 1.0
 * @since 2025-11-15
 */
public class GamePanel extends JPanel {
    private JButton[][] buttons;
    private GameFrame gameFrame;
    private ScorePanel scorePanel;
    private GameClient gameClient;
    private char marker;
    /**
     * Constructor to initialize a GamePanel instance.
     * @param gameFrame The GameFrame instance associated with this game.
     * @param scorePanel The ScorePanel instance associated with this game.
     */
    public GamePanel(GameClient gameClient, GameFrame gameFrame, ScorePanel scorePanel) {
        this.gameClient = gameClient;
        this.gameFrame = gameFrame;
        this.scorePanel = scorePanel;
        initComponents();
    }
    private void initComponents() {
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("SansSerif", Font.PLAIN, 100));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setOpaque(false);
                buttons[i][j].setEnabled(false);
                buttons[i][j].addActionListener(e -> {
                    // receive the button click event.
                    // find the position of the button clicked.
                    // send the move to the server via socket.
                    JButton source = (JButton) e.getSource();
                    int row = -1, col = -1;
                    for(int r = 0; r < 3; r++) {
                        for(int c = 0; c < 3; c++) {
                            if(buttons[r][c] == source) {
                                row = r;
                                col = c;
                                break;
                            }
                        }
                        if(row != -1) break;
                    }
                    gameClient.sendMove(row, col);
                });
                add(buttons[i][j]);
            }
        }
    }
    /**
     * Set mark X at (row, col).
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public void setX(int row, int col) {
        buttons[row][col].setText("X");
        buttons[row][col].setForeground(Color.GREEN);
    }
    /**
     * Set mark O at (row, col).
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public void setO(int row, int col) {
        buttons[row][col].setText("O");
        buttons[row][col].setForeground(Color.RED);
    }
    /**
     * Enable the buttons, reset the button texts.
     */
    public void gameStart() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
    }
}
