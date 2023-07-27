package org.devcourse.springbasic.domain.voucher.dao;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dao.UpdateResult;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherFactory;
import org.devcourse.springbasic.domain.voucher.domain.VoucherTable;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.global.exception.custom.DuplicateVoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Profile("dev")
@Repository
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        String QUERY =  "SELECT * FROM " + VoucherTable.TABLE_NAME +
                        " WHERE " + VoucherTable.VOUCHER_ID + " = UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString().getBytes());

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY, paramMap, VOUCHER_ROW_MAPPER));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCriteria(
        LocalDate creationStartDate,
        LocalDate creationEndDate,
        VoucherType voucherType
    ) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT * FROM " +  VoucherTable.TABLE_NAME + " WHERE 1=1 "
        );
        MapSqlParameterSource paramMap = new MapSqlParameterSource();

        if (creationStartDate != null) {
            queryBuilder.append(" AND " + VoucherTable.CREATED_AT + " >= :startDate");
            paramMap.addValue("startDate", creationStartDate);
        }
        if (creationEndDate != null) {
            queryBuilder.append(" AND " + VoucherTable.CREATED_AT + " <= :endDate");
            paramMap.addValue("endDate", creationEndDate);
        }
        if (voucherType != null) {
            queryBuilder.append(" AND " + VoucherTable.VOUCHER_TYPE + " = :discountType");
            paramMap.addValue("discountType", voucherType.toString());
        }

        return jdbcTemplate.query(queryBuilder.toString(), paramMap, VOUCHER_ROW_MAPPER);
    }

    @Override
    public UUID save(Voucher voucher) {

        String QUERY =  "INSERT INTO " + VoucherTable.TABLE_NAME + "(" +
                        VoucherTable.VOUCHER_ID + "," +
                        VoucherTable.DISCOUNT_AMOUNT + "," +
                        VoucherTable.VOUCHER_TYPE + "," +
                        VoucherTable.CREATED_AT + ")" +
                        " values (UUID_TO_BIN(:voucherId), :discountAmount, :voucherType, :createdAt)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                .addValue("discountAmount", voucher.getDiscountRate())
                .addValue("voucherType", voucher.getVoucherType().toString())
                .addValue("createdAt", LocalDateTime.now());

        try {
            jdbcTemplate.update(QUERY, paramMap);
            return voucher.getVoucherId();
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new DuplicateVoucherException("이미 존재하는 바우처입니다.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String QUERY =
                "DELETE FROM " + VoucherTable.TABLE_NAME +
                        " WHERE " + VoucherTable.VOUCHER_ID + " = UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString().getBytes());

        UpdateResult updateResult = new UpdateResult(jdbcTemplate.update(QUERY, paramMap));
        if (!updateResult.isSucceeded()) {
            throw new NoSuchElementException("삭제할 바우처가 없습니다.");
        }
    }


    private static final RowMapper<Voucher> VOUCHER_ROW_MAPPER = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes(VoucherTable.VOUCHER_ID));
        Long discountAmount = resultSet.getLong(VoucherTable.DISCOUNT_AMOUNT);
        VoucherType voucherType = VoucherType.findVoucherType(resultSet.getString(VoucherTable.VOUCHER_TYPE));
        LocalDateTime createdAt = resultSet.getTimestamp(VoucherTable.CREATED_AT).toLocalDateTime();

        return VoucherFactory.createVoucherWithParam(voucherId, discountAmount, voucherType, createdAt);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
