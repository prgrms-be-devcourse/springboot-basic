package org.prgrms.springorder.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = ((rs, rowNum) -> {

        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        long amount = rs.getLong("amount");
        VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

        String customerIdStr = rs.getString("customer_id");
        UUID customerId = customerIdStr == null ? null : UUID.fromString(customerIdStr);
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return VoucherFactory.toVoucher(voucherType, voucherId, amount, customerId,
            createdAt);
    });

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString());
            put("amount", voucher.getAmount());
            put("voucherType", voucher.getVoucherType().getType());
            put("customerId", voucher.getCustomerId() == null ? null : voucher.getCustomerId().toString());
            put("createdAt", voucher.getCreatedAt());
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher findVoucher = jdbcTemplate.queryForObject(VoucherSQL.FIND_BY_ID.getSql(),
                Collections.singletonMap("voucherId", voucherId.toString()),
                voucherRowMapper);

            return Optional.ofNullable(findVoucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            jdbcTemplate.update(VoucherSQL.INSERT.getSql(), toParamMap(voucher));

            return voucher;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }

    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(VoucherSQL.FIND_ALL.getSql(), voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(VoucherSQL.DELETE_ALL.getSql(), Collections.emptyMap());
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(VoucherSQL.UPDATE_BY_ID.getSql(), toParamMap(voucher));
        return voucher;
    }

    @Override
    public Optional<CustomerWithVoucher> findByIdWithCustomer(UUID voucherId) {

        try {
            CustomerWithVoucher customerWithVoucher
                = jdbcTemplate.queryForObject(VoucherSQL.FIND_BY_ID_WITH_CUSTOMER.getSql(),
                Collections.singletonMap("voucherId", voucherId.toString()),
                (rs, rowNum) -> {
                    UUID findVoucherId = UUID.fromString(rs.getString("voucher_id"));
                    long amount = rs.getLong("amount");
                    VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

                    LocalDateTime voucherCreatedAt = rs.getTimestamp("created_at")
                        .toLocalDateTime();

                    UUID customerId = UUID.fromString(rs.getString("customer_id"));

                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    CustomerStatus customerStatus = CustomerStatus.of(
                        rs.getString("customer_status"));

                    return new CustomerWithVoucher(findVoucherId, amount, voucherCreatedAt,
                        voucherType, customerId, name, email, customerStatus);
                });
            return Optional.ofNullable(customerWithVoucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(VoucherSQL.DELETE_BY_ID.getSql(),
            Collections.singletonMap("voucherId", voucherId.toString()));
    }

}
