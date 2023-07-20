package com.wonu606.vouchermanager.repository.customer.reader;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.rowmapper.CustomerResultSetRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbcReader implements CustomerReader {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CustomerResultSetRowMapper rowMapper;

    public CustomerJdbcReader(DataSource dataSource, CustomerResultSetRowMapper rowMapper) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
    }

    @Override
    public List<CustomerResultSet> findAll() {
        String selectionSql = "SELECT email, nickname FROM customer";
        return namedParameterJdbcTemplate.query(selectionSql, rowMapper);
    }
}
