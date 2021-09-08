package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "DELETE from customers";

    public List<String> findNames(String name){
        List<String> names = new ArrayList<>();

        try (
                // AutoCloseable을 구현하고 있어서 자동으로 호출 해주기 때문에 Try catch를 통해 close를 하지 않아도 된다(Java 10이상)
                var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);

        ){
            // Prepared Statement를 사용하기 위해 try catch로 잡아줘야하고 set을 통해 ? 로부터 인덱스 1 순서로 값을 매핑 해줘야 한다
            statement.setString(1, name);
            try (var resultSet = statement.executeQuery()) {
                // 개별 커서가 있어서 next로 하나 하나 가져와야함
                while(resultSet.next()) {
                    var customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    // 현재는 not null이지만 null 여부 확인 하지 않은채로 null을 가져오게되면 NullPointException 발생하니 주의
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            logger.error("Got an error while closing connection", throwables);

        }
        return names;
    }

    public List<String> findAllName(){
        List<String> names = new ArrayList<>();

        try (
                // AutoCloseable을 구현하고 있어서 자동으로 호출 해주기 때문에 Try catch를 통해 close를 하지 않아도 된다(Java 10이상)
                var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ){
            // 개별 커서가 있어서 next로 하나 하나 가져와야함
            while(resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                // 현재는 not null이지만 null 여부 확인 하지 않은채로 null을 가져오게되면 NullPointException 발생하니 주의
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerName);
                }
        } catch (SQLException throwables) {
            logger.error("Got an error while closing connection", throwables);

        }
        return names;
    }

    public List<UUID> findAllIds(){
        List<UUID> names = new ArrayList<>();

        try (
                // AutoCloseable을 구현하고 있어서 자동으로 호출 해주기 때문에 Try catch를 통해 close를 하지 않아도 된다(Java 10이상)
                var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ){
            // 개별 커서가 있어서 next로 하나 하나 가져와야함
            while(resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                // 현재는 not null이지만 null 여부 확인 하지 않은채로 null을 가져오게되면 NullPointException 발생하니 주의
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerId);
            }
        } catch (SQLException throwables) {
            logger.error("Got an error while closing connection", throwables);

        }
        return names;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
            var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            var statement = connection.prepareStatement(INSERT_SQL);

        ){
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got an error while closing connection", throwables);
        }

        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);

        ){
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got an error while closing connection", throwables);
        }

        return 0;
    }

    public int deleteAllCustomers() {
        try (
          var connection = DriverManager.getConnection("Jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
          var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ){
          return statement.executeUpdate();
        } catch (SQLException throwables) {
          logger.error("Got an error while closing connection", throwables);
        }
        return 0;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong()); // 128비트를 두번을 통해 64비트씩

    }


    public static void main(String[] args) {
        var customerRepository = new JdbcCustomerRepository();
        var count = customerRepository.deleteAllCustomers();
        logger.info("deleted count -> {} ", count);

        var customerId = UUID.randomUUID();
        logger.info("created customerId -> {}", customerId);
        logger.info("created UUID Version -> {}", customerId.version());
        customerRepository.insertCustomer(customerId, "new-user", "new-user@gmail.com");
        customerRepository.findAllIds().forEach(v -> logger.info("Founded customerId : {} and version : {}", v, v.version()));




    }
}
