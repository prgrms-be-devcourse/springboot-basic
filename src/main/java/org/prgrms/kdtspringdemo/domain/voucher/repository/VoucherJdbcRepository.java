package org.prgrms.kdtspringdemo.domain.voucher.repository;

import org.prgrms.kdtspringdemo.domain.voucher.data.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class VoucherJdbcRepository implements VoucherRepository{
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("type");
        int amount = resultSet.getInt("amount");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));

        if(voucherType.equals("FIXED")){
            return new FixedAmountVoucher(voucherId, amount, customerId);
        }else{
            return new PercentDiscountVoucher(voucherId, amount, customerId);
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type", voucher.getType());
            put("amount", voucher.getAmount());
            put("customerId", voucher.getCustomerId().toString().getBytes());
        }};
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String insertQuery = "INSERT INTO vouchers(voucher_id, type, amount, customer_id) VALUES (UUID_TO_BIN(:voucherId),:type,:amount,UUID_TO_BIN(:customerId))";
        int update = jdbcTemplate.update(insertQuery,toParamMap(voucher));

        if (update != 1) {
            logger.error("Voucher 등록 에서 Nothing was inserted");
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        //customer_id = :customer_id
        String updateQuery = "UPDATE vouchers SET type = :type, amount = :amount, customer_id =  UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        int update = jdbcTemplate.update(updateQuery, toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return voucher;
    }

    @Override
    public int count() {
        String countQuery = "select count(*) from vouchers";

        return jdbcTemplate.queryForObject(countQuery, Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        String findAllQuery = "select * from vouchers";

        return jdbcTemplate.query(findAllQuery, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findByCustomerId(UUID customerId) {
        String findByCustomerQuery = "select * from vouchers WHERE customer_id = UUID_TO_BIN(:customerId)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByCustomerQuery,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result: {}", e.getMessage());
            return Optional.empty();
        }
//        return jdbcTemplate.query(findByCustomerQuery, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String findByIdQuery = "select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

        try {
            return Optional.of(jdbcTemplate.queryForObject(findByIdQuery,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);

            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String deleteAllQuery = "DELETE FROM vouchers";
        jdbcTemplate.update(deleteAllQuery, Collections.emptyMap());
    }

}
