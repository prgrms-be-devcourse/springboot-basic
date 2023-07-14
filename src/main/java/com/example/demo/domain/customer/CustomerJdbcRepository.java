package com.example.demo.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerJdbcRepository implements CustomerRepository {

    private final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers VALUES (:customer_id, :name, :age)";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customer_id", customer.getCustomerId().toString())
                .addValue("name", customer.getName())
                .addValue("age", customer.getAge());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count != 1) {
            logger.error("고객이 save되지 않았음. 잘 못된 입력 {}", customer);
            throw new IllegalArgumentException(String.format("고객이 save되지 않았음. 잘 못된 입력 : %s", customer));
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customers WHERE customer_id = :customer_id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id.toString());

        try {
            Customer customer = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("존재하지 않는 아이디가 입력되어 조회할 수 없음. 존재하지 않는 id = {}", id, e);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 조회할 수 없습니다. 존재하지 않는 id = %s", id), e);
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";

        List<Customer> customers = namedParameterJdbcTemplate.query(sql, rowMapper());
        return customers;
    }


    @Override
    public void updateName(UUID id, String name) {
        String sql = "UPDATE customers SET name = :name WHERE customer_id = :customer_id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("customer_id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.error("존재하지 않는 아이디가 입력되어 customer 정보를 업데이트 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 customer 정보를 업데이트 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM customers WHERE customer_id = :customer_id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("customer_id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.error("존재하지 않는 아이디가 입력되어 customer 정보를 삭제 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 customer 정보를 삭제 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    private RowMapper<Customer> rowMapper() {
        return (resultSet, count) -> {
            String customerId = resultSet.getString("customer_id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");

            return Customer.builder()
                    .customerId(UUID.fromString(customerId))
                    .name(name)
                    .age(age)
                    .build();
        };
    }
}
