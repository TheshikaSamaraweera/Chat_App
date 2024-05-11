import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//listening to clients which are wis to connect
//and respond a new thread to handle them
public class Server {
    private ServerSocket serverSocket;

    // Reference to ServerUI instance
    private ServerUI serverUI;

    public Server(ServerSocket serverSocket, ServerUI serverUI) {
        this.serverSocket = serverSocket;
        this.serverUI = serverUI;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                serverUI.appendToDisplay("A new client has connected!"); // Update UI
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
