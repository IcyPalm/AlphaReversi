package alphareversi;

public class Main {
    public static void main(String[] args) {

        Connection connection = Connection.getInstance();

        connection.startConnection("127.0.0.1",1337);

        connection.sendMessage("dit is een test string");

    }
}
