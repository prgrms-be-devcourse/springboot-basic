package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.voucher.NotFoundVoucherException;
import org.prgrms.kdt.exception.voucher.WrongRangeInputException;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"jdbc", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static Map<Long, Voucher> cache = new ConcurrentHashMap<>();

    private static final int NOT_AFFECT_RESULT = 0;
    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;
    private static final int MIN_AMOUNT = 0;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(String type, long discountDegree) {
        VoucherType voucherType = VoucherType.selectVoucherTypeByTypeNumber(type);
        validateDiscountDegree(voucherType, discountDegree);

        String sql = "insert into voucher(type_name, discount_degree) values( :typeName,:discountDegree)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String typeName = VoucherType.getTypeName(type);

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("typeName", typeName)
                .addValue("discountDegree", discountDegree);

        jdbcTemplate.update(sql, param, keyHolder, new String[]{"voucher_Id"});

        long voucherId = keyHolder.getKey().longValue();
        Voucher voucher = VoucherType.createVoucher(type, voucherId, discountDegree);
        cache.put(voucherId, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select voucher_id, type_name, discount_degree from voucher";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public Voucher findById(long voucherId) {
        if (cache.containsKey(voucherId)) {
            return cache.get(voucherId);
        }
        String sql = "select voucher_id, type_name, discount_degree from voucher where voucher_id = :voucherId";
        Map<String, Object> param = Map.of("voucherId", voucherId);

        try {
            Voucher voucher = jdbcTemplate.queryForObject(sql, param, voucherRowMapper());
            return voucher;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }
    }

    @Override
    public void update(long voucherId, long discountDegree) {
        Voucher voucher = findById(voucherId);
        String typeName = voucher.getTypeName();
        VoucherType voucherType = VoucherType.selectVoucherTypeFromTypeName(typeName);
        validateDiscountDegree(voucherType, discountDegree);

        String sql = "update voucher set discount_degree = :discountDegree where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("discountDegree", discountDegree)
                .addValue("voucherId", voucherId);

        int result = jdbcTemplate.update(sql, param);

        if (result == NOT_AFFECT_RESULT) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }

        if (cache.containsKey(voucherId)) {
            cache.remove(voucherId);
        }
        Voucher updateVoucher = findById(voucherId);

        cache.put(voucherId, updateVoucher);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from voucher", Collections.EMPTY_MAP);
        cache.clear();
    }

    @Override
    public void deleteById(long voucherId) {
        String sql = "delete from voucher where voucher_id = :voucherId";
        Map<String, Object> param = Map.of("voucherId", voucherId);
        int result = jdbcTemplate.update(sql, param);
        if (result == NOT_AFFECT_RESULT) {
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }
        cache.remove(voucherId);
    }

    @Override
    public List<Voucher> findByTypeName(String typeNumber) {
        String sql = "select voucher_id, type_name, discount_degree from voucher where type_name = :typeName";
        String typeName = VoucherType.getVoucherTypeName(typeNumber);
        Map<String, Object> param = Map.of("typeName", typeName);

        return jdbcTemplate.query(sql, param, voucherRowMapper());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return ((rs, rowNum) -> {
            long voucherId = rs.getLong("voucher_Id");
            String typeName = rs.getString("type_name");
            long discountDegree = rs.getLong("discount_degree");

            VoucherType voucherType = VoucherType.selectVoucherTypeFromTypeName(typeName);
            return VoucherType.createVoucher(voucherType, voucherId, discountDegree);
        });
    }

    private void validateDiscountDegree(VoucherType voucherType, long discountDegree) {
        if (voucherType == VoucherType.FIXED_AMOUNT) {
            validateFixedVoucher(discountDegree);
            return;
        }
        validatePercentDiscountVoucher(discountDegree);
    }

    private void validateFixedVoucher(long discountDegree) {
        if (MIN_AMOUNT > discountDegree) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }

    private void validatePercentDiscountVoucher(long discountDegree) {
        if (MIN_PERCENT > discountDegree || MAX_PERCENT < discountDegree) {
            throw new WrongRangeInputException(ErrorCode.WRONG_RANGE_INPUT_EXCEPTION.getMessage());
        }
    }
}
