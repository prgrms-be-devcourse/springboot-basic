package com.program.commandLine.repository;

import com.program.commandLine.model.VoucherWallet;
import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.customer.CustomerFactory;
import com.program.commandLine.model.customer.CustomerType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.toUUID;

@Component(value = "customerRepository")
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerFactory customerFactory;

    private static String sqlWallet = "select * from voucher_wallet WHERE customer_id = UUID_TO_BIN(:customerId) ";


    public CustomerJdbcRepository(DataSource dataSource, CustomerFactory customerFactory) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.customerFactory = customerFactory;
    }

    private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            CustomerType customerType = CustomerType.getType(rs.getString("type"));
            String customerName = rs.getString("name");
            UUID customerId = toUUID(rs.getBytes("customer_id"));
            String email = rs.getString("email");
            LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null ?
                    rs.getTimestamp("last_login_at").toLocalDateTime() : null;

            return customerFactory.createCustomer(customerType, customerId, customerName, email, lastLoginAt, new ArrayList<>());
        }
    };

    private final RowMapper<VoucherWallet> WalletRowMapper = new RowMapper<VoucherWallet>() {
        @Override
        public VoucherWallet mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            LocalDateTime createAt = rs.getTimestamp("create_at").toLocalDateTime();
            return new VoucherWallet(voucherId, createAt);
        }
    };


    private Map<String, Object> toCustomerParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("type", customer.getCustomerType().getString());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    private Map<String, Object> toWalletParamMap(UUID customerId, UUID voucherId) {
        return new HashMap<>() {{
            put("customerId", customerId.toString().getBytes());
            put("voucherId", voucherId.toString().getBytes());
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        String customerSql = "INSERT INTO customers(customer_id,type,name,email, last_login_at)" +
                "  VALUES (UUID_TO_BIN(:customerId),:type,:name,:email,:lastLoginAt)";
        String walletSql = "INSERT INTO customers(customer_id,type,name,email, last_login_at)" +
                "  VALUES (UUID_TO_BIN(:customerId),:type,:name,:email,:lastLoginAt)";

        int update = jdbcTemplate.update(customerSql, toCustomerParamMap(customer));
        customer.getVoucherWallets().forEach(wallet ->
                jdbcTemplate.update(walletSql, toWalletParamMap(customer.getCustomerId(), wallet.voucherId()))
        );
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return customer;
    }

    @Override
    public Customer lastLoginUpdate(Customer customer) {
        String sql = "UPDATE customers SET last_login_at= :lastLoginAt  WHERE customer_id = UUID_TO_BIN(:customerId)";
        int update = jdbcTemplate.update(sql, toCustomerParamMap(customer));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
        return customer;
    }

    @Override
    public int count() {
        String sql = "select count(*) from customers";
        return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String customerSql = "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)";
        try {
            Customer customer = jdbcTemplate.queryForObject(customerSql,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper);
            List<VoucherWallet> wallets = jdbcTemplate.query(sqlWallet,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), WalletRowMapper);
            wallets.forEach(wallet -> customer.getVoucherWallets().add(wallet));
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        String customerSql = "select * from customers WHERE name = :name";
        try {
            Customer customer = jdbcTemplate.queryForObject(customerSql,
                    Collections.singletonMap("name", name), customerRowMapper);
            List<VoucherWallet> wallets = jdbcTemplate.query(sqlWallet,
                    Collections.singletonMap("customerId", customer.getCustomerId().toString().getBytes()), WalletRowMapper);
            wallets.forEach(wallet -> customer.getVoucherWallets().add(wallet));
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String customerSql = "select * from customers WHERE email = :email";
        try {
            Customer customer = jdbcTemplate.queryForObject(customerSql,
                    Collections.singletonMap("email", email), customerRowMapper);
            List<VoucherWallet> wallets = jdbcTemplate.query(sqlWallet,
                    Collections.singletonMap("customerId", customer.getCustomerId().toString().getBytes()), WalletRowMapper);
            wallets.forEach(wallet -> customer.getVoucherWallets().add(wallet));
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        jdbcTemplate.getJdbcTemplate().update(sql);
    }
}
