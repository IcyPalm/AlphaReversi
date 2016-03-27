package src.alphareversi;

import src.GameModules.ReversiModule.ReversiModel;

public class Main {

    /**
     * Base function of the application.
     * @param args arguments provided on startup
     */
    public static void main(String[] args) {

        Connection connection = Connection.getInstance();

        connection.startConnection("127.0.0.1", 1337);

    }
}
