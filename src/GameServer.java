import java.io.*;
import java.net.*;
/**
 * The GameServer class handles the server-side operations of the game, including network communication with clients,
 * game state management, etc.
 * @author Li Qixuan
 * @version 1.0
 * @since 2025-12-06
 */
public class GameServer {
    private ServerSocket serverSocket;
    private Socket socketP1, socketP2;
    private PrintWriter senderP1, senderP2;
    private PlayerHandler[] playerHandlers;
    private int current;
    private GameLogic gameLogic;
    private boolean gameStarted;
    private int playerCount;
    /**
     * Constructor for GameServer class. creates the server socket and waits for client connections.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    public GameServer() throws IOException {
        serverSocket = new ServerSocket(5000);
        socketP1 = serverSocket.accept();
        senderP1 = new PrintWriter(socketP1.getOutputStream(), true);
        senderP1.println("PLAYER1");
        socketP2 = serverSocket.accept();
        senderP2 = new PrintWriter(socketP2.getOutputStream(), true);
        senderP2.println("PLAYER2");
        playerHandlers = new PlayerHandler[2];
        playerHandlers[0] = new PlayerHandler(socketP1, 0);
        playerHandlers[1] = new PlayerHandler(socketP2, 1);
        gameLogic = new GameLogic();
        playerCount = 0;
    }
    private void gameStart() {
        broadcastMessage("START");
        gameLogic.resetBoard();
        current = 0;
        gameStarted = true;
    }
    private void gameOver() {
        gameStarted = false;
        playerCount = 0;
    }
    private void checkGameStart() {
       if(playerCount >= 2) {
            gameStart();
       }
    }
    private class PlayerHandler extends Thread {
        private Socket socket;
        private BufferedReader receiver;
        private PrintWriter sender;
        private int playerID;
        public PlayerHandler(Socket socket, int playerID) throws IOException {
            this.socket = socket;
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sender = new PrintWriter(socket.getOutputStream(), true);
            this.playerID = playerID;
        }
        public void run() {
            try {
                String message;
                while (true) {
                    message = receiver.readLine();
                    if(message == null) {
                        playerHandlers[1 - playerID].sendMessage("GAMEEND");
                    }
                    if(gameStarted) {
                        if(playerHandlers[current] == this) {
                            String[] parts = message.split(" ");
                            // System.out.println("Received: " + message);
                            if(parts[0].equals("MOVE")) {
                                int row = Integer.parseInt(parts[2]);
                                int col = Integer.parseInt(parts[3]);
                                if(!gameLogic.checkValid(row, col)) {
                                    continue;
                                }
                                String mark = parts[1];
                                char marker = mark.charAt(0);
                                gameLogic.setMark(row, col, marker);
                                broadcastMessage(message);
                                if(gameLogic.checkWinner(marker)) {
                                    broadcastMessage("GAMEOVER " + (playerID == 0 ? "PLAYER1" : "PLAYER2"));
                                    gameOver();
                                } else if(gameLogic.isBoardFull()) {
                                    broadcastMessage("GAMEOVER DRAW");
                                    gameOver();
                                } else {
                                    current = 1 - current;
                                }
                            }
                        }    
                    } else {
                        if(message.equals("READY")) {
                            playerCount++;
                            checkGameStart();
                        }
                    }
                    
                }
            } catch (IOException e) {
                playerHandlers[1 - playerID].sendMessage("GAMEEND");
            }
        }
        public void sendMessage(String message) {
            sender.println(message);
        }
    }
    private void broadcastMessage(String message) {
        playerHandlers[0].sendMessage(message);
        playerHandlers[1].sendMessage(message);
    }
    private void run() throws IOException {
        playerHandlers[0].start();
        playerHandlers[1].start();
    }
    /**
     * The main method is used to start the GameServer.
     * @param args Command line arguments. (Not used.)
     * @throws IOException if an I/O error occurs when starting the server.
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.run();
    }
}