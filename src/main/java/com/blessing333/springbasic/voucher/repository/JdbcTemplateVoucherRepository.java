package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.common.util.UUIDUtil;
import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JdbcTemplateVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_ID = "voucherId";
    private static final String VOUCHER_ID_COLUMN = "voucher_id";
    private static final String VOUCHER_TYPE = "voucherType";
    private static final String VOUCHER_TYPE_COLUMN = "voucher_type";
    private static final String DISCOUNT_AMOUNT = "discountAmount";
    private static final String DISCOUNT_AMOUNT_COLUMN = "discount_amount";
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, discount_amount) VALUES (:voucherId, :voucherType, :discountAmount)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET discount_amount = :discountAmount WHERE voucher_id = :voucherId";
    private static final String FIND_ALL_SQL = "SELECT * FROM vouchers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";
    private static final String FIND_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE voucher_type = :voucherType";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_SQL = "DELETE FROM vouchers WHERE voucher_id = :voucherId";
    private static final VoucherRowMapper voucherRowMapper = new VoucherRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insert(Voucher voucher) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
    }

    @Override
    public void update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher;
        Map<String, byte[]> param = Collections.singletonMap(VOUCHER_ID, UUIDUtil.toBinary(voucherId));
        try {
            voucher = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            voucher = null;
        }
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findByVoucherType(Voucher.VoucherType type) {
        Map<String, String> param = Collections.singletonMap(VOUCHER_TYPE, type.getOptionNumber());
        return jdbcTemplate.query(FIND_BY_TYPE_SQL,param,voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, byte[]> param = Collections.singletonMap(VOUCHER_ID, UUIDUtil.toBinary(voucherId));
        jdbcTemplate.update(DELETE_SQL, param);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> map = new HashMap<>();
        map.put(VOUCHER_TYPE, voucher.getVoucherType().getOptionNumber());
        map.put(VOUCHER_ID, UUIDUtil.toBinary(voucher.getVoucherId()));
        map.put(DISCOUNT_AMOUNT, voucher.getDiscountAmount());
        return map;
    }

    private static class VoucherRowMapper implements RowMapper<Voucher> {
        @Override
        public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var voucherId = UUIDUtil.toUUID(resultSet.getBytes(VOUCHER_ID_COLUMN));
            var voucherType = resultSet.getString(VOUCHER_TYPE_COLUMN);
            int discountAmount = resultSet.getInt(DISCOUNT_AMOUNT_COLUMN);
            return new Voucher(voucherId, voucherType, discountAmount);
        }
    }
}
