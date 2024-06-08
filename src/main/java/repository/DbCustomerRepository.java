package repository;

import domain.Customer;
import ui.App;

import java.sql.*;
import java.util.*;

public class DbCustomerRepository implements CustomerRepository {
    private static String url = "jdbc:mysql://127.0.0.1:3306/icecream";
    private static String user = "root";
    private static String password = "4149055160Pp!#";
    private static int nextId = 0;
//    private static Map<String, Customer> repo = new HashMap<>();
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
    public DbCustomerRepository() {
        String sql = "CREATE TABLE IF NOT EXISTS DBcustomer (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "CustomerId VARCHAR(20) NOT NULL)";
        String getMaxIdSql = "SELECT MAX(id) FROM DBcustomer";
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            ResultSet rs = statement.executeQuery(getMaxIdSql);
            if (rs.next()) {
                nextId = rs.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException("cannot create table",e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Customer addCustomer() {
        String CustomerId = "C" + ++nextId;
        Customer c = new Customer(CustomerId);
        String insertSql = "INSERT INTO DBcustomer (CustomerId) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            pstmt.setString(1, c.getQueue());
            pstmt.executeUpdate();
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Customer findCustomer(String CustomerId) {
        String selectSql = "SELECT * FROM DBcustomer WHERE CustomerId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

            pstmt.setString(1, CustomerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("CustomerId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public Collection<Customer> allCustomers() {
        List<Customer> customers = new ArrayList<>();
        String selectAllSql = "SELECT * FROM DBcustomer";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllSql)) {

            while (rs.next()) {
                customers.add(new Customer(rs.getString("CustomerId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}