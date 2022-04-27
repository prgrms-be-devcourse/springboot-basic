package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, type, value) VALUES (UUID_TO_BIN(:voucherId), :type, :value)";
    private static final String UPDATE_VALUE_BY_ID_SQL = "UPDATE vouchers SET value = :value WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE type = :type";
    private static final String SELECT_COUNT_ALL_SQL = "SELECT COUNT(*) FROM vouchers";


    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long value = resultSet.getLong("value");
        if(type.equals("fix")) {
            return FixedAmountVoucher.builder()
                    .id(voucherId)
                    .amount(value)
                    .build();
        }
        else {
            return PercentDiscountVoucher.builder()
                    .id(voucherId)
                    .percent(value)
                    .build();
        }
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {

        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {

        jdbcTemplate.update(UPDATE_VALUE_BY_ID_SQL,toParamMap(voucher));
        return null;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_SQL, Collections.emptyMap(),Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(String type) {
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, Collections.singletonMap("type", type), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("JdbcVoucherRepository findById - 해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_BY_ID_SQL,Collections.emptyMap());
    }



    private Map<String, Object> toParamMap(Voucher voucher) {
        String type;
        String voucherTypeName = voucher.getClass().getSimpleName();
        if(voucherTypeName.equals("FixedAmountVoucher")) {
            type = "fix";
        }
        else {
            type = "percent";
        }

        HashMap<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", voucher.getVoucherID().toString().getBytes());
            put("type", type);
            put("value", voucher.getValue());
        }};
        return hashMap;

    }
    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());

    }
}
