package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.*;
import org.springframework.context.annotation.Profile;
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

    private final String LOAD = "SELECT voucher_id, discount_policy, amount FROM voucher";
    private final String CREATE = "INSERT INTO voucher(voucher_id, discount_policy, amount) VALUES(UUID_TO_BIN(?), (?), (?))";
    private final String UPDATE_AMOUNT_BY_ID = "UPDATE voucher SET amount = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private final String DELETE_BY_ID = "DELETE FROM voucher";

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final JdbcTemplate jdbcTemplate;

    public DatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        load();
    }

    //create
    @Override
    public int create(UUID voucherId, DiscountPolicy discountPolicy) {
        String policy = String.valueOf(discountPolicy.getPolicyStatus());
        long amount = discountPolicy.getAmountOrPercent();

        int rowsAffected = jdbcTemplate.update(CREATE, voucherId.toString().getBytes(), policy, amount);

        Voucher voucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, voucher);

        return rowsAffected;
    }

    //read
    @Override
    public List<Voucher> voucherLists() {
        return storage.values().stream()
                .toList();
    }

    //update
    public void update(UUID voucherId, long amountOrPercent) {
        jdbcTemplate.update(UPDATE_AMOUNT_BY_ID, amountOrPercent, voucherId.toString().getBytes());

        Voucher findVoucher = storage.get(voucherId);
        PolicyStatus policy = findVoucher.getDiscountPolicy().getPolicyStatus();

        DiscountPolicy discountPolicy = getPolicy(policy, amountOrPercent);

        Voucher updateVoucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, updateVoucher);
    }

    //delete
    @Override
    public int deleteAll() {
        int rowsAffected = jdbcTemplate.update(DELETE_BY_ID);
        storage.clear();
        return rowsAffected;
    }

    @Override
    public Voucher getById(UUID voucherId) {
        return storage.get(voucherId);
    }

    private void load() {
        List<Voucher> vouchers = jdbcTemplate.query(LOAD, new VoucherRowMapper());
        for (Voucher voucher : vouchers) {
            storage.put(voucher.getVoucherId(), voucher);
        }
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
