package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

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
        jdbcTemplate.update(DELETE_QUERY, id);
        return id;
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> new Customer(rs.getObject("customer_id", UUID.class),
                rs.getString("name"),
                rs.getInt("year_of_birth"));
    }
}
