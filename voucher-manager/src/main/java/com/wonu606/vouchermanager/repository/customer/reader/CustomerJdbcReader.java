package com.wonu606.vouchermanager.repository.customer.reader;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.rowmapper.CustomerResultSetRowMapper;
import java.util.List;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbcReader implements CustomerReader {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CustomerResultSetRowMapper rowMapper;

    public CustomerJdbcReader(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            CustomerResultSetRowMapper rowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<CustomerResultSet> findAll() {
        String selectionSql = "SELECT email, nickname FROM customer";
        return namedParameterJdbcTemplate.query(selectionSql, rowMapper);
    }
}
