package org.prgrms.kdt.repository;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.util.IntUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherCustomerRowMapper = (resultSet, rowNum) -> {
        var discountAmount = resultSet.getInt("discount_amount");
        var voucherType = resultSet.getInt("voucher_type");
        var voucherId = IntUtils.toUUID(resultSet.getBytes("voucher_id"));
        var voucherCreatedAt = resultSet.getTimestamp("v.created_at").toLocalDateTime();
        UUID customerId = null;
        if(resultSet.getBytes("owner_id") != null) {
            customerId = IntUtils.toUUID(resultSet.getBytes("owner_id"));
        }
        String email = "";
        if(resultSet.getString("email") != null) {
            email = resultSet.getString("email");
        }
        LocalDateTime ownedAt = null;
        if(resultSet.getTimestamp("owned_time") != null) {
            ownedAt = resultSet.getTimestamp("owned_time").toLocalDateTime();
        }
        LocalDateTime customerCreatedAt = null;
        if(resultSet.getTimestamp("c.created_at") != null) {
            customerCreatedAt = resultSet.getTimestamp("c.created_at").toLocalDateTime();
        }
        String name = "";
        if(resultSet.getString("name") != null) {
            name = resultSet.getString("name");
        }
        LocalDateTime lastLoginAt = null;
        if(resultSet.getTimestamp("last_login_at") != null) {
            lastLoginAt = resultSet.getTimestamp("last_login_at").toLocalDateTime();
        }
        Customer customer = new Customer(customerId, name, email, lastLoginAt, customerCreatedAt);
        if (voucherType == 1) {
            return new FixedAmountVoucher(voucherId, discountAmount, customer, ownedAt, voucherCreatedAt);
        }
        return new PercentDiscountVoucher(voucherId, discountAmount, voucherCreatedAt, customer, ownedAt);
    };

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Voucher> selectJoinVoucherCustomer(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT v.*, c.* " +
                            "FROM vouchers v, customers c " +
                            "WHERE c.customer_id = v.owner_id AND voucher_id = UUID_TO_BIN(:voucherId)",
                    paramMap, voucherCustomerRowMapper));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Voucher> getVoucherByVoucherIdAndEmail(UUID voucherId, String email) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("email", email);
        }};
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT v.*, c.* " +
                            "FROM vouchers v, customers c " +
                            "WHERE c.customer_id = v.owner_id AND voucher_id = UUID_TO_BIN(:voucherId) AND email = :email",
                    paramMap, voucherCustomerRowMapper));
        } catch (Exception e) {
            OutputConsole.printMessage("WRONG : invalid input");
            return Optional.empty();
        }
    }

    public Map<UUID, Voucher> getVoucherListByCustomerId(String customerEmail) {
        var paramMap = new HashMap<String, Object>() {{
            put("email", customerEmail);
        }};
        return jdbcTemplate.query("SELECT c.*, v.* FROM customers c, vouchers v WHERE c.customer_id = v.owner_id AND c.email = :email",
                        paramMap, voucherCustomerRowMapper)
                .stream()
                .collect(Collectors.toMap(Voucher::getVoucherId, voucher -> voucher));
    }

    public List<Voucher> getVoucherWalletList() {
        return jdbcTemplate.query("SELECT * FROM vouchers v LEFT JOIN customers c ON c.customer_id = v.owner_id "
                , voucherCustomerRowMapper);

    }
}
