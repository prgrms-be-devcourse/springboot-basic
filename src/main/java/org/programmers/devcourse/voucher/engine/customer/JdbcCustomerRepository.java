package org.programmers.devcourse.voucher.engine.customer;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.configuration.JdbcProperties;
import org.programmers.devcourse.voucher.engine.exception.ExceptionFormatter;
import org.programmers.devcourse.voucher.util.UUIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository {

  private static final String SELECT_BY_ID =
      "SELECT customer_id, name, email, last_login_at, created_at FROM customers WHERE customer_id = ?";
  private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
  private final JdbcProperties databaseProperties;

  public JdbcCustomerRepository(
      JdbcProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }


  public Optional<Customer> getById(UUID customerId) {

    try (
        var connection = createConnection();
        var statement = connection.prepareStatement(SELECT_BY_ID)) {
      statement.setBytes(1, UUIDMapper.toBytes(customerId));
      try (var resultSet = statement.executeQuery()) {
        return Customer.from(resultSet);
      }


    } catch (SQLException | IOException throwable) {
      logger.error(ExceptionFormatter.formatExceptionForLogger(throwable));
    }

    return Optional.empty();
  }

  public Connection createConnection() throws SQLException {

    return DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUser(),
        databaseProperties.getPassword());

  }


}
