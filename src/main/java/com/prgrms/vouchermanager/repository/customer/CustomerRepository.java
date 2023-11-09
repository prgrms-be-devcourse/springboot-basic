package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.message.QueryMessage.*;

@Slf4j
@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BlacklistRepository blacklistRepository;

    public CustomerRepository(DataSource dataSource, BlacklistRepository blacklistRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.blacklistRepository = blacklistRepository;
    }

    public Customer create(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER.getMessage(),
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getYearOfBirth(),
                false);
        return customer;
    }

    public Customer findById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_CUSTOMER.getMessage(), customerRowMapper(), id.toString().getBytes());
    }

    public List<Customer> list() {
        log.info("list 시작");
        return jdbcTemplate.query(LIST_CUSTOMER.getMessage(), customerRowMapper());
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        jdbcTemplate.update(UPDATE_YEAR_OF_BIRTH_CUSTOMER.getMessage(), year, id.toString().getBytes());
        return this.findById(id);
    }

    public Customer updateName(UUID id, String name) {
        jdbcTemplate.update(UPDATE_NAME_CUSTOMER.getMessage(), name, id.toString().getBytes());
        return this.findById(id);
    }

    public int delete(UUID id) {
        int update = jdbcTemplate.update(DELETE_CUSTOMER.getMessage(), id.toString().getBytes());
        return update;
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            log.info("customer_id: " + convertBytesToUUID(rs.getBytes("customer_id")));
            log.info("name : " + rs.getString("name"));
            log.info("year_of_birth: " + rs.getInt("year_of_birth"));

            return new Customer(convertBytesToUUID(rs.getBytes("customer_id")),
                    rs.getString("name"),
                    rs.getInt("year_of_birth"),
                    rs.getBoolean("is_blacklist"));
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
