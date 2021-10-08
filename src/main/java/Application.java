import module.DBConnector;

public class Application {
    public static void main(String[] args) {
        System.out.println("run");
        DBConnector dbConnector = new DBConnector();
        System.out.println(dbConnector.isConnected());

    }
}
