package org.prgrms.kdt.jdbcRepository;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.CustomerVoucherEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.repository.CustomerVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.prgrms.kdt.jdbcRepository.CustomerJdbcRepository.customerEntityRowMapper;
import static org.prgrms.kdt.jdbcRepository.CustomerJdbcRepository.toUUID;
import static org.prgrms.kdt.jdbcRepository.VoucherJdbcRepository.voucherEntityRowMapper;

@Repository
public class CustomerVoucherJdbcRepository implements CustomerVoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerVoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private HashMap<String, Object> toParamMap(CustomerVoucherEntity customerVoucherEntity) {
        return new HashMap<>() {
            {
                put("customerVoucherId", customerVoucherEntity.getCustomerVoucherId().toString().getBytes());
                put("customerId", customerVoucherEntity.getCustomerId().toString().getBytes());
                put("voucherId", customerVoucherEntity.getVoucherId().toString().getBytes());
                put("createdAt", Timestamp.valueOf(customerVoucherEntity.getCreatedAt()));
            }
        };
    }

    private static RowMapper<CustomerVoucherEntity> customerVoucherEntityRowMapper = (resultSet, i) -> {
        var customerVoucherId = toUUID(resultSet.getBytes("customer_voucher_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new CustomerVoucherEntity(customerVoucherId, customerId, voucherId, createdAt);
    };


    @Override
    public CustomerVoucherEntity insert(CustomerVoucherEntity customerVoucherEntity) {
        var update = jdbcTemplate.update("insert into customer_voucher(customer_voucher_id,customer_id, voucher_id, created_at) values(UUID_TO_BIN(:customerVoucherId),UUID_TO_BIN(:customerId),UUID_TO_BIN(:voucherId), :createdAt)",
                toParamMap(customerVoucherEntity));
        if (update != 1) {
            throw new RuntimeException("Nothing was Inserted");
        }
        return customerVoucherEntity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customer_voucher", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId, UUID voucherId) {
        Map<String, byte[]> idList = new HashMap<>() {{
            put("customerId", customerId.toString().getBytes());
            put("voucherId", voucherId.toString().getBytes());
        }};
        jdbcTemplate.update("delete from customer_voucher where customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)",
                idList);
    }

    @Override
    public List<CustomerVoucherEntity> findAll() {
        return jdbcTemplate.query("select * from customer_voucher", customerVoucherEntityRowMapper);
    }

    @Override
    public List<VoucherEntity> findByCustomerId(UUID customerId) {
        try {
            return (List<VoucherEntity>) jdbcTemplate.queryForObject("select v.voucher_id, v.voucher_type, v.discount, v.created_at from" +
                            " customer_voucher as cv right outer join voucher as v on cv.customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), voucherEntityRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<CustomerEntity> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select c.customer_id, c.name, c.email, c.created_at from" +
                            " customer_voucher as cv right outer join customer as c on cv.voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    customerEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }
}
