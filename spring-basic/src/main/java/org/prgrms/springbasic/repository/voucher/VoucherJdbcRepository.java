package org.prgrms.springbasic.repository.voucher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.domain.wallet.Wallet;
import org.prgrms.springbasic.utils.exception.NoDatabaseChange;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_INSERTED;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_UPDATED;
import static org.prgrms.springbasic.utils.sql.VoucherSQL.*;

@Repository
@Profile("prd")
@Slf4j
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Voucher save(Voucher voucher) {
        var insert = jdbcTemplate.update(CREATE_VOUCHER.getQuery(), toParamMap(voucher));

        if(insert != 1) {
            log.error("Got not inserted result: {}", voucher);

            throw new NoDatabaseChange(NOT_INSERTED.getMessage());
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_VOUCHER_ID.getQuery(),
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_CUSTOMER_ID.getQuery(),
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findVouchers() {
        return jdbcTemplate.query(SELECT_VOUCHERS.getQuery(), voucherRowMapper);
    }

    @Override
    public List<Wallet> findWallets() {
        return jdbcTemplate.query(SELECT_WALLETS.getQuery(), walletRowMapper);
    }

    @Override
    public int countData() {
        var count = jdbcTemplate.queryForObject(SELECT_COUNT.getQuery(),
                Collections.emptyMap(),
                Integer.class);

        return (count == null) ? 0 : count;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update(UPDATE_VOUCHER.getQuery(), toParamMap(voucher));

        if(update != 1) {
            log.error("Got not updated result: {}", voucher);

            throw new NoDatabaseChange(NOT_UPDATED.getMessage());
        }

        return voucher;
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        jdbcTemplate.update(DELETE_VOUCHERS.getQuery(), Collections.singletonMap("voucherId", voucherId));
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE_VOUCHERS.getQuery(), Collections.singletonMap("customerId", customerId));
    }

    @Override
    public void deleteVouchers() {
        jdbcTemplate.update(DELETE_VOUCHERS.getQuery(), Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>(){
            {
                put("voucherId", voucher.getVoucherId().toString().getBytes());
                put("voucherType", voucher.getVoucherType().toString());
                put("discountInfo", voucher.getDiscountInfo());
                put("createdAt", voucher.getCreatedAt());
                put("modifiedAt", voucher.getModifiedAt() == null ? null : Timestamp.valueOf(voucher.getModifiedAt()));
                put("customerId", voucher.getCustomerId() == null ? null : voucher.getCustomerId().toString().getBytes());
            }
        };
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type").toUpperCase());
        var discountInfo = resultSet.getLong("discount_info");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var modifiedAt = resultSet.getTimestamp("modified_at") == null ?
                null : resultSet.getTimestamp("modified_at").toLocalDateTime();
        var customerId = resultSet.getBytes("customer_id") == null ? null : toUUID(resultSet.getBytes("customer_id"));

        return new Voucher(voucherId, voucherType, discountInfo, createdAt, modifiedAt, customerId);
    };

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        var name = resultSet.getString("name");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var discountInfo = resultSet.getLong("discount_info");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return Wallet.builder().customerId(customerId).customerType(customerType)
                .name(name).voucherId(voucherId).voucherType(voucherType)
                .discountInfo(discountInfo).createdAt(createdAt).build();
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
