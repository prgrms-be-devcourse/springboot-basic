package org.prgrms.vouchermanagement.wallet.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.prgrms.vouchermanagement.wallet.domain.WalletMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletMapperJdbcMapperRepository implements WalletMapperRepository {

    private static final String CREATE = "INSERT INTO wallet(customer_id, voucher_id) VALUES(UUID_TO_BIN(?), UUID_TO_BIN(?))";
    private static final String FINDVOUCHERS = "select v.voucher_id, v.discount_policy, v.amount FROM voucher v JOIN wallet w ON v.voucher_id = w.voucher_id WHERE w.customer_id = UUID_TO_BIN(?)";
    private static final String DELETE = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(?)";
    private static final String FINDCUSTOMER = "SELECT c.customer_id, c.customer_name, c.customer_age FROM wallet w, customer c WHERE w.customer_id = c.customer_id AND w.voucher_id = UUID_TO_BIN(?)";
    private static final String FINDALL = "SELECT customer_id, voucher_id FROM wallet";

    private final JdbcTemplate jdbcTemplate;

    public WalletMapperJdbcMapperRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(WalletCreateInfo walletCreateInfo) {
        UUID customerId = walletCreateInfo.customerId();
        UUID voucherId = walletCreateInfo.voucherId();

        return jdbcTemplate.update(CREATE, customerId.toString().getBytes(), voucherId.toString().getBytes());
    }

    @Override
    public List<Voucher> findVouchers(UUID customerId) {
        return jdbcTemplate.query(FINDVOUCHERS, new Object[]{customerId.toString().getBytes()}, new VoucherRowMapper());
    }

    public List<WalletMapper> findAll() {
        return jdbcTemplate.query(FINDALL, new WalletRowMapper());
    }

    @Override
    public int delete(UUID customerId) {
        return jdbcTemplate.update(DELETE, customerId.toString().getBytes());
    }

    public Optional<Customer> findCustomer(UUID voucherId) {
        List<Customer> customers = jdbcTemplate.query(FINDCUSTOMER, new Object[]{voucherId.toString().getBytes()}, new CustomerRowMapper());
        return customers.stream().findFirst();
    }

    private class WalletRowMapper implements RowMapper<WalletMapper> {
        @Override
        public WalletMapper mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

            return new WalletMapper(customerId, voucherId);
        }
    }

    private class VoucherRowMapper implements RowMapper<Voucher> {
        @Override
        public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
            PolicyStatus policy = PolicyStatus.valueOf(resultSet.getString("discount_policy").toUpperCase());
            long amountOrPercent = resultSet.getLong("amount");

            DiscountPolicy discountPolicy = getPolicy(policy, amountOrPercent);

            return new Voucher(voucherId, discountPolicy);
        }
    }

    private class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            String customerName = resultSet.getString("customer_name");
            int customerAge = resultSet.getInt("customer_age");

            return new Customer(customerId, customerName, customerAge);
        }
    }

    private DiscountPolicy getPolicy(PolicyStatus policy, long amountOrPercent) {
        DiscountPolicy discountPolicy = null;

        if (policy == PolicyStatus.FIXED) {
            discountPolicy = new FixedAmountVoucher(amountOrPercent);
        } else if (policy == PolicyStatus.PERCENT) {
            discountPolicy = new PercentDiscountVoucher(amountOrPercent);
        }

        return discountPolicy;
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }
}
