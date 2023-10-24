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

@Slf4j
@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = "insert into customers(customer_id, name, year_of_birth) values(UUID_TO_BIN(?), ?, ?)";
    private static final String FIND_BY_ID_QUERY = "select * from customers where customer_id = UUID_TO_BIN(?)";
    private static final String LIST_QUERY = "select * from customers";
    private static final String UPDATE_YEAR_OF_BIRTH_QUERY = "update customers set year_of_birth=? where customer_id=UUID_TO_BIN(?)";
    private static final String UPDATE_NAME_QUERY = "update customers set name=? where customer_id=UUID_TO_BIN(?)";
    private static final String DELETE_QUERY = "delete from customers where customer_id = UUID_TO_BIN(?)";

    public CustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Customer create(Customer customer) {
        jdbcTemplate.update(INSERT_QUERY,
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getYearOfBirth());
        return customer;
    }

    public Customer findById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, customerRowMapper(), id.toString().getBytes());
    }

    public List<Customer> list() {
        log.info("list 시작");
        return jdbcTemplate.query(LIST_QUERY, customerRowMapper());
    }

    public Customer updateYearOfBirth(UUID id, int year) {
        jdbcTemplate.update(UPDATE_YEAR_OF_BIRTH_QUERY, year, id.toString().getBytes());
        return this.findById(id);
    }

    public Customer updateName(UUID id, String name) {
        jdbcTemplate.update(UPDATE_NAME_QUERY, name, id.toString().getBytes());
        return this.findById(id);
    }

    public UUID delete(UUID id) {
        jdbcTemplate.update(DELETE_QUERY, id.toString().getBytes());
        return id;
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
