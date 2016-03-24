package alphareversi;

import alphareversi.commands.CommandParser;
import com.sun.corba.se.spi.activation.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by timmein on 24/03/16.
 */
public class Connection {
    private static Connection instance = null;

    protected Connection() {
        // Exists only to defeat instantiation.
    }
    public static Connection getInstance() {
        if(instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    private boolean connected = false;
    private Socket comms;
    private BufferedReader input;
    private PrintWriter output;
    private Thread serverListenerThread;

    public boolean startConnection(String host,int port) {
        try {
            this.comms = new Socket(host, port);
            this.comms.setTcpNoDelay(true);

            this.input = new BufferedReader(
                    new InputStreamReader(comms.getInputStream()));
            this.output = new PrintWriter(comms.getOutputStream(), true);
            this.connected = true;
            startServerResponseThread();

        } catch (IOException e) {
            this.connected = false;
        }
        return this.connected;
    }

    public void startServerResponseThread(){
        Runnable runnable = () -> {

            while (true) {
                try {
                    //TODO checken of de input een response is van een request. anders doorsturen naar commanddispatcher.
                    CommandParser.parseString(input.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        // create the thread
        this.serverListenerThread = new Thread(runnable);
        // start the thread
        this.serverListenerThread.start();
    }

    //TODO zorgen dat commands verstuurd worden en onthouden voor een mogelijke return.
    public void sendMessage(String string){
        output.println(string);
    }

}
