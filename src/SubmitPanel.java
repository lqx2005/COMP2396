import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.lang.Thread;
/**
 * SubmitPanel class extends JPanel class to implement a panel for submitting player's name.
 * @author Li Qixuan
 * @version 2.0
 * @since 2025-12-06
 */
public class SubmitPanel extends JPanel {
    private JTextField nameField;
    private JButton submitButton;
    private JPanel buttonPanel;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private GameFrame gameFrame;
    /**
     * Constructor to initalize the components of the instance.
     * @param gameFrame The GameFrame instance associated with the game.
     */
    public SubmitPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        initComponents();
    }
    private void initComponents() {
        nameField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // get the name from the name field class.
            // disable the input function.
            // set player's name in the game frame class.
            String name = nameField.getText().trim();
            disableInput();
            gameFrame.setPlayer(name);
        });
        buttonPanel = new JPanel();
        messageLabel = new JLabel("Enter your name:", SwingConstants.LEFT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel = new JLabel("Current Time: " + LocalTime.now().format(formatter));
        Timer timer = new Timer(1000, e -> {
            // update the local time every one second.
            timeLabel.setText("Current Time: " + LocalTime.now().format(formatter));
        });
        timer.start();
        buttonPanel.add(messageLabel);
        buttonPanel.add(nameField);
        buttonPanel.add(submitButton);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(buttonPanel);
        add(timeLabel);
    }
    private void disableInput() {
        submitButton.setEnabled(false);
        nameField.setEnabled(false);
    }
}
