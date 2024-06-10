package repository;

import domain.Customer;

import java.sql.*;
import java.util.*;

public class DbCustomerRepository implements CustomerRepository {
    private String url;
    private String user;
    private String password;
    private static int nextId = 0;
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
    public DbCustomerRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
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
            System.err.println("Couldn't insert new customer" + e.getMessage());
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
                return new Customer(rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("couldn't find customer " + CustomerId);
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
                customers.add(new Customer(rs.getString("customerid")));
            }
        } catch (SQLException e) {
            System.err.println("couldn't get all customers" + e.getMessage());
        }
        return customers;
    }

    @Override
    public void removeCustomer(String q) {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBcustomer` WHERE `customerid`='" + q + "';";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove customer from database" + e.getMessage());
        }
    }

    @Override
    public void clearCustomers() {
        try (Connection connect = getConnection()){
            String sql = "DELETE FROM `DBcustomer`;";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql);
            statement1.execute(" SET sql_safe_updates = 1;");
        }catch (SQLException e){
            System.err.println("couldn't remove customer from database" + e.getMessage());
        }
    }
}