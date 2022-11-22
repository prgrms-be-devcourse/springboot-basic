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
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("voucherId", voucher.getVoucherId().toString());
        paramMap.put("amount", voucher.getAmount());
        paramMap.put("voucherType", voucher.getVoucherType().getType());
        paramMap.put("customerId", voucher.getCustomerId() == null ? null : voucher.getCustomerId().toString());
        paramMap.put("createdAt", voucher.getCreatedAt());

        return paramMap;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher findVoucher = jdbcTemplate.queryForObject(FIND_BY_ID,
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
            jdbcTemplate.update(INSERT, toParamMap(voucher));

            return voucher;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }

    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_BY_ID, toParamMap(voucher));
        return voucher;
    }

    @Override
    public Optional<CustomerWithVoucher> findByIdWithCustomer(UUID voucherId) {

        try {
            CustomerWithVoucher customerWithVoucher
                = jdbcTemplate.queryForObject(FIND_BY_ID_WITH_CUSTOMER,
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
        jdbcTemplate.update(DELETE_BY_ID,
            Collections.singletonMap("voucherId", voucherId.toString()));
    }


    private static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";

    private static final String FIND_ALL = "SELECT * FROM vouchers";

    private static final String DELETE_ALL = "DELETE FROM vouchers";

    private static final String DELETE_BY_ID = "DELETE FROM vouchers WHERE voucher_id = :voucherId";

    private static final String INSERT = "INSERT INTO vouchers(voucher_id, amount, voucher_type, customer_id, created_at) "
        + "VALUES (:voucherId, :amount, :voucherType, :customerId, :createdAt)";

    private static final String UPDATE_BY_ID = "UPDATE vouchers  "
        + "SET amount = :amount, voucher_type = :voucherType, customer_id = :customerId WHERE voucher_id = :voucherId";

    private static final String FIND_BY_ID_WITH_CUSTOMER = "SELECT "
        + " * "
        + " FROM vouchers v "
        + " INNER JOIN customers c ON v.customer_id = c.customer_id  "
        + " WHERE voucher_id = :voucherId";

}
