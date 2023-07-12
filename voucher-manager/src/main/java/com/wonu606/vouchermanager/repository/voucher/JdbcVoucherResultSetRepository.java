package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcVoucherResultSetRepository implements VoucherResultSetRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherResultSetRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (isVoucherIdPresent(voucher)) {
            updateVoucher(voucher);
        }
        insertVoucher(voucher);
        return voucher;
    }

    @Override
    public Optional<VoucherResultSet> findById(UUID id) {
        String selectSql = "SELECT * FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", id.toString());
        try {
            VoucherResultSet voucherResultSet =
                    namedParameterJdbcTemplate.queryForObject(selectSql,
                            params,
                            voucherResultSetRowMapper());
            return Optional.ofNullable(voucherResultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherResultSet> findAll() {
        String selectSql = "SELECT * FROM voucher";
        return namedParameterJdbcTemplate.query(selectSql, voucherResultSetRowMapper());
    }

    @Override
    public void deleteById(UUID id) {
        String deleteSql = "DELETE FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", id.toString());
        namedParameterJdbcTemplate.update(deleteSql, params);
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM voucher";
        namedParameterJdbcTemplate.update(deleteSql, new HashMap<>());
    }

    @Override
    public List<VoucherResultSet> findAllByUuids(List<UUID> uuidList) {
        if (uuidList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> uuidStringList = uuidList.stream().map(UUID::toString).collect(Collectors.toList());

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        
        parameters.addValue("uuidList", uuidStringList);

        String selectSql = "SELECT * FROM voucher WHERE voucher_id IN (:uuidList)";

        return namedParameterJdbcTemplate.query(selectSql, parameters, voucherResultSetRowMapper());
    }

    private RowMapper<VoucherResultSet> voucherResultSetRowMapper() {
        return (resultSet, rowNum) -> {
            String voucherSimpleName = resultSet.getString("voucher_type");
            UUID uuid = UUID.fromString(resultSet.getString("voucher_id"));
            double discountValue = resultSet.getDouble("discount_value");

            return new VoucherResultSet(uuid, voucherSimpleName, discountValue);
        };
    }

    private boolean isVoucherIdPresent(Voucher voucher) {
        String selectSql = "SELECT count(*) FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", voucher.getUuid().toString());

        Integer count = namedParameterJdbcTemplate.queryForObject(selectSql, params, Integer.class);
        return count != null && count >= 0;
    }

    private void updateVoucher(Voucher voucher) {
        String updateSql = "UPDATE voucher SET voucher_type = :voucher_type, discount_value = :discount_value WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_type", voucher.getClass().getSimpleName());
        params.put("voucher_id", voucher.getUuid().toString());
        params.put("discount_value", voucher.getDiscountValue());

        namedParameterJdbcTemplate.update(updateSql, params);
    }

    private void insertVoucher(Voucher voucher) {
        String insertSql = "INSERT INTO voucher (voucher_id, voucher_type, discount_value) VALUES (:voucher_id, :voucher_type, :discount_value)";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_type", voucher.getClass().getSimpleName());
        params.put("voucher_id", voucher.getUuid().toString());
        params.put("discount_value", voucher.getDiscountValue());

        namedParameterJdbcTemplate.update(insertSql, params);
    }
}
