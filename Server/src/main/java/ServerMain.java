import java.io.IOException;
import java.util.ResourceBundle;

public class ServerMain {
    public static void main(String[] args) throws IOException {
//      int port = Integer.parseInt(args[0]);
//      if (args.length > 1) {
//          System.out.println("Usage: java -jar Server-1.0.jar [port]");
//          System.exit(0);
//      }
//        ResourceBundle.getBundle("config").getString("serverAddress");
//        ResourceBundle.getBundle("config").getString("serverPort");
        Server server = new Server(9000);
        server.run();
    }
}