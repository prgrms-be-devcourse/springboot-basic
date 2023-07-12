package org.devcourse.springbasic.domain.voucher.dao;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
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
import java.util.stream.Collectors;

@Profile("dev")
@Repository
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public VoucherDto.ResponseDto findById(UUID voucherId) {

        String QUERY = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString().getBytes());

        try {
            Voucher voucher = jdbcTemplate.queryForObject(
                    QUERY,
                    paramMap,
                    VOUCHER_ROW_MAPPER
            );
            return new VoucherDto.ResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountRate());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new IllegalArgumentException("해당 ID인 바우처가 없습니다.");
        }
    }

    @Override
    public List<VoucherDto.ResponseDto> findAll() {
        String QUERY = "select * from vouchers";
        List<Voucher> vouchers = jdbcTemplate.query(QUERY, VOUCHER_ROW_MAPPER);
        return vouchers.stream()
                .map(voucher -> new VoucherDto.ResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountRate()))
                .collect(Collectors.toList());
    }

    @Override
    public UUID save(VoucherDto.SaveRequestDto voucherDto) {

        String QUERY = "insert into vouchers(voucher_id, discount_amount, voucher_type)" +
                     "values (UUID_TO_BIN(:voucherId), :discountAmount, :voucherType)";
        VoucherType voucherType = voucherDto.getVoucherType();
        Voucher voucher = voucherType.getVoucherSupplier().get();

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherType", voucher.getVoucherType())
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discountAmount", voucher.getDiscountRate());

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
