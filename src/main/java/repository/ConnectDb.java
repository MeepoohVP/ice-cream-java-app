package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
    private static String url = "jdbc:mysql://localhost:3306/icecream";
    private static String user = "root";
    private static String password = "4149055160Pp!#";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}
