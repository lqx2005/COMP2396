import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * The GameFrame class extends JFrame class and constructs the main window for the game.
 * It initializes and arranges all GUI components used in the game.
 * @author Li Qixuan
 * @version 2.0
 * @since 2025-12-06
 */
public class GameFrame extends JFrame {
    private JLabel messageTitle;
    public ScorePanel scorePanel;
    private SubmitPanel submitPanel;
    private GameMenu gameMenu;
    private GameClient gameClient;
    private GamePanel gamePanel;
    private String player, playerID;
    private int isYourTurn;
    private String[] TurnMessage = {
        "Valid move, waiting for your opponent.", 
        "Your opponent has moved. Now is your turn."
    };
    /**
     * Constructor to initialize the GameFrame instance.
     */
    public GameFrame(GameClient gameClient) {
        this.gameClient = gameClient;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setTitle("Tic Tac Toe");
        initComponents();
    }
    private void initComponents() {
        gameMenu = new GameMenu(this);
        setJMenuBar(gameMenu);
        messageTitle = new JLabel("Enter your player name...", SwingConstants.CENTER);
        scorePanel = new ScorePanel();
        submitPanel = new SubmitPanel(this);
        gamePanel = new GamePanel(this.gameClient, this, this.scorePanel);
        add(messageTitle, BorderLayout.NORTH);
        add(scorePanel, BorderLayout.EAST);
        add(submitPanel, BorderLayout.SOUTH);
        add(gamePanel, BorderLayout.CENTER);
        setVisible(true);
    }
    /**
     * Set the message title under the menu bar.
     * @param message The message displayed under the menu bar.
     */
    public void setMessageTitle(String message) {
        messageTitle.setText(message);
    }
    /**
     * Set the player's name and disable the input function.
     * @param name The name of the player.
     */
    public void setPlayer(String name) {
        player = name;
        if(playerID.equals("PLAYER1")) {
            setTitle("Tic Tac Toe-Player 1: " + player);
        } else {
            setTitle("Tic Tac Toe-Player 2: " + player);
        }
        setMessageTitle("WELCOME " + player.toUpperCase());
        gameClient.sendMessage("READY");
    }
    /**
     * Set the player's ID.
     * @param id The ID string of the player.
     */
    public void setPlayerID(String id) {
        playerID = id;
    }
    /**
     * Switch the turn between players.
     */
    public void switchTurn() {
        isYourTurn = 1 - isYourTurn;
    }
    /**
     * Update the title message to show whose turn it is.
     */
    public void updateTurnMessage() {
        setMessageTitle(TurnMessage[isYourTurn]);
    }
    /**
     * Start the game after player submits their name.
     * Set up which player goes first.
     * Update the turn message.
     */
    public void gameStart() {
        gamePanel.gameStart();
        if(playerID.equals("PLAYER1")) {
            isYourTurn = 1;
        } else {
            isYourTurn = 0;
        }
        updateTurnMessage();
    }
    /**
     * Handle the case when the game is over.
     * Show the message to indicate the result and ask whether the player wants to play again.
     * Will exit the game if the player chooses not to play again.
     * @param result The result of the game.
     */
    public void gameOver(String result) {
        String message = "";
        if(result.equals("DRAW")) {
            message = "It's a draw! Play again?";
        } else {
            if(result.equals(playerID)) {
                message = "Congratulations. You win! Do you want to play again?";
            } else {
                message = "You lose. Do you want to play again?";
            }
        }
        int choice = JOptionPane.showConfirmDialog(
            this,
            message,
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if(choice == JOptionPane.YES_OPTION) {
            gameClient.sendMessage("READY");
        } else {
            Exit();
        }
    }
    /**
     * Handle the case when one of the players leaaves the game.
     * Show a message and exit the game.
     */
    public void gameEnd() {
        String message = "Game Ends. One of the players left.";
        JOptionPane.showMessageDialog(
            this,
            message,
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );
        Exit();
    }
    /**
     * Exit the game application on the client side.
     */
    public void Exit() {
        System.exit(0);
    }
    /**
     * Get the GamePanel instance.
     * @return The GamePanel instance.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    /**
     * Get the ScorePanel instance.
     * @return The ScorePanel instance.
     */
    public ScorePanel getScorePanel() {
        return scorePanel;
    }
}