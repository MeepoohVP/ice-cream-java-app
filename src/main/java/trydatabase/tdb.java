package trydatabase;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class tdb {
    public static void main(String[] args){
        String url = "jdbc:mysql://127.0.0.1:3306/icecream";
        String user = "root";
        String password = "4149055160Pp!#";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection connection = DriverManager.getConnection(url,user,password);
            System.out.println("Database connected");

            Statement statement = connection.createStatement();
            String sql = "select * FROM dborderdetail"; //เลือกมาสดง
            String sql1 = "update dborderdetail set ordercode = '1' where menu = 'rocky road'"; //อัปเด่าเข้าไป
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println(resultSet.getString(1) +" "+ resultSet.getString(2));
            }
            statement.executeUpdate(sql1);//อัปเด่าเข้าไปใน sql
        }catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
