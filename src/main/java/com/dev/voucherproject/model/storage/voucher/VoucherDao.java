package com.dev.voucherproject.model.storage.voucher;

import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.voucherproject.model.voucher.VoucherPolicy.*;


@Repository
public class VoucherDao {
    private static final String INSERT = "INSERT INTO vouchers(voucher_id, policy, discount_figure)" +
        "VALUES (UUID_TO_BIN(:voucherId), :policy, :discount_figure)";

    private static final String UPDATE = "UPDATE vouchers SET policy = :policy,  discount_figure = :discount_figure " +
        "WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    private static final String FIND_ALL = "SELECT * FROM vouchers";

    private static final String FIND_BY_UUID = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    private static final String DELETE_ALL = "DELETE FROM vouchers";

    private static final String DELETE_BY_UUID = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    private static final String FIND_ALL_BY_POLICY = "SELECT * FROM vouchers WHERE policy = :policy";

    private static final String FIND_ALL_BY_BETWEEN_DATE = "SELECT * FROM vouchers WHERE DATE(created_at) " +
            "BETWEEN :startDate AND :endDate";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherPolicy policy = valueOf(resultSet.getString("policy"));
        long discountFigure = Long.parseLong(resultSet.getString("discount_figure"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return Voucher.of(voucherId, createdAt, policy, discountFigure);
    };

    private SqlParameterSource toParamMap(Voucher voucher) {
        return new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString().getBytes())
            .addValue("policy", VoucherPolicy.convertPolicyNameToPolicy(voucher.getPolicyName()).toString())
            .addValue("discount_figure", voucher.getDiscountFigure())
            .addValue("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
    }

    private SqlParameterSource toDateParamMap(LocalDate startDate, LocalDate endDate) {
        return new MapSqlParameterSource()
                .addValue("startDate", Date.valueOf(startDate))
                .addValue("endDate", Date.valueOf(endDate));
    }

    public void insert(Voucher voucher) {
        jdbcTemplate.update(INSERT, toParamMap(voucher));
    }

    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, rowMapper);
    }

    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(FIND_BY_UUID, Collections.singletonMap("voucherId", voucherId.toString().getBytes()), rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(MessageFormat.format("{0} 는 존재하지 않는 바우처 ID 입니다.", voucherId));
        }
    }

    public List<Voucher> findAllByPolicy(VoucherPolicy voucherPolicy) {
        return jdbcTemplate.query(FIND_ALL_BY_POLICY, Collections.singletonMap("policy", voucherPolicy.name()), rowMapper);
    }

    public List<Voucher> findAllBetweenDates(LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(FIND_ALL_BY_BETWEEN_DATE, toDateParamMap(startDate, endDate), rowMapper);
    }

    public void update(Voucher voucher) {
        jdbcTemplate.update(UPDATE, toParamMap(voucher));
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    public void deleteById(UUID voucherId) {
        int update = jdbcTemplate.update(DELETE_BY_UUID, Collections.singletonMap("voucherId", voucherId.toString().getBytes()));

        if (update != 1) {
            throw new IllegalArgumentException(MessageFormat.format("{0} 는 존재하지 않는 바우처 ID 입니다.", voucherId));
        }
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return uuid;
    }
}
