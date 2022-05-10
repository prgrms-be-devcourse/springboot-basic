package com.kdt.commandLineApp.service.voucherWallet;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i)->{
        try {
            long customerId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            int age = Optional.ofNullable(resultSet.getInt("age")).orElseThrow(()-> new WrongCustomerParamsException());
            return new Customer(customerId, name, age, sex);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("id");
        String type = resultSet.getString("type");
        int amount = resultSet.getInt("amount");

        try {
            return new Voucher(voucherId, type, amount);
        }
        catch (WrongVoucherParamsException e) {
            e.printStackTrace();
            return null;
        }
    };

    @Autowired
    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void giveVoucherToCustomer(long customerId, long voucherId) {
        String sql = "insert into mysql.voucherWallet (customer_id, voucher_id) values (:customerId, :voucherId)";
        Map<String, Object> paramMap = new ConcurrentHashMap<>();

        paramMap.put("customerId", customerId);
        paramMap.put("voucherId", voucherId);

        namedParameterJdbcTemplate.update(
                sql,
                paramMap
        );
    }

    @Override
    public void deleteVoucherFromCustomer(long customerId, long voucherId) {
        String sql = "delete from mysql.voucherWallet where customer_id = :customerId and voucher_id = :voucherId";
        Map<String, Object> paramMap = new ConcurrentHashMap<>();
        paramMap.put("customerId",customerId);
        paramMap.put("voucherId",voucherId);

        namedParameterJdbcTemplate.update(
                sql,
                paramMap
        );
    }

    @Override
    public List<Voucher> getCustomerVouchers(long customerId) {
        String sql = "select * from mysql.voucher where id in (select voucher_id from mysql.voucherWallet where customer_id = :customerId)";

        return namedParameterJdbcTemplate.query(
                sql,
                Collections.singletonMap("customerId", customerId),
                voucherRowMapper
        );
    }

    @Override
    public List<Customer> getCustomersWithVoucherId(long voucherId) {
        String sql = "select * from mysql.customer where id in (select customer_id from mysql.voucherWallet where voucher_id = :voucherId)";

        return namedParameterJdbcTemplate.query(
                sql,
                Collections.singletonMap("voucherId", voucherId),
                customerRowMapper
        );
    }
}
