package org.programmers.springorder.voucher.repository;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Profile("default")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT = "insert into vouchers(voucher_id, discount_value, voucher_type) values(UUID_TO_BIN(:voucherId), :discountValue, :voucherType)";
    private final String UPDATE_VOUCER_OWNER = "update vouchers set customer_id = UUID_TO_BIN(:customerId) where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String FIND_ALL = "select * from vouchers";
    private final String FIND_BY_VOUCHER_ID = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String FIND_BY_CUSTOMER_ID = "select * from vouchers where customer_id = UUID_TO_BIN(:customerId)";

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_VOUCHER_ID,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }


    @Override
    public Voucher updateVoucherOwner(Voucher voucher, Customer customer) {
        int update = jdbcTemplate.update(UPDATE_VOUCER_OWNER, toUpdateOwnerMap(voucher, customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllByCustomerId(Customer customer) {
        return Collections.unmodifiableList(jdbcTemplate.query(
                FIND_BY_CUSTOMER_ID,
                Collections.singletonMap("customerId", customer.getCustomerId().toString().getBytes()),
                voucherRowMapper
        ));
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        jdbcTemplate.update("delete  from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
    }

    public void clear() {
        jdbcTemplate.getJdbcOperations().update("delete from vouchers");
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> map = new HashMap<>();
        map.put("voucherId", voucher.getVoucherId().toString().getBytes());
        map.put("discountValue", voucher.getDiscountValue());
        map.put("voucherType", voucher.getVoucherType().name());
        return map;
    }

    private Map<String, Object> toUpdateOwnerMap(Voucher voucher, Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("voucherId", voucher.getVoucherId().toString().getBytes());
        map.put("customerId", customer.getCustomerId().toString().getBytes());
        return map;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountValue = resultSet.getLong("discount_value");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        UUID customerID = resultSet.getBytes("customer_id") != null ?
                toUUID(resultSet.getBytes("customer_id")) : null;
        return Voucher.getVoucher(voucherId, discountValue, voucherType, customerID);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
