import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//listening to clients which are wis to connect
//and respond a new thread to handle them
public class Server {
    private ServerSocket serverSocket;
    private ServerUI serverUI;

    public Server(ServerSocket serverSocket, ServerUI serverUI) {
        this.serverSocket = serverSocket;
        this.serverUI = serverUI;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                String clientName = receiveClientName(socket); // Receive client's name
                serverUI.appendToDisplay("A new " + clientName + " has connected!"); // Update UI
                ClientHandler clientHandler = new ClientHandler(socket, clientName); // Pass client's name to ClientHandler

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private String receiveClientName(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
    }

    public void closeServerSocket(){
        try{
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        ServerUI serverUI = new ServerUI(); // Create instance of ServerUI
        Server server = new Server(serverSocket, serverUI);
        server.startServer();
    }
}
