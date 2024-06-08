package repository;

import domain.Order;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DbOrderRepository implements OrderRepository {
    private static int nextCode = 0;
    private Map<String, Order> repo;

    private void readFromDb(){
        repo = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = ConnectDb.getConnection();
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "select * FROM dborder";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String ownerId = rs.getString(1);
                    String code = rs.getString(2);
                    String status = rs.getString(3);
                    Order order = new Order(code, ownerId, status);
                    repo.put(code, order);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void writeToDb(){
        try (Connection connect = ConnectDb.getConnection()){
            String sql = "INSERT INTO dborder (code, ownerid, status) VALUES (?, ?, ?)";
            repo.values().forEach(s ->
            {
                try (PreparedStatement pstmt = connect.prepareStatement(sql)){
                    pstmt.setString(1, s.getCode());
                    pstmt.setString(2, s.getOwnerId());
                    pstmt.setString(3, s.getStatus());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public Order addOrder(String ownerId) {
        String OrderCode = "" + ++nextCode;
        Order c = new Order(OrderCode, ownerId, "wait payment");
        if (repo.putIfAbsent(OrderCode, c) == null) {
            writeToDb();
            return c;
        }
        return null;
    }

    @Override
    public Order findOrder(String orderCode) {
        return repo.get(orderCode);
    }

    @Override
    public Order updateOrder(Order order) {
        try {
            repo.replace(order.getCode(), order);
            writeToDb();
        } catch (Exception e) {
            return null;
        }
        return order;
    }

    @Override
    public Collection<Order> allOrder() {
        return repo.values();
    }
}