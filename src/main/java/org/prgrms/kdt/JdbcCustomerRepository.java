package org.prgrms.kdt;

import org.prgrms.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name = ?";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "DELETE from customers";



    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while(resultSet.next()) {
                String customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerName);
            }
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();

        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while(resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                uuids.add(customerId);
            }
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }

        return uuids;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return 0;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        } catch(SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return 0;
    }

    public void transactionTest(Customer customer) {
        String updateNameSql = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
        String updateEmailSql = "UPDATE customers SET email = ? WHERE customer_id = UUID_TO_BIN(?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            connection.setAutoCommit(false);
            try (
                PreparedStatement updateNameStatement = connection.prepareStatement(updateNameSql);
                PreparedStatement updateEmailStatement = connection.prepareStatement(updateEmailSql);
            ) {
                updateNameStatement.setString(1 , customer.getName());
                updateNameStatement.setBytes(2 , customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1 , customer.getEmail());
                updateEmailStatement.setBytes(2 , customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            if(connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException throwable) {
                    logger.error("Got error while closing connection", throwable);
                    throw new RuntimeException(exception);
                }
            }
            logger.error("Got error while closing connection", exception);
            throw new RuntimeException(exception);
        }
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) {
        JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();

        customerRepository.transactionTest(new Customer(UUID.fromString("e7192567-44a3-4c97-888b-7bf3da54096e"), "update-user", "new-user2@gmail.com", LocalDateTime.now()));


//        int count = customerRepository.deleteAllCustomers();
//        logger.info("deleted count -> {}", count);
//
        UUID customerId = UUID.randomUUID();
//        logger.info("created customerId -> {}", customerId);
//        logger.info("created UUID Version -> {}", customerId.version());
//        customerRepository.insertCustomer(customerId, "new-user2", "new-user2@gmail.com");
//        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {} and version : {}", v, v.version()));

    }
}
