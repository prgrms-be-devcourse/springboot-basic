package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
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
        fileToDb();
    }

    public Customer create(Customer customer) {
        jdbcTemplate.update(INSERT_QUERY_CUSTOMER.getMessage(),
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getYearOfBirth());
        return customer;
    }

    public Customer findById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY_VOUCHER.getMessage(), customerRowMapper(), id.toString().getBytes());
    }

    public List<Customer> list() {
        log.info("list 시작");
        return jdbcTemplate.query(LIST_QUERY_CUSTOMER.getMessage(), customerRowMapper());
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        jdbcTemplate.update(UPDATE_YEAR_OF_BIRTH_QUERY.getMessage(), year, id.toString().getBytes());
        return this.findById(id);
    }

    public Customer updateName(UUID id, String name) {
        jdbcTemplate.update(UPDATE_NAME_QUERY.getMessage(), name, id.toString().getBytes());
        return this.findById(id);
    }

    public UUID delete(UUID id) {
        jdbcTemplate.update(DELETE_QUERY_CUSTOMER.getMessage(), id.toString().getBytes());
        return id;
    }

    private void fileToDb() {
        List<Customer> blacklist = blacklistRepository.blacklist();
        blacklist.forEach(this::create);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            log.info("customer_id: " + convertBytesToUUID(rs.getBytes("customer_id")));
            log.info("name : " + rs.getString("name"));
            log.info("year_of_birth: " + rs.getInt("year_of_birth"));

            return new Customer(convertBytesToUUID(rs.getBytes("customer_id")),
                    rs.getString("name"),
                    rs.getInt("year_of_birth"));
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
