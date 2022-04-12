package org.programmers.devcourse.voucher.engine.customer;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import org.programmers.devcourse.voucher.util.UUIDMapper;


@ToString
public class Customer {

  @Getter
  private final UUID customerId;
  @Getter
  private final String name;
  @Getter
  private final String email;
  private final LocalDateTime lastLoginAt;
  @Getter
  private final LocalDateTime createdAt;


  public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt,
      LocalDateTime createdAt) {
    this.customerId = customerId;
    this.name = name;
    this.email = email;
    this.lastLoginAt = lastLoginAt;
    this.createdAt = createdAt;
  }

  public static Optional<Customer> from(ResultSet resultSet) throws SQLException, IOException {
    if (!resultSet.next()) {
      return Optional.empty();
    }
    UUID customerId = UUIDMapper.fromBytes(resultSet.getBinaryStream("customer_id").readAllBytes());
    var name = resultSet.getString("name");
    var email = resultSet.getString("email");
    var lastLoginAt = resultSet.getTimestamp("last_login_at") == null ? null
        : resultSet.getTimestamp("last_login_at").toLocalDateTime();
    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

    return Optional.of(new Customer(customerId, name, email, lastLoginAt, createdAt));
  }

  public Optional<LocalDateTime> getLastLoginAt() {
    return Optional.ofNullable(lastLoginAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return customerId.equals(customer.customerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId);
  }
}
