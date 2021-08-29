package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        logger.info("customer id -> {}, name  -> {}, createAt -> {}", customerId, customerName, createdAt);
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };;

    public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt())
        );
        if(update != 1){
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
//        try(
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?), ?, ?, ?)");
//        )
//        {
//            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
//            statement.setString(2, customer.getName());
//            statement.setString(3, customer.getEmail());
//            statement.setTimestamp(4, Timestamp.valueOf(customer.getCreatedAt()));
//            var executeUpdate = statement.executeUpdate();
//            if(executeUpdate != 1){
//                throw new RuntimeException("Noting was inserted");
//            }
//            return customer;
//        }catch (SQLException e){
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("update customers set name = ?, email = ?, last_login_at = ? where customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
                customer.getCustomerId().toString().getBytes()
        );
        if(update != 1){
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
//        try(
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("update customers set name = ?, email = ?, last_login_at = ? where customer_id = UUID_TO_BIN(?)");
//        )
//        {
//            statement.setString(1, customer.getName());
//            statement.setString(2, customer.getEmail());
//            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
//            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
//            var executeUpdate = statement.executeUpdate();
//            if(executeUpdate != 1){
//                throw new RuntimeException("Noting was updated");
//            }
//            return customer;
//        }catch (SQLException e){
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public int count(){
        return jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
//        List<Customer> allCustomers = new ArrayList<>();
//        try (
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("select * from customers");
//                var resultSet = statement.executeQuery();
//        ){
//            while(resultSet.next()){
//                mapToCustomer(allCustomers, resultSet);
//            }
//        } catch (SQLException e) {
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
//        return allCustomers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(?)", customerRowMapper, customerId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }

//        List<Customer> allCustomers = new ArrayList<>();
//
//        try (
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("select * from customers where customer_id = UUID_TO_BIN(?)");
//
//        ){
//            statement.setBytes(1, customerId.toString().getBytes());
//            try(var resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    mapToCustomer(allCustomers, resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
//        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where name = ?", customerRowMapper, name));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }

//        List<Customer> allCustomers = new ArrayList<>();
//        try (
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("select * from customers where name = ?");
//
//        ){
//            statement.setString(1, name);
//            try(var resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    mapToCustomer(allCustomers, resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
//        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email = ?", customerRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }

//        List<Customer> allCustomers = new ArrayList<>();
//
//        try (
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("select * from customers where email = ?");
//
//        ){
//            statement.setString(1, email);
//            try(var resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    mapToCustomer(allCustomers, resultSet);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error("Got error while closing connection", e);
//            throw new RuntimeException(e);
//        }
//        return allCustomers.stream().findFirst();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
//        try(
//                var connection = dataSource.getConnection();
//                var statement = connection.prepareStatement("delete from customers");
//        )
//        {
//            statement.executeUpdate();
//        }catch (SQLException e){
//            logger.error("Got error while closing connection", e);
//        }
    }

//    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
//        var customerName = resultSet.getString("name");
//        var email = resultSet.getString("email");
//        var customerId = toUUID(resultSet.getBytes("customer_id"));
//        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
//                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
//        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//        logger.info("customer id -> {}, name  -> {}, createAt -> {}", customerId, customerName, createdAt);
//        allCustomers.add(new Customer(customerId, customerName, email, lastLoginAt, createdAt));
//    }

    static UUID toUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
