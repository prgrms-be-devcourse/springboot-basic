package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.customer.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private enum CustomerQuery {
        INSERT("INSERT INTO customer(customer_id, name, created_at) VALUES(UNHEX(REPLACE(?, '-', '')), ?, ?)"),
        FIND_BY_ID("select * from customer where customer_id = UNHEX(REPLACE(?, '-', ''))"),
        FIND_ALL("select * from customer"),
        FIND_BY_NAME("select * from customer where name = ?"),
        UPDATE("update customer set name = ? where customer_id = UNHEX(REPLACE(?, '-', ''))"),
        UPDATE_BY_NAME("update customer set name = ? where name = ?"),
        DELETE("delete from customer where customer_id = UNHEX(REPLACE(?, '-', ''))"),
        DELETE_BY_NAME("delete from customer where name = ?");

        private final String query;

        CustomerQuery(String query) {
            this.query = query;
        }
    }

    private enum CustomerColumn {
        ID("customer_id"),
        NAME("name"),
        CREATED_AT("created_at");

        private final String column;

        CustomerColumn(String column) {
            this.column = column;
        }
    }

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> {
        String name = resultSet.getString(CustomerColumn.NAME.column);
        UUID customerId = toUUID(resultSet.getBytes(CustomerColumn.ID.column));
        LocalDateTime createAt = resultSet.getTimestamp(CustomerColumn.CREATED_AT.column).toLocalDateTime();
        return new Customer(customerId, createAt, name);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public void save(Customer customer) {
        jdbcTemplate.update(CustomerQuery.INSERT.query,
                customer.getId().toString(),
                customer.getName(),
                Timestamp.valueOf(customer.getCreatedAt()));
    }

    @Override
    public void update(Customer customer) {
        int update = jdbcTemplate.update(CustomerQuery.UPDATE.query,
                customer.getName(),
                customer.getId().toString());
        if (update == 0) {
            throw new DataModifyingException(
                    "Nothing was updated. query: " + CustomerQuery.UPDATE.query + " params: " + customer.getName() + ", " + customer.getId());
        }
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(CustomerQuery.DELETE.query,
                id.toString());
        if (update == 0) {
            throw new DataModifyingException("Nothing was deleted. query: " + CustomerQuery.DELETE.query + " params: " + id);
        }
    }

    @Override
    public void delete(String name) {
        int update = jdbcTemplate.update(CustomerQuery.DELETE_BY_NAME.query, name);
        if (update == 0) {
            throw new DataModifyingException(
                    "Nothing was deleted. query: " + CustomerQuery.DELETE_BY_NAME.query + " params: " + name);
        }
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                CustomerQuery.FIND_BY_ID.query,
                ROW_MAPPER,
                id.toString()));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                CustomerQuery.FIND_BY_NAME.query,
                ROW_MAPPER,
                name));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(CustomerQuery.FIND_ALL.query, ROW_MAPPER);
    }
}