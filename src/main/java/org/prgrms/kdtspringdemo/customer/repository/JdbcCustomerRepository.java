package org.prgrms.kdtspringdemo.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/kdt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
    }
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var isBlack = Boolean.parseBoolean(resultSet.getString("is_black"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        return new Customer(customerId, customerName, isBlack);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public JdbcCustomerRepository() {
        this.jdbcTemplate = new JdbcTemplate(dataSource());
    }

    @Override
    public Optional<List<Customer>> getAllBlackList() throws IOException {
        return Optional.of(jdbcTemplate.query("select * from customers where is_black = ?", customerRowMapper,true));
    }
}
