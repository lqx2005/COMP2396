import java.io.*;
import java.net.*;
/**
 * The GameClient class handles the client-side operations of the game, including GUI, network communication.
 * @author Li Qixuan
 * @version 1.0
 * @since 2025-12-06
 */
public class GameClient {
    private GameFrame gameFrame;
    private Socket socket;
    private PrintWriter sender;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private char marker;
    private BufferedReader receiver;
    /**
     * Constructor for GameClient class. Initializes the components of the client.
     * @throws IOException if an I/O error occurs when creating the socket or streams.
     */
    public GameClient() throws IOException {
        gameFrame = new GameFrame(this);
        socket = new Socket("127.0.0.1", 5000);
        sender = new PrintWriter(socket.getOutputStream(), true);
        gamePanel = gameFrame.getGamePanel();
        scorePanel = gameFrame.getScorePanel();
        receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String playerID;
        playerID = receiver.readLine();
        gameFrame.setPlayerID(playerID);
        System.out.println("Assigned ID: " + playerID);
        if(playerID.equals("PLAYER1")) {
            marker = 'X';
        } else if(playerID.equals("PLAYER2")) {
            marker = 'O';
        }
    }
    private class ServerHandler extends Thread {
        private Socket socket;
        private BufferedReader receiver;
        public ServerHandler(Socket socket) throws IOException {
            this.socket = socket;
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        public void run() {
            try {
                String order;
                while(true) {
                    order = receiver.readLine();
                    System.out.println("Received: " + order);
                    String[] parts = order.split(" ");
                    if(parts[0].equals("START")) {
                        gameFrame.gameStart();
                    } else if(parts[0].equals("MOVE")) {
                        int row = Integer.parseInt(parts[2]);
                        int col = Integer.parseInt(parts[3]);
                        String mark = parts[1];
                        if(mark.equals("X")) {
                            gamePanel.setX(row, col);
                        } else if(mark.equals("O")) {
                            gamePanel.setO(row, col);
                        }
                        gameFrame.switchTurn();
                        gameFrame.updateTurnMessage();
                    } else if(parts[0].equals("GAMEOVER")) {
                        String message = parts[1];
                        gameFrame.gameOver(message);
                        if(message.equals("DRAW")) {
                            scorePanel.updateScores(ScorePanel.ScoreOption.DRAW);
                        } else if(message.equals("PLAYER1")) {
                            scorePanel.updateScores(ScorePanel.ScoreOption.PLAYER1_WIN);
                        } else if(message.equals("PLAYER2")) {
                            scorePanel.updateScores(ScorePanel.ScoreOption.PLAYER2_WIN);
                        }
                    } else if(parts[0].equals("GAMEEND")) {
                        gameFrame.gameEnd();
                    }
                }
            } catch(IOException e) {
            	gameFrame.gameEnd();
            }
        }
    }
    private void run() throws IOException {
        ServerHandler handler = new ServerHandler(socket);
        handler.start();
    }
    /**
     * Send a message to the server.
     * @param message The message which will be sent to the server.
     */
    public void sendMessage(String message) {
        sender.println(message);
    }
    /**
     * Send a move to the server.
     * @param row The row of the move.
     * @param col The column of the move.
     */
    public void sendMove(int row, int col) {
        String moveMessage = "MOVE " + marker + " " + row + " " + col;
        sendMessage(moveMessage);
    }
    /**
     * The main method to start the GameClient.
     * @param args Command line arguments. Not used.
     */
    public static void main(String[] args) {
        try {
            GameClient client = new GameClient();
            client.run();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}