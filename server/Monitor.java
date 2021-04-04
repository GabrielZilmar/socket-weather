import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Erick Lage e Gabriel Zilmar
 * @version 04/04/2021
 */

/**
 * Classe server, responsavel pelo servidor
 */
class Server {
    private int port;
    private List<PrintStream> clients;
    private Weather weather;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<PrintStream>();
        this.weather = new Weather();
    }

    /**
     * Metodo que inicia o servidor e aceita novos clientes
     * 
     * @throws IOException
     */
    public void start() throws IOException {
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("Server open on port " + this.port);
        int flag = 0;
        while (true) {
            Socket client = server.accept();
            System.out.println("New connection with " + client.getInetAddress().getHostAddress());
            PrintStream ps = new PrintStream(client.getOutputStream());
            this.clients.add(ps);

            ClientHandler sm = new ClientHandler(client.getInputStream(), this, flag);
            new Thread(sm).start();
            flag = 1;
        }
    }

    /**
     * Metodo que envia mensagem para os clientes
     */
    public void sendMessage() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        for (PrintStream client : this.clients) {
            client.println("Hour: " + formatter.format(now) + "\nWeather: " + "\n\tTemperature: "
                    + this.weather.getTemperature() + "º\t" + this.weather.getStatus());
        }
        weather.changeWeather();
    }
}

/**
 * Classe ClienteHandler, faz a comunicação entre os clientes
 */
class ClientHandler implements Runnable {
    private Server server;
    private int flag;

    public ClientHandler(InputStream client, Server server, int flag) {
        this.server = server;
        this.flag = flag;
    }

    /**
     * Metodo run, e executado para chamar o envio de mensagem e enviar para todos
     * os clientes
     */
    public void run() {
        if (this.flag == 0) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    server.sendMessage();
                }
            }, 0, 5000);
        }

    }
}

/**
 * Classe Principal Monitor
 */
public class Monitor {
    /**
     * Metodo main, le a porta que o servidor ira rodar e o inicia
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int port = -1;
        while (port < 0 || port > 50000) {
            System.out.print("Port for server: ");
            port = scan.nextInt();
        }
        scan.close();

        try {
            new Server(port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
