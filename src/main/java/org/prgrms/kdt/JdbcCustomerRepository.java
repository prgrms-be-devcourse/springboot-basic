package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    private final String SELECT_BY_NAME_SQL = "select * from customers where name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "delete from customers";

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(SELECT_ALL_SQL);
            var resultSet = statement.executeQuery();
        )
        {
            while (resultSet.next()) {
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var customerName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {} name -> {} createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> ids = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(SELECT_ALL_SQL);
            var resultSet = statement.executeQuery();
        )
        {
            while (resultSet.next()) {
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                ids.add(customerId);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return ids;
    }

    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        )
        {
            statement.setString(1, name);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var customerName = resultSet.getString("name");
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {} name -> {} createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return names;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(INSERT_SQL);
        )
        {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return 0;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public int updateCustomerName(UUID customerId, String newName) {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        )
        {
            statement.setString(1, newName);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return 0;
    }

    public int deleteAllCustomer() {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            var statement = connection.prepareStatement(DELETE_ALL_SQL);
        )
        {
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

        return 0;
    }

    public static void main(String[] args) {
        var customerRepository = new JdbcCustomerRepository();
        customerRepository.deleteAllCustomer();

        var customerId = UUID.randomUUID();
        customerRepository.insertCustomer(customerId, "new-user", "new-user@gmail.com");
        customerRepository.updateCustomerName(customerId, "new-name");
        var names = customerRepository.findAllNames();
        names.forEach(v -> logger.info("Found name {}", names));
    }
}

