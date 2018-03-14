import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void run() {
        while(!serverSocket.isClosed()) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                if(serverSocket.isClosed()) {
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.submit(new Handler(clientSocket));
        }
        this.threadPool.shutdown();
    }
}