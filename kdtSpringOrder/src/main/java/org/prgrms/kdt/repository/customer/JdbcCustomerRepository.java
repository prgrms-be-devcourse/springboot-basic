package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.RegularCustomer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM customers WHERE name = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";

    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL)) {
            statement.setString(1, name); //SELECT_SQL 의 ? 에 매칭되는 값 = name
            logger.info("statement -> {}", statement);
            //개별 row에 대한 cursor가 있어서 next로 하나씩 가져온다.
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer name -> {}, customerId -> {}, createdAt -> {}", customerName, customerId, createdAt);

                    names.add(customerName);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }

        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer name -> {}, customerId -> {}, createdAt -> {}", customerName, customerId, createdAt);

                names.add(customerName);
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }
        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer name -> {}, customerId -> {}, createdAt -> {}", customerName, customerId, createdAt);

                uuids.add(customerId);
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            logger.info("statement -> {}", statement);
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }
        return 0; //0을 에러값으로 처리하는게 관례? 라고 생각하면되나..
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            logger.info("statement -> {}", statement);
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }
        return 0;
    }

    //삭제한 row count 반환
    public int deleteAllCustomer() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)) {
            logger.info("statement -> {}", statement);
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throwable.printStackTrace();
        }
        return 0;
    }

    static UUID toUUID(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    ////이름을 업데이트 한다 + 메일을 업데이트 한다
    public void transactionTest(Customer customer){
        String updateNameSql = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
        String updateEmailSql = "UPDATE customers SET email = ? WHERE customer_id = UUID_TO_BIN(?)";

        Connection connection = null;
        try { connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            connection.setAutoCommit(false);
            try (PreparedStatement updateNameStatement = connection.prepareStatement(updateNameSql);
                 PreparedStatement updateEmailStatement = connection.prepareStatement(updateEmailSql);) {

                updateNameStatement.setString(1, customer.getName());
                updateNameStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1, customer.getEmail());
                updateEmailStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            if(connection != null) {
                try{
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

    public static void main(String[] args) {

        JdbcCustomerRepository jdbcCustomerRepository =new JdbcCustomerRepository();

        jdbcCustomerRepository.transactionTest(new RegularCustomer(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), "user1", "user2@gmail.com", LocalDateTime.now()));

//        logger.info("created customerId -> {}", customerId);
//        jdbcCustomerRepository.insertCustomer(UUID.randomUUID(), "user2", "user2@gmail.com");
//        jdbcCustomerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {}", v));
    }
}