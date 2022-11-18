package org.programmers.weekly.mission.domain.voucher.repository;

import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.model.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long discount = resultSet.getInt("discount");

        return new VoucherDto(voucherId, type, discount);
    };

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type", voucher.getType());
            put("discount", voucher.getDiscount());
        }};
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, discount) VALUES (UUID_TO_BIN(:voucherId), :type, :discount)",
                toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return new VoucherDto(voucher.getVoucherId(), voucher.getType(), voucher.getDiscount());
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET type = :type, discount = :discount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

}
