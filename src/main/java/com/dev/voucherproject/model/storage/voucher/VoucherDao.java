package com.dev.voucherproject.model.storage.voucher;

import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.voucherproject.model.voucher.VoucherPolicy.*;


@Repository
@Profile("default")
public class VoucherDao implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(VoucherDao.class);

    public static final String INSERT = "INSERT INTO vouchers(voucher_id, policy, discount_figure)" +
        "VALUES (UUID_TO_BIN(:voucherId), :policy, :discount_figure)";

    public static final String UPDATE = "UPDATE vouchers SET policy = :policy,  discount_figure = :discount_figure " +
        "WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    public static final String FIND_ALL = "SELECT * FROM vouchers";

    public static final String FIND_BY_UUID = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    public static final String DELETE_ALL = "DELETE FROM vouchers";

    public static final String DELETE_BY_UUID = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    public static final String FIND_ALL_BY_POLICY = "SELECT * FROM vouchers WHERE policy = :policy";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherPolicy policy = resultSet.getString("policy").equals("F") ? FIXED_AMOUNT_VOUCHER : PERCENT_DISCOUNT_VOUCHER;
        long discountFigure = Long.parseLong(resultSet.getString("discount_figure"));

        return Voucher.of(voucherId, policy, discountFigure);
    };

    private SqlParameterSource toParamMap(Voucher voucher) {
        return new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString().getBytes())
            .addValue("policy", toOneLetter(voucher.getVoucherPolicy()))
            .addValue("discount_figure", voucher.getDiscountFigure());
    }

    @Override
    public void insert(Voucher voucher) {
        jdbcTemplate.update(INSERT, toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, rowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(FIND_BY_UUID, Collections.singletonMap("voucherId", voucherId.toString().getBytes()), rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.warn("{} 는 존재하지 않는 바우처 아이디입니다.", voucherId);
        }
        return Optional.empty();
    }

    public List<Voucher> findAllByPolicy(VoucherPolicy voucherPolicy) {
        return jdbcTemplate.query(FIND_ALL_BY_POLICY, Collections.singletonMap("policy", toOneLetter(voucherPolicy)), rowMapper);
    }


    public void update(Voucher voucher) {
        jdbcTemplate.update(UPDATE, toParamMap(voucher));
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_UUID, Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    private String toOneLetter(VoucherPolicy voucherPolicy) {
        if (voucherPolicy == FIXED_AMOUNT_VOUCHER) {
            return "F";
        }

        return "P";
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return uuid;
    }
}
