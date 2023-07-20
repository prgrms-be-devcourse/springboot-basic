package com.wonu606.vouchermanager.repository.customer.reader;

import com.wonu606.vouchermanager.repository.customer.reader.rowmapper.CustomerReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import java.util.List;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbcReader implements CustomerReader {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CustomerReaderRowMapperManager rowMapperManager;

    public CustomerJdbcReader(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            CustomerReaderRowMapperManager rowMapperManager) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapperManager = rowMapperManager;
    }

    @Override
    public List<CustomerResultSet> findAll() {
        String selectionSql = "SELECT email, nickname FROM customer";
        return namedParameterJdbcTemplate.query(selectionSql,
                rowMapperManager.getRowMapperForType(CustomerResultSet.class));
    }
}
