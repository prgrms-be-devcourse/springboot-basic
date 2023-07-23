package com.example.demo.repository.customer;

import com.example.demo.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customer VALUES (:id, :name, :age)";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("name", customer.getName())
                .addValue("age", customer.getAge());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count != 1) {
            log.error("고객이 save되지 않았음. 잘 못된 입력 {}", customer);
            throw new IllegalArgumentException(String.format("고객이 save되지 않았음. 잘 못된 입력 : %s", customer));
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id.toString());

        try {
            Customer customer = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            log.error("존재하지 않는 아이디가 입력되어 조회할 수 없음(Optional.empty()로 반환). 존재하지 않는 id = {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";

        List<Customer> customers = namedParameterJdbcTemplate.query(sql, rowMapper());
        return customers;
    }


    @Override
    public void updateName(UUID id, String name) {
        String sql = "UPDATE customer SET name = :name WHERE id = :id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            log.error("존재하지 않는 아이디가 입력되어 customer 정보를 업데이트 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 customer 정보를 업데이트 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM customer WHERE id = :id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            log.error("존재하지 않는 아이디가 입력되어 customer 정보를 삭제 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 customer 정보를 삭제 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    @Override
    public boolean notExistById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id.toString());
        return namedParameterJdbcTemplate.query(sql, paramSource, (rs, count) -> 0).isEmpty();
    }

    private RowMapper<Customer> rowMapper() {
        return (resultSet, count) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");

            return Customer.builder()
                    .id(id)
                    .name(name)
                    .age(age)
                    .build();
        };
    }
}
