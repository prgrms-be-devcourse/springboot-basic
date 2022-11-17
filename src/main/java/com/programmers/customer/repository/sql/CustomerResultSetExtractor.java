package com.programmers.customer.repository.sql;

import com.programmers.customer.Customer;
import com.programmers.voucher.repository.sql.VoucherRowMapper;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerResultSetExtractor implements ResultSetExtractor<Customer> {
    private final CustomerRowMapper customerRowMapper;
    private final VoucherRowMapper voucherRowMapper;

    public CustomerResultSetExtractor(CustomerRowMapper customerRowMapper, VoucherRowMapper voucherRowMapper) {
        this.customerRowMapper = customerRowMapper;
        this.voucherRowMapper = voucherRowMapper;
    }

    @Override
    public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
        Customer customer = null;
        int row = 0;

        while (rs.next()) {
            if (customer == null) {
                customer = customerRowMapper.mapRow(rs, row);
            }

            if (rs.getBytes("voucher_id") == null) {
                return customer;
            }

            Voucher voucher = voucherRowMapper.mapRow(rs, row);
            customer.getWallet().add(voucher);
            row++;
        }
        return customer;
    }
}
