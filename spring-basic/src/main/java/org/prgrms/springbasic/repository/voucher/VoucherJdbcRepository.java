package org.prgrms.springbasic.repository.voucher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.domain.wallet.Wallet;
import org.prgrms.springbasic.utils.exception.NoDatabaseChangeException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.util.Collections.*;
import static java.util.Map.of;
import static java.util.Optional.empty;
import static org.prgrms.springbasic.utils.UUIDConverter.toUUID;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_INSERTED;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_UPDATED;
import static org.prgrms.springbasic.utils.sql.VoucherSQL.*;

@Slf4j
@Profile({"prd", "test"})
@Repository
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());

    @Override
    public Voucher save(Voucher voucher) {
        var insertedCount = jdbcTemplate.update(CREATE_VOUCHER.getQuery(), toParamMap(voucher));

        if(insertedCount != 1) {
            log.error("Got not inserted result: {}", voucher);

            throw new NoDatabaseChangeException(NOT_INSERTED.getMessage());
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_VOUCHER_ID.getQuery(),
                    singletonMap("voucherId",
                            voucherId.toString().getBytes()),
                                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());

            return empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query(SELECT_BY_CUSTOMER_ID.getQuery(),
                    singletonMap("customerId",
                            customerId.toString().getBytes()),
                                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());

            return emptyList();
        }
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        try {
            return jdbcTemplate.query(SELECT_BY_VOUCHER_TYPE.getQuery(),
                    singletonMap("voucherType",
                            voucherType.toString()),
                                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());

            return emptyList();
        }
    }

    @Override
    public List<Voucher> findByCreatedPeriod(String from, String to) {
        try {
            return jdbcTemplate.query(SELECT_BY_DATETIME.getQuery(),
                    of("from", from,
                            "to", to),
                            voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());

            return emptyList();
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
    public int countVouchers() {
        var count = jdbcTemplate.queryForObject(SELECT_COUNT.getQuery(), emptyMap(), Integer.class);

        return (count == null) ? 0 : count;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var updatedCount = jdbcTemplate.update(UPDATE_VOUCHER.getQuery(), toParamMap(voucher));

        if(updatedCount != 1) {
            log.error("Got not updated result: {}", voucher);

            throw new NoDatabaseChangeException(NOT_UPDATED.getMessage());
        }

        return voucher;
    }

    @Override
    public boolean deleteByVoucherId(UUID voucherId) {
        var deletedCount = jdbcTemplate.update(DELETE_BY_VOUCHER_ID.getQuery(),
                singletonMap("voucherId", voucherId.toString().getBytes()));

        return deletedCount == 1;
    }

    @Override
    public boolean deleteByCustomerId(UUID customerId) {
        var deletedCount = jdbcTemplate.update(DELETE_BY_CUSTOMER_ID.getQuery(),
                singletonMap("customerId", customerId.toString().getBytes()));

        return deletedCount == 1;
    }

    @Override
    public void deleteVouchers() {
        jdbcTemplate.update(DELETE_VOUCHERS.getQuery(), emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return objectMapper.convertValue(voucher, new TypeReference<HashMap<String, Object>>() {});
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
}
