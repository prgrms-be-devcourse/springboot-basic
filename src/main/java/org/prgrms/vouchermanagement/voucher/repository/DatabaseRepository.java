package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.exception.LoadFailException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.prgrms.vouchermanagement.wallet.repository.WalletMapperJdbcMapperRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("database")
public class DatabaseRepository implements VoucherRepository {
    private static final String LOAD = "SELECT voucher_id, discount_policy, amount FROM voucher";
    private static final String CREATE = "INSERT INTO voucher(voucher_id, discount_policy, amount) VALUES(UUID_TO_BIN(?), (?), (?))";
    private static final String FIND_BY_ID = "SELECT voucher_id, discount_policy, amount FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String UPDATE_AMOUNT_BY_ID = "UPDATE voucher SET amount = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_BY_ID = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String FIND_BY_POLICY = "SELECT voucher_id, discount_policy, amount FROM voucher WHERE discount_policy = ?";

    private final JdbcTemplate jdbcTemplate;

    public DatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //create
    @Override
    public int create(UUID voucherId, DiscountPolicy discountPolicy) {
        String policy = String.valueOf(discountPolicy.getPolicyStatus());
        long amount = discountPolicy.getAmountOrPercent();

        return jdbcTemplate.update(CREATE, voucherId.toString().getBytes(), policy, amount);
    }

    //read
    @Override
    public List<Voucher> voucherLists() {
        return jdbcTemplate.query(LOAD, new VoucherRowMapper());
    }

    //update
    public void update(UUID voucherId, long amountOrPercent) {
        jdbcTemplate.update(UPDATE_AMOUNT_BY_ID, amountOrPercent, voucherId.toString().getBytes());
    }

    //delete
    @Override
    public int delete(UUID voucherId) {
        return jdbcTemplate.update(DELETE_BY_ID, voucherId.toString().getBytes());
    }

    //find
    @Override
    public Voucher findById(UUID voucherId) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{voucherId.toString().getBytes()}, new VoucherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new LoadFailException("찾을 수 없습니다.");
        }
    }

    @Override
    public List<Voucher> findVoucherByPolicy(PolicyStatus policy) {
        return jdbcTemplate.query(FIND_BY_POLICY, new VoucherRowMapper(), policy.name());
    }

    //load 역할
    //static -> toUUID때문에 x
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
