package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//spring 을 이용한 jdbc

@Repository
public class CustomerJdbcRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final DataSource dataSource;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> allCustomers = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                var statement = connection.prepareStatement("select * from customers");
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                String email = resultSet.getString("email");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var lastLoginAt = resultSet.getTimestamp("last_login_at")!=null?
                        resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                //logger.info("customer id-> {}, customer name-> {}, createdAt -> {}", customerId, customerName, createdAt);
                allCustomers.add(new Customer(customerId, customerName, email, createdAt));
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
        return allCustomers;
    }

    @Override
    public Optional<Customer> findById(UUID cutomerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(String name) {
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
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }
}
