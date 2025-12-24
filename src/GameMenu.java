import javax.swing.*;
/**
 * GameMenu class extends JMenuBar class to implement the menu bar for the game.
 * @author Li Qixuan
 * @version 1.0
 * @since 2025-11-15
 */
public class GameMenu extends JMenuBar {
    private JMenu controlMenu;
    private JMenu helpMenu;
    private GameFrame gameFrame;
    /**
     * Constructor to initialize a GameMenu instance.
     * @param gameFrame The GameFrame instance associated with this menu.
     */
    public GameMenu(GameFrame gameFrame) {
        initComponents();
        this.gameFrame = gameFrame;
    }
    private void initComponents() {
        controlMenu = new JMenu("Control");
        helpMenu = new JMenu("Help");
        JMenuItem Exit = new JMenuItem("Exit");
        Exit.addActionListener(e -> gameFrame.Exit());
        JMenuItem Instructions = new JMenuItem("Instructions");
        Instructions.addActionListener(e -> {
            // Display game instructions in the information box.
            String instructions = "Game Instructions:\n\n"
                    + "1. The game is played on a 3x3 grid.\n"
                    + "2. Player is 'X' and Computer is 'O'.\n"
                    + "3. Player and computer take turns placing their marks in a empty cell.\n"
                    + "4. The first one who gets 3 of their marks in a row (horizontally, vertically, or diagonally) wins.\n"
                    + "5. If all cells are filled and no player has 3 in a row, the game ends in a draw.";
            JOptionPane.showMessageDialog(gameFrame, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });
        controlMenu.add(Exit);
        helpMenu.add(Instructions);
        add(controlMenu);
        add(helpMenu);
    }
}
