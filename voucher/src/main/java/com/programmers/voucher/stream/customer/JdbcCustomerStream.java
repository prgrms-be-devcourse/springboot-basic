package com.programmers.voucher.stream.customer;

import com.programmers.voucher.domain.Customer;
import com.programmers.voucher.domain.QueryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile(value = "jdbc")
public class JdbcCustomerStream implements CustomerStream {

    @Value(value = "${mysql.user-id}")
    private String MYSQL_USERID;
    @Value("${mysql.password}")
    private String MYSQL_PASSWORD;
    @Override
    public List<Customer> findAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", "root", "1234");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", MYSQL_USERID, MYSQL_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(QueryType.SELECT_ALL.stringQuery);
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
                UUID customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Customer customer = new Customer(customerId, name, email);
                customers.add(customer);
            }

        } catch (SQLException e) {
            throw e;
        }
        return customers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) throws SQLException {

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer","root", "1234");
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", MYSQL_USERID, MYSQL_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(QueryType.SELECT_BY_ID.stringQuery);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                return Optional.of(new Customer(customerId, name, email));
            }

        } catch (SQLException e) {
            throw e;
        }
        return Optional.empty();
    }

    @Override
    public UUID save(Customer customer) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer","root", "1234");
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", MYSQL_USERID, MYSQL_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(QueryType.SAVE.stringQuery);
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return customer.getCustomerId();
    }

    @Override
    public UUID update(UUID customerId, String name) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", "root", "1234");
//                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", MYSQL_USERID, MYSQL_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(QueryType.UPDATE.stringQuery);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return customerId;
    }

    @Override
    public void deleteById(UUID customerId) throws SQLException {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/customer", "root", "1234");
                var statement = connection.prepareStatement(QueryType.DELETE.stringQuery);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }


    public static void main(String[] args) throws SQLException {
        JdbcCustomerStream jdbcCustomerStream = new JdbcCustomerStream();

        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Tommy", "tommy0419@gmail.com");
        // 생성
        UUID saveId = jdbcCustomerStream.save(customer);

        // 조회
        Customer findCustomer = jdbcCustomerStream.findById(customerId).get();

        // 업데이트
        UUID updateId = jdbcCustomerStream.update(customerId, "new Tommy");

        // 조회
        Customer editedCustomer = jdbcCustomerStream.findById(customerId).get();
        System.out.println(customerId == saveId);
        System.out.println("Before name : " + findCustomer.getName());
        System.out.println("After name : " + editedCustomer.getName());

        // 삭제
        jdbcCustomerStream.deleteById(customerId);

    }
}
