package org.prgms.customer.repository;

import org.prgms.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    private final static Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final static String DB_URL = "jdbc:mysql://database-2.ctzh5yr25fim.ap-northeast-2.rds.amazonaws.com:3306/ordermgmt";
    private final static String SELECT_ALL_QUERY = "SELECT * FROM customers;";
    private final static String SELECT_BY_NAME_QUERY = "SELECT * FROM customers WHERE name = ?;";
    private final static String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) values (UUID_TO_BIN(?), ?, ?)";
    private final static String UPDATE_NAME_BY_ID_QUERY = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?);";
    private final static String DELETE_QUERY = "DELETE FROM customers;";
    private final static String DELETE_QUERY_BY_ID = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?);";

    public List<Customer> findAll() {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(SELECT_ALL_QUERY);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            return parseResultToCustomer(resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    // test용
    private void findAllElement() {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(SELECT_ALL_QUERY);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                var byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
                var customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info(
                        "customer id : {}, name : {}, email : {}  createdAt : {}",
                        customerId, name, email, createdAt);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public List<Customer> findByName(String name) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(SELECT_BY_NAME_QUERY)
        ) {
            stmt.setString(1, name);
            return parseResultToCustomer(stmt.executeQuery());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public int insertCustomer(Customer customer) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(INSERT_QUERY)
        ) {
            stmt.setString(1, customer.customerId().toString());
            stmt.setString(2, customer.name());
            stmt.setString(3, customer.email());
            return stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public int deleteAll() {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(DELETE_QUERY)
        ) {
            return stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    public void deleteById(UUID customerId) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(DELETE_QUERY_BY_ID)
        ) {
            stmt.setString(1, customerId.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void updateCustomer(Customer targetCustomer) {
        try (
                Connection conn = DriverManager.getConnection(DB_URL, "root", "12345678");
                var stmt = conn.prepareStatement(UPDATE_NAME_BY_ID_QUERY)
        ) {
            stmt.setString(1, targetCustomer.name());
            stmt.setString(2, targetCustomer.customerId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private List<Customer> parseResultToCustomer(ResultSet resultSet) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            var byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
            var customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
            var name = resultSet.getString("name");
            var email = resultSet.getString("email");
            customers.add(new Customer(customerId, name, email));
        }
        resultSet.close();
        return customers;
    }

}
