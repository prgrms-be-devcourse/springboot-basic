package org.devcourse.springbasic.domain.voucher.dao;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Profile("dev")
@Repository
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Voucher findById(UUID voucherId) {

        String QUERY = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString().getBytes());

        try {
            return jdbcTemplate.queryForObject(
                    QUERY,
                    paramMap,
                    VOUCHER_ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new IllegalArgumentException("해당 ID인 바우처가 없습니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        String QUERY = "select * from vouchers";
        return jdbcTemplate.query(QUERY, VOUCHER_ROW_MAPPER);
    }

    @Override
    public UUID save(Voucher voucher) {

        String QUERY = "insert into vouchers(voucher_id, discount_amount, voucher_type)" +
                     "values (UUID_TO_BIN(:voucherId), :discountAmount, :voucherType)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                .addValue("discountAmount", voucher.getDiscountRate())
                .addValue("voucherType", voucher.getVoucherType().toString());

        try {
            jdbcTemplate.update(QUERY, paramMap);
            return voucher.getVoucherId();
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new IllegalArgumentException("이미 존재하는 바우처입니다.");
        }
    }

    private static final RowMapper<Voucher> VOUCHER_ROW_MAPPER = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        Long discountAmount = resultSet.getLong("discount_amount");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));

        return voucherType.getVoucherBiFunction().apply(voucherId, discountAmount);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
