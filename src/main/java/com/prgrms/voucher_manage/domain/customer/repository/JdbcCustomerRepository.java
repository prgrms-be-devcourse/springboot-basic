package com.prgrms.voucher_manage.domain.customer.repository;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Customer>  findBlackList(){
        String sql = "select * from customer where type = ?";
        return jdbcTemplate.query(sql, rowMapper,"B");
    }

    public Customer save(Customer customer){
        String sql = "insert into customer(customer_id, name, type) values (UUID_TO_BIN(?), ?, ?)";
        jdbcTemplate.update(sql,customer.getCustomerId().toString().getBytes(), customer.getName(), customer.getType());
        return customer;
    }

    public void update(Customer customer){
        String sql = "update customer set name = ?, type = ? where customer_id = ?";
        int isUpdated = jdbcTemplate.update(sql, customer.getName(), customer.getType(),customer.getCustomerId().toString().getBytes());
        if (isUpdated!=1) throw new RuntimeException("Nothing was updated");
    }

    public void deleteAll(){
        String sql = "DELETE FROM customer";
        jdbcTemplate.update(sql);
    }

    private static final RowMapper<Customer> rowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var type = resultSet.getString("type");
        return new Customer(customerId, name, type);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
