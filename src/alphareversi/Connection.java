package alphareversi;

import alphareversi.commands.CommandDispatcher;
import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.SendCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by timmein on 24/03/16.
 * Singleton implementation of the connection class. Holds connection with server and messaging.
 */
public class Connection {
    private static Connection instance = null;
    public CommandDispatcher commandDispatcher;
    private boolean connected = false;
    private Socket comms;
    private BufferedReader input;
    private PrintWriter output;
    private Thread serverListenerThread;

    protected Connection() {
        this.commandDispatcher = new CommandDispatcher();
    }

    /**
     * Singleton Implementation.
     * @return Connection instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    /**
     * Function to open a connection with a server and start listening.
     * @param host String server address
     * @param port int port number
     * @return boolean did the server start successfully
     */
    public boolean startConnection(String host, int port) throws IOException {
        try {
            this.comms = new Socket(host, port);
            this.comms.setTcpNoDelay(true);

            this.input = new BufferedReader(
                    new InputStreamReader(comms.getInputStream()));
            this.output = new PrintWriter(comms.getOutputStream(), true);
            this.connected = true;
            startServerResponseThread();
        } catch (IOException exception) {
            this.connected = false;
            throw exception;
        }
        return this.connected;
    }

    /**
     * Start listening to the server. And handle incoming commands via the dispatcher
     */
    public void startServerResponseThread() {
        Runnable runnable = () -> {

            while (true) {
                try {
                    RecvCommand command = CommandParser.parseString(input.readLine());
                    this.commandDispatcher.sendCommand(command);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        };

        // create the thread
        this.serverListenerThread = new Thread(runnable);
        // start the thread
        this.serverListenerThread.start();
    }

    /**
     * Sends a command object to the server.
     * @param command SendCommand send command to server
     */
    public void sendMessage(SendCommand command) {
        output.println(command.toString());
    }

    /**
     * return the state of the serverconnection
     * @return state of the connection
     */
    public boolean getConnected() {
        return this.connected;
    }

}
