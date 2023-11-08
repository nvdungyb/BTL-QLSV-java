package Main;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    public static Connection connect() {
        try {
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "Dung3032003_135709");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

