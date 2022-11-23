package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> customerRowMapper = (resultSet, i) -> {
        var discountWay = resultSet.getString("discount_way");
        var discountValue = resultSet.getLong("discount_value");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var customerId = resultSet.getLong("customer_id");
        return new Voucher(voucherId, discountWay, discountValue, customerId);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }


    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountWay", voucher.getDiscountWay());
            put("discountValue", voucher.getDiscountValue());
            put("customerId", voucher.getCustomerId());
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", customerRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("insert into vouchers(voucher_id, discount_way, discount_value, customer_id) values (UUID_TO_BIN(:voucherId), :discountWay, :discountValue, :customerId)",
                toParamMap(voucher));
        if (update != 1) throw new RuntimeException("Insert failed");
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update("update vouchers set discount_way = :discountWay, customer_id = :customerId, discount_value = :discountValue where voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        if (update != 1) throw new RuntimeException("Insert failed");
        return voucher;
    }

    @Override
    public List<Voucher> findByDiscountWay(String discountWay) {
        return jdbcTemplate.query(
                "select * from vouchers where discount_way =:discountWay",
                customerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.EMPTY_MAP);
    }
}
