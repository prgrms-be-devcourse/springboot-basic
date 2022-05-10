package com.kdt.commandLineApp.voucherWallet;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import com.kdt.commandLineApp.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.kdt.commandLineApp.UUIDConverter.toUUID;

@Repository
@Profile("db")
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i)->{
        try {
            UUID customerId = toUUID(resultSet.getBytes("cid"));
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
        UUID voucherId = toUUID(resultSet.getBytes("vid"));
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
    public void giveVoucherToCustomer(String customerId, String voucherId) {
        String sql = "insert into mysql.voucherWallet (cid, vid) values (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))";
        Map<String, Object> paramMap = new ConcurrentHashMap<>();

        paramMap.put("customerId", customerId.getBytes());
        paramMap.put("voucherId",voucherId.getBytes());

        namedParameterJdbcTemplate.update(
                sql,
                paramMap
        );
    }

    @Override
    public void deleteVoucherFromCustomer(String customerId, String voucherId) {
        String sql = "delete from mysql.voucherWallet where cid = UUID_TO_BIN(:customerId) and vid = UUID_TO_BIN(:voucherId)";
        Map<String, Object> paramMap = new ConcurrentHashMap<>();
        paramMap.put("customerId",customerId.getBytes());
        paramMap.put("voucherId",voucherId.getBytes());

        namedParameterJdbcTemplate.update(
                sql,
                paramMap
        );
    }

    @Override
    public List<Voucher> getCustomerVouchers(String customerId) {
        String sql = "select * from mysql.voucher where vid in (select vid from mysql.voucherWallet where cid = UUID_TO_BIN(:customerId))";

        return namedParameterJdbcTemplate.query(
                sql,
                Collections.singletonMap("customerId", customerId.getBytes()),
                voucherRowMapper
        );
    }

    @Override
    public List<Customer> getCustomersWithVoucherId(String voucherId) {
        String sql = "select * from mysql.customer where cid in (select cid from mysql.voucherWallet where vid = UUID_TO_BIN(:voucherId))";

        return namedParameterJdbcTemplate.query(
                sql,
                Collections.singletonMap("voucherId", voucherId.getBytes()),
                customerRowMapper
        );
    }
}
