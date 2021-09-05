package org.programmers.voucher.repository;

import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile("prod")
public class VoucherJdbcRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var voucherValue = Long.parseLong(resultSet.getString("voucher_value"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Voucher(voucherId, voucherType, voucherValue, createdAt);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType());
            put("voucherValue", voucher.getVoucherValue());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        }};
    }

    @Override
    public void save(Voucher voucher) {

        VoucherType temp = voucher.getVoucherType();
        int update = 0;
        switch (temp) {
            case FIXED -> {
                update = namedParameterJdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,voucher_value,created_at) VALUES(UUID_TO_BIN(:voucherId),'FIXED', :voucherValue, :createdAt)",
                        toParamMap(voucher));
            }

            case PERCENT -> {
                update = namedParameterJdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,voucher_value,created_at) VALUES(UUID_TO_BIN(:voucherId),'PERCENT', :voucherValue, :createdAt)",
                        toParamMap(voucher));
            }
        }

        if (update != 1) {
            throw new RuntimeException("Nothing was saved");
        }
    }

    @Override
    public void update(VoucherType voucherType, long value, long changeValue) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherType", voucherType.toString());
            put("voucherValue", value);
            put("changeValue", changeValue);
        }};
        var update = namedParameterJdbcTemplate.update("UPDATE vouchers SET voucher_value = :changeValue WHERE voucher_value = :voucherValue AND voucher_type = :voucherType",
                paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
    }

    @Override
    public Optional<Voucher> findByVoucherTypeAndVoucherValue(VoucherType voucherType, Long value) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherType", voucherType.toString());
            put("voucherValue", value);
        }};

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from vouchers WHERE voucher_type = :voucherType AND voucher_value = :voucherValue", paramMap, voucherRowMapper));
    }

    //여기서부터
    @Override
    public void deleteByVoucherId(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        var delete = namedParameterJdbcTemplate.update("DELETE from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)", paramMap);

        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public void deleteByVoucherTypeAndValue(VoucherType voucherType, long value) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherType", voucherType.toString());
            put("voucherValue", value);
        }};
        var delete = namedParameterJdbcTemplate.update("DELETE from vouchers WHERE voucher_type = :voucherType AND voucher_value = :voucherValue", paramMap);
        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }


    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE from vouchers", Collections.emptyMap());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
