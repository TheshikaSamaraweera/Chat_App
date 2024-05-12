import java.io.*;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;
    private ClientUI clientUI;

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public void setUsername(String username) {
        this.username = username;
        try {
            sendMessage(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        bufferedWriter.write(username + ": " + message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                try {
                    while ((msgFromGroupChat = bufferedReader.readLine()) != null) {
                        // Append message to UI using ClientUI instance
                        clientUI.appendMessage(msgFromGroupChat);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client client = new Client("localhost", 1234);
                ClientUI clientUI = new ClientUI(client);
                client.setClientUI(clientUI);
            }
        });
    }
}
