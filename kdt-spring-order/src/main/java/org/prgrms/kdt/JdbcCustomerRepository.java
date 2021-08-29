package org.prgrms.kdt;

import org.prgrms.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name = ?";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values (UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";


    public List<String> findAllName(){
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ){
            while(resultSet.next()){
                var customerName = resultSet.getString("name");
                var customer_id = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name  -> {}, createAt -> {}", customer_id, customerName, createdAt);
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
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ){
            while(resultSet.next()){
                var customerName = resultSet.getString("name");
                var customer_id = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name  -> {}, createAt -> {}", customer_id, customerName, createdAt);
                uuids.add(customer_id);
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email){

        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(INSERT_SQL);
                )
        {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        }catch (SQLException e){
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name){

        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        )
        {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        }catch (SQLException e){
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int deleteAllCustomers(){

        try(
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        )
        {
            return statement.executeUpdate();
        }catch (SQLException e){
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    public List<String> findNames(String name) {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        var SELECT_SQL = String.format("select * from customers WHERE name = '%s'", name);
        List<String> names = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
//            var statement = connection.createStatement();
            var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
//            var resultSet = statement.executeQuery();
        ){
            statement.setString(1, name);
            try (var resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    var customerName = resultSet.getString("name");
                    var customer_id = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name  -> {}, createAt -> {}", customer_id, customerName, createdAt);
                    names.add(customerName);
                }
            }

        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
//        finally {
//            try{
//                if(connection != null) connection.close();
//                if(statement != null) statement.close();
//                if(resultSet != null) resultSet.close();
//            }catch(SQLException e){
//                logger.error("Got error while closing connection", e);
//            }
//        }
        return names;
    }

    public void transactionTest(Customer customer){
        String updateNameSql = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";
        String updateEmailSql = "update customers set email = ? where customer_id = UUID_TO_BIN(?)";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root1234!");
            connection.setAutoCommit(false);
            try(
                    var updateNameStatement = connection.prepareStatement(updateNameSql);
                    var updateEmailStatement = connection.prepareStatement(updateEmailSql);
            ) {
                updateNameStatement.setString(1, customer.getName());
                updateNameStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1, customer.getEmail());
                updateEmailStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            }
        }catch (SQLException e){
            if(connection != null){
                try{
                    connection.rollback();
                    connection.close();
                }catch (SQLException exception){
                    logger.error("Got error while closing connection", exception);
                    throw new RuntimeException(exception);
                }
            }
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
    }

    static UUID toUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args){
//        var customerRepository = new JdbcCustomerRepository();
//        customerRepository.deleteAllCustomers();
//        var customer2 = UUID.randomUUID();
//        customerRepository.insertCustomer(customer2, "new-user", "new-user@gamil.com");
//        customerRepository.insertCustomer(UUID.randomUUID(), "new-user2", "new-user2@gamil.com");
//        logger.info("created customerId -> {}", customer2);
//        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {}", v));
//        List<String> names = customerRepository.findAllName();
//        names.forEach(v -> logger.info("Found name : {}", v));
//        customerRepository.updateCustomerName(customer2, "users");
//        names = customerRepository.findAllName();
//        names.forEach(v -> logger.info("Found name : {}", v));
    }
}
