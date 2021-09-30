import modul.DBEngine;

public class Application {
    public static void main(String[] args) {
        System.out.println("run");
        DBEngine dbEngine = new DBEngine();
        System.out.println(dbEngine.isConnected());

    }
}
