package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateReqDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcDao implements CustomerDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerJdbcDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer VALUES (:id, :status)";
        SqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", customer.getCustomerId().toString())
            .addValue("status", customer.getCustomerStatus().getStatusName());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        List<Customer> customers = namedParameterJdbcTemplate.query(sql, customerRowMapper());
        return customers;
    }


    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", id.toString());
        try {
            Customer customer = namedParameterJdbcTemplate.queryForObject(sql, paramMap, customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByCustomerStatus(CustomerStatus customerStatus) {
        String sql = "SELECT * FROM customer WHERE status = :status";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("status", customerStatus.getStatusName());
        List<Customer> customers = namedParameterJdbcTemplate.query(sql, paramMap, customerRowMapper());
        return customers;
    }

    @Override
    public void updateStatus(CustomerUpdateReqDto reqDto) {
        String sql = "UPDATE customer SET status = :status WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("status", reqDto.getStatus().getStatusName())
            .addValue("id", reqDto.getId().toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM customer WHERE id = :id";
        SqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", id.toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            String status = resultSet.getString("status");
            CustomerStatus customerStatus = CustomerStatus.findByStatus(status);
            return new Customer(UUID.fromString(id), customerStatus);
        };
    }
}
