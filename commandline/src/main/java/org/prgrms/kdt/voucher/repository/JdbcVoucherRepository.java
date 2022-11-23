package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherException;
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

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(String type, long discountDegree) {
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
        String sql = "update voucher set discount_degree = :discountDegree where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("discountDegree", discountDegree)
                .addValue("voucherId", voucherId);

        jdbcTemplate.update(sql, param);

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
        String sql ="delete from voucher where voucher_id = :voucherId";
        Map<String, Object> param = Map.of("voucherId", voucherId);
        int result = jdbcTemplate.update(sql, param);
        if(result == NOT_AFFECT_RESULT){
            throw new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage());
        }
        cache.remove(voucherId);
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
}
