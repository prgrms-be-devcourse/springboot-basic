package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerRepository;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    public static final String INSERT_WALLET_SQL = "INSERT INTO voucher_wallet(wallet_id, voucher_id, customer_id) VALUES (:walletId, :voucherId, :customerId)";
    public static final String FIND_VOUCHER_BY_CUSTOMER_SQL = "SELECT v.voucher_id, v.voucher_type ,v.amount, v.created_at FROM voucher_wallet w INNER JOIN voucher v ON v.voucher_id = w.voucher_id WHERE w.customer_id=:customerId";
    public static final String DELETE_SQL = "DELETE FROM voucher_wallet WHERE wallet_id=:walletId";
    public static final String FIND_CUSTOMER_BY_VOUCHER_SQL = "SELECT c.customer_id, c.name, c.email, c.created_at FROM voucher_wallet w INNER JOIN customer c ON c.customer_id = w.customer_id WHERE w.voucher_id=:voucherId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wallet wallet) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("walletId", wallet.getWalletId());
        paramMap.put("voucherId", wallet.getVoucherId());
        paramMap.put("customerId", wallet.getCustomerId());
        jdbcTemplate.update(INSERT_WALLET_SQL, paramMap);
    }

    @Override
    public List<Voucher> findVoucherByCustomer(Customer customer) {
        return jdbcTemplate.query(FIND_VOUCHER_BY_CUSTOMER_SQL,
                Collections.singletonMap("customerId", customer.getCustomerId()),
                (rs, rowNum) -> {
                    UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
                    VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
                    int amount = rs.getInt("amount");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    return voucherType.constructor(voucherId, amount, createdAt);
                });
    }

    @Override
    public void remove(UUID walletId) {
        jdbcTemplate.update(DELETE_SQL, Collections.singletonMap("walletId", walletId));
    }

    @Override
    public List<Customer> findCustomerByVoucher(Voucher voucher) {
        return jdbcTemplate.query(FIND_CUSTOMER_BY_VOUCHER_SQL, Collections.singletonMap("voucherId", voucher.getVoucherId()), new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                String name = rs.getString("name");
                String email = rs.getString("email");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                return new Customer(customerId, name, email, createdAt);
            }
        });
    }

    public void clear() {
        jdbcTemplate.update("DELETE FROM voucher_wallet", Collections.emptyMap());
    }
}
