import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * ScorePanel class extends JPanel class to create a score panel for this game. 
 * It shows the number of time that the player wins, the computer wins, and draws.
 * @author Li Qixuan
 * @version 2.0
 * @since 2025-12-06
 */
public class ScorePanel extends JPanel {
    private int player1Score;
    private int player2Score;
    private int drawsScore;
    private JLabel player1ScoreLabel;
    private JLabel player1ScoreValue;
    private JLabel player2ScoreLabel;
    private JLabel player2ScoreValue;
    private JLabel drawsScoreLabel;
    private JLabel drawsScoreValue;
    public enum ScoreOption {
        PLAYER1_WIN,
        PLAYER2_WIN,
        DRAW
    }
    /**
     * Constructor to initialize a ScorePanel instance.
     */
    public ScorePanel() {
        player1Score = 0;
        player2Score = 0;
        drawsScore = 0;
        initComponents();
    }
    private void initComponents() {
        setPreferredSize(new Dimension(180, 0));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Score",
            TitledBorder.LEFT,
            TitledBorder.TOP
        ));
        setLayout(new GridLayout(3, 1));
        player1ScoreLabel = new JLabel("Player 1 Wins:", SwingConstants.LEFT);
        player1ScoreValue = new JLabel(String.valueOf(player1Score), SwingConstants.LEFT);
        player2ScoreLabel = new JLabel("Player 2 Wins:", SwingConstants.LEFT);
        player2ScoreValue = new JLabel(String.valueOf(player2Score), SwingConstants.LEFT);
        drawsScoreLabel = new JLabel("Draws:", SwingConstants.LEFT);
        drawsScoreValue = new JLabel(String.valueOf(drawsScore), SwingConstants.LEFT);
        add(player1ScoreLabel);
        add(player1ScoreValue);
        add(player2ScoreLabel);
        add(player2ScoreValue);
        add(drawsScoreLabel);
        add(drawsScoreValue);
    }
    /**
     * Update the scores when one game ends.
     * @param option option to indicate that which score should be updated.
     */
    public void updateScores(ScoreOption option) {
        if(option == ScoreOption.PLAYER1_WIN) {
            player1Score++;
        } else if(option == ScoreOption.PLAYER2_WIN) {
            player2Score++;
        } else if(option == ScoreOption.DRAW) {
            drawsScore++;
        }
        player1ScoreValue.setText(String.valueOf(player1Score));
        player2ScoreValue.setText(String.valueOf(player2Score));
        drawsScoreValue.setText(String.valueOf(drawsScore));
    }
}