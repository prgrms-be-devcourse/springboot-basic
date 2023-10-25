package com.prgrms.voucher_manage.domain.customer.repository;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.matchCustomerType;

@Repository
@RequiredArgsConstructor
public class JdbcCustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Customer> findAll(){
        String sql = "select * from customer";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Customer> findByName(String name){
        String sql = "select * from customer where name = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper,name));
    }

    public List<Customer> findByType(String type){
        String sql = "select * from customer where type = ?";
        return jdbcTemplate.query(sql, rowMapper,type);
    }

    public Customer save(Customer customer){
        String sql = "insert into customer(customer_id, name, type) values (UUID_TO_BIN(?), ?, ?)";
        jdbcTemplate.update(sql,customer.getId().toString().getBytes(), customer.getName(), customer.getType().getLabel());
        return customer;
    }

    public int update(Customer customer){
        String sql = "update customer set type = ? where name = ?";
        return jdbcTemplate.update(sql, customer.getType().getLabel(),customer.getName());
    }

    public void deleteAll(){
        String sql = "DELETE FROM customer";
        jdbcTemplate.update(sql);
    }

    private static final RowMapper<Customer> rowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var type = resultSet.getString("type");
        return new Customer(customerId, name, matchCustomerType(type));
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
