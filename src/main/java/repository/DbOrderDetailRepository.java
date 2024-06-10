package repository;

import domain.OrderDetail;

import java.sql.*;
import java.util.*;

public class DbOrderDetailRepository implements OrderDetailRepository {
    private String url;
    private String user;
    private String password;
    private static Map<String, OrderDetail> orderDetails = new HashMap<>();
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
    public DbOrderDetailRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        String sql = "CREATE TABLE IF NOT EXISTS DBorderdetail (" +
                "orderCode VARCHAR(20) PRIMARY KEY, " +
                "menu VARCHAR(120), " +
                "price INT)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error while creating table" + e.getMessage());
        }
    }
    @Override
    public OrderDetail addOrderDetail(String orderCode) {
        OrderDetail orderDetail = new OrderDetail(orderCode);
        String insertSql = "INSERT INTO DBorderdetail (orderCode, menu, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            pstmt.setString(1, orderDetail.getOrderCode());
            pstmt.setString(2, orderDetail.getIceCream().toString());
            pstmt.setInt(3, (int) orderDetail.getTotal());
            pstmt.executeUpdate();
            return orderDetail;
        } catch (SQLException e) {
            System.err.println("Couldn't insert order detail" + e.getMessage());
        }
        return null;
    }
    @Override
    public OrderDetail findOrderDetail(String o){
        String selectSql = "SELECT * FROM DBorderdetail WHERE orderCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, o);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new OrderDetail(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("cannot find order detail" + e.getMessage());
        }
        return null;
    }
    @Override
    public Collection<OrderDetail> allOrderDetails(){
        List<OrderDetail> orderDetails = new ArrayList<>();
        String selectAllSql = "SELECT * FROM DBorderdetail";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllSql)) {

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail(rs.getString(1));
                orderDetail.addIceCream(rs.getString(2), rs.getInt(3));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            System.err.println("cannot find order details" + e.getMessage());
        }
        return orderDetails;
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        String updateSql = "UPDATE DBorderdetail SET menu = ?, price = ? WHERE orderCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, orderDetail.getIceCream().toString());
            pstmt.setInt(2,(int) orderDetail.getTotal());
            pstmt.setString(3, orderDetail.getOrderCode());
            pstmt.executeUpdate();
            return orderDetail;
        } catch (SQLException e) {
            System.err.println("cannot update order detail" + e.getMessage());
        }
        return null;
    }

    @Override
    public void removeOrderDetail(String odCode) {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBorderdetail` WHERE `ordercode`='" + odCode + "';";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove order detail from database" + e.getMessage());
        }
    }

    @Override
    public void clearOrderDetails() {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBorderdetail`;";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove order detail from database" + e.getMessage());
        }
    }
}
