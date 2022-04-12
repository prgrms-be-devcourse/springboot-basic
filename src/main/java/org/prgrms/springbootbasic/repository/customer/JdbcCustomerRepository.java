package org.prgrms.springbootbasic.repository.customer;

import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values(UUID_TO_BIN(?), ?, ?)";

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(SELECT_ALL_SQL);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                customers.add(new Customer(customerId, name, email));
            }
        } catch (SQLException throwables) {
            logger.error("error in findAll()", throwables);
        }
        return customers;
    }

    public UUID save(Customer customer) {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("error in save()", throwables);
        }
        return customer.getCustomerId();
    }

}
