package com.wonu606.vouchermanager.repository.voucher.reader;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.reader.rowmapper.VoucherReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoucherJdbcReader implements VoucherReader {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final VoucherReaderRowMapperManager rowMapperManager;

    public VoucherJdbcReader(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            VoucherReaderRowMapperManager rowMapperManager) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapperManager = rowMapperManager;
    }

    @Override
    public Optional<VoucherResultSet> findById(VoucherFindQuery query) {
        String selectionSql = "SELECT voucher_type, voucher_id, discount_value FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        try {
            VoucherResultSet resultSet = namedParameterJdbcTemplate.queryForObject(selectionSql,
                    params, rowMapperManager.getRowMapperForType(VoucherResultSet.class));
            return Optional.ofNullable(resultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherResultSet> findAll() {
        String selectSql = "SELECT voucher_type, voucher_id, discount_value FROM voucher";
        return namedParameterJdbcTemplate.query(selectSql,
                rowMapperManager.getRowMapperForType(VoucherResultSet.class));
    }
}
