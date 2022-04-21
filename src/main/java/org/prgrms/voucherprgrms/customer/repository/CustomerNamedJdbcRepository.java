package org.prgrms.voucherprgrms.customer.repository;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Primary
@Qualifier("named")
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        var voucherId = resultSet.getBytes("voucher_id") != null ?
                toUUID(resultSet.getBytes("voucher_id")) : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, name, email, createdAt, voucherId);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {
            {
                put("customerId", customer.getCustomerId().toString().getBytes());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("voucherId", customer.getVoucherId() != null ? customer.getVoucherId().toString().getBytes() : null);
            }
        };
    }

    @Override
    public Customer insert(Customer customer) {
        var insert = jdbcTemplate.update("insert into CUSTOMERS(customer_id, name, email) values(UUID_TO_BIN(:customerId), :name, :email)",
                toParamMap(customer));

        if (insert != 1) {
            throw new RuntimeException("Customer Insertion failed");
        }
        return customer;
    }


    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from CUSTOMERS where email=:email",
                Collections.singletonMap("email", email),
                customerRowMapper));
    }

    public Optional<Customer> findByVoucher(Voucher voucher) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from CUSTOMERS where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucher.getVoucherId().toString().getBytes()),
                customerRowMapper));
    }

    @Override
    public Customer changeVoucher(Customer customer) {
        var update = jdbcTemplate.update("update CUSTOMERS set voucher_id=UUID_TO_BIN(:voucherId) where customer_id=UUID_TO_BIN(:customerId)", toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Allocation failed");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from CUSTOMERS", Collections.emptyMap());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from CUSTOMERS", customerRowMapper);
    }


    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
