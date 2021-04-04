import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Erick Lage e Gabriel Zilmar
 * @version 04/04/2021
 */

/**
 * Classe Cliente
 */
class Client {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Metodo que inicia o cliente
     * 
     * @throws UnknownHostException
     * @throws IOException
     */
    public void start() throws UnknownHostException, IOException {
        Socket client = new Socket(this.host, this.port);
        System.out.println("Client connected!");

        ReceiveMessage rm = new ReceiveMessage(client.getInputStream());
        new Thread(rm).start();
    }
}

/**
 * Classe ReceiveMessage, recebe as mensagens
 */
class ReceiveMessage implements Runnable {
    private InputStream server;

    public ReceiveMessage(InputStream server) {
        this.server = server;
    }

    /**
     * Metodo run, executado para mostrar as mensagens recebidas.
     */
    public void run() {
        Scanner scan = new Scanner(this.server);
        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
        scan.close();
    }
}

/**
 * Classe Principal User
 */
public class User {
    /**
     * Metodo main, le a porta para se conectar ao servidor
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int port = -1;
        while (port < 0 || port > 50000) {
            System.out.println("Server port:");
            port = scan.nextInt();
        }
        scan.close();

        String host = "127.0.0.1";
        System.out.println("The client will run on the localhost.");
        try {
            new Client(host, port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
