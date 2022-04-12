package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.events.StartElement;
import java.nio.ByteBuffer;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    //insert 만들기
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";

    //특정 이름
    public List<String> findNames(String name){
        List<String> names = new ArrayList<>();
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            logger.info("check -> {}", statement);
            try (var resultSet = statement.executeQuery()
            ){
                while (resultSet.next()){
                    var customerName = resultSet.getString("name"); //name collum value call
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer name -> {}, customer id -> {}, created_at -> {} ", customerName, customerId, createdAt);
                    names.add(customerName);
                }
            }
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    //전체 이름
    public List<String> findAllNames(){
        List<String> names = new ArrayList<>();
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                var customerName = resultSet.getString("name"); //name collum value call
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer name -> {}, customer id -> {}, created_at -> {} ", customerName, customerId, createdAt);
                names.add(customerName);
            }
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    //전체 ID
    public List<UUID> findAllIds(){
        List<UUID> uuids = new ArrayList<>();
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                var customerName = resultSet.getString("name"); //name collum value call
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer name -> {}, customer id -> {}, created_at -> {} ", customerName, customerId, createdAt);
                uuids.add(customerId);
            }
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
        return uuids;
    }

    //입력 메서드
    public int insertCustomer(UUID customerId, String name, String email) {
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    //업데이트 메서드
    public int updateCustomerName(UUID customerId, String name) {
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setBytes(2, customerId.toString().getBytes());
            statement.setString(1, name);
            return statement.executeUpdate();
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    //삭제 메서드
    public void deleteAllCustomer() {
        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            statement.executeUpdate();
        }catch (SQLException throwables){
            logger.error("Got error while closing connection", throwables);
        }
    }

    static UUID toUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) throws SQLException {
        var customerRepository = new JdbcCustomerRepository();

        customerRepository.deleteAllCustomer();
        var customer = UUID.randomUUID();
        logger.info("creted cutomerId -> {}", customer);
        customerRepository.insertCustomer(customer, "new-user", "new-user@gmail.com");
        customerRepository.findAllIds().forEach(v -> logger.info("Found customer Id : {}", v));


    }
}
