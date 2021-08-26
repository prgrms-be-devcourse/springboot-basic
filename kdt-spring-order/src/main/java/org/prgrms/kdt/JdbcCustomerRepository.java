package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";

    public List<String> findNames(String name){
        List<String> names = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);// 1-> ?의 순서
            logger.info("statement -> {}", statement);// ''안으로 몽땅 들어가게 된다 즉, or 절이 발동 하지 않는다.
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    //logger.info("customer id-> {}, customer name-> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return names;
    }

    public List<String> findAllNames(){
        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                //logger.info("customer id-> {}, customer name-> {}, createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return names;
    }

    public List<UUID> findAllIds(){
        List<UUID> uuids = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");

                // UUID version이 다름 즉 bytes가 달라서 잘라서 써야함
                //var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                //logger.info("customer id-> {}, customer name-> {}, createdAt -> {}", customerId, customerName, createdAt);
                uuids.add(customerId);
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return uuids;
    }


    public int insertCustomer(UUID customerId, String name, String email){

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0; // 문제발생시 0 반환
    }

    public int deleteAllCustomers(){

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0; // 문제발생시 0 반환
    }

    public int updateCustomerName (UUID customerId, String name){
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0; // 문제발생시 0 반환
    }



    static UUID toUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) throws SQLException {
        var customerRepository = new JdbcCustomerRepository();
        var count = customerRepository.deleteAllCustomers();
        logger.info("deleted count -> {}",count);

        customerRepository.insertCustomer(UUID.randomUUID(), "new-user", "new-user@gmail.com");
        var customer2 = UUID.randomUUID();
        customerRepository.insertCustomer(customer2, "new-user2", "new-user2@gmail.com");
        customerRepository.findAllNames().forEach(v -> logger.info("Found name : {}", v));

        customerRepository.updateCustomerName(customer2, "updated-user2");
        customerRepository.findAllNames().forEach(v -> logger.info("Found name : {}", v));


        var customerId = UUID.randomUUID();
        logger.info("created customerId -> {}",customerId);
        customerRepository.insertCustomer(customerId, "new-user3", "new-user3@gmail.com");
        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {} and version : {}", v,v.version()));
        // customerId와 들어간 uuid가 다른 녀석이 나온다!!! 왜지??
        // 실제로 DB에는 맞게 들어갔는데 조회가 이상하다!
        logger.info("created UUID Version -> {}",customerId.version());
        // UUID.nameUUIDFromBytes(resultSet.getBytes(customer_id")); 에서 UUID버전은 3으로 DB의 버전 4와 다른 것이였다!

    }
}
