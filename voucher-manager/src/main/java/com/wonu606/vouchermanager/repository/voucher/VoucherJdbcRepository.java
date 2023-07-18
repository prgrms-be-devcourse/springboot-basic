package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.repository.voucher.rowmapper.VoucherResultSetRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final VoucherResultSetRowMapper voucherResultSetRowMapper;

    public VoucherJdbcRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        voucherResultSetRowMapper = new VoucherResultSetRowMapper();
    }

    @Override
    public VoucherInsertResultSet insert(VoucherInsertQuery query) {
        String insertionSql = "INSERT INTO voucher (voucher_id, voucher_type, discount_value) VALUES (:voucher_id, :voucher_type, :discount_value)";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_type", query.getVoucherClassSimpleName());
        params.put("voucher_id", query.getVoucherId());
        params.put("discount_value", query.getDiscountValue());

        return new VoucherInsertResultSet(namedParameterJdbcTemplate.update(insertionSql, params));
    }

    @Override
    public Optional<VoucherResultSet> findById(VoucherFindQuery query) {
        String selectionSql = "SELECT voucher_type, voucher_id, discount_value FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        try {
            VoucherResultSet resultSet = namedParameterJdbcTemplate.queryForObject(selectionSql,
                    params, voucherResultSetRowMapper);
            return Optional.ofNullable(resultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherResultSet> findAll() {
        String selectSql = "SELECT voucher_type, voucher_id, discount_value FROM voucher";
        return namedParameterJdbcTemplate.query(selectSql, voucherResultSetRowMapper);
    }

    @Override
    public void deleteById(VoucherDeleteQuery query) {
        String deleteSql = "DELETE FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        namedParameterJdbcTemplate.update(deleteSql, params);
    }
}
