package repository;

import domain.Customer;
import domain.Order;

import java.sql.*;
import java.util.*;

public class DbOrderRepository implements OrderRepository {
    private String url;
    private String user;
    private String password;
    private static int nextCode = 0;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        }catch (SQLException e){
            System.err.println("Incorrect information.");
        }catch (ClassNotFoundException e){
            System.err.println("Couldn't find JDBC driver");
        }
        return null;
    }
    public DbOrderRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        String sql = "CREATE TABLE IF NOT EXISTS DBorder (" +
                "code INT PRIMARY KEY AUTO_INCREMENT, " +
                "ownerid VARCHAR(20) NOT NULL, " +
                "status VARCHAR(20) NOT NULL)";
        String getMaxCodeSql = "SELECT MAX(code) FROM DBorder";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            ResultSet rs = statement.executeQuery(getMaxCodeSql);
            if (rs.next()) {
                nextCode = rs.getInt(1);
            }
        }catch (SQLException e) {
            System.err.println("Cannot create table " + e.getMessage());
        }
    }
    @Override
    public Order addOrder(String ownerId) {
        String orderCode = "" + ++nextCode;
        Order o = new Order(orderCode,ownerId,"wait payment");
        String insertSql = "INSERT INTO DBOrder (code,ownerid,status) VALUES (?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setInt(1, nextCode);
            pstmt.setString(2, o.getOwnerId());
            pstmt.setString(3, o.getStatus());
            pstmt.executeUpdate();
            return o;
        } catch (SQLException e) {
            System.err.println("couldn't insert order" + e.getMessage());
        }
        return null;
    }

    @Override
    public Order findOrder(String orderCode) {
        String selectSql = "SELECT * FROM DBOrder WHERE code = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setInt(1, Integer.parseInt(orderCode));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Order(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("couldn't find order"+e.getMessage());
        }
        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        String updateSql = "UPDATE DBorder SET ownerid = ?, status = ? WHERE code = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, order.getOwnerId());
            pstmt.setString(2, order.getStatus());
            pstmt.setInt(3, Integer.parseInt(order.getCode()));
            pstmt.executeUpdate();
            return order;
        } catch (SQLException e) {
            System.err.println("couldn't update order"+e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Order> allOrder() {
        List<Order> orders = new ArrayList<>();
        String selectAllSql = "SELECT * FROM DBOrder";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllSql)) {

            while (rs.next()) {
                orders.add(new Order(rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println("couldn't get all orders"+e.getMessage());
        }
        return orders;
    }

    @Override
    public void removeOrder(String odCode) {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBorder` WHERE `code`=" + Integer.parseInt(odCode) + ";";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove order from database" + e.getMessage());
        }
    }

    @Override
    public void clearOrder() {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBorder`;";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove order from database" + e.getMessage());
        }
    }
}