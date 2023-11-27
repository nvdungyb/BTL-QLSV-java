package manage.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    public static Connection connect() {
        try {
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo", "root", "2311");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

