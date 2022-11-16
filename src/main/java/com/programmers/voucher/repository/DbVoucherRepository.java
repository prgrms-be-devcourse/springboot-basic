package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.voucher.repository.sql.VoucherSql.*;

@Repository
@Profile("dev")
public class DbVoucherRepository implements VoucherRepository{
    private final Logger log = LoggerFactory.getLogger(DbVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DbVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_VOUCHER_BY_ID,
                    Collections.singletonMap("voucherId", id.toString().getBytes()), voucherRowMapper));
        } catch (DataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query(FIND_ALL_VOUCHERS, voucherRowMapper);
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("createAt", LocalDateTime.now());

        Map<String, Object> voucherRuleMap = new HashMap<>();
        voucherRuleMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        voucherRuleMap.put("voucherType", voucher.getClass().getSimpleName().replaceAll("Voucher", ""));
        voucherRuleMap.put("voucherValue", voucher.getValue());

        jdbcTemplate.update(INSERT_VOUCHER, paramMap);
        jdbcTemplate.update(INSERT_VOUCHER_RULE, voucherRuleMap);

        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    private final static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("voucher_type").substring(0, 1);
        long value = resultSet.getLong("voucher_value");

        VoucherType voucherType = VoucherType.getValidateVoucherType(type);
        return VoucherFactory.createVoucher(voucherId, voucherType, value);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
