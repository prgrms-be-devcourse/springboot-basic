package org.prgrms.spring_week1.Voucher.repository;

import static org.prgrms.spring_week1.jdbcUtils.toLocalDateTime;
import static org.prgrms.spring_week1.jdbcUtils.toUUID;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.FixedAmountVoucher;
import org.prgrms.spring_week1.Voucher.model.PercentDiscountVoucher;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherStatus;
import org.prgrms.spring_week1.Voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("type"));
        Long discount = resultSet.getLong("discount");
        VoucherStatus voucherStatus = VoucherStatus.valueOf(resultSet.getString("status"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        if (voucherType == VoucherType.FIXEDAMOUNT) {
            return new FixedAmountVoucher(voucherId, discount, voucherStatus, createdAt, updatedAt,
                voucherType, customerId);
        } else {
            return new PercentDiscountVoucher(voucherId, discount, voucherStatus, createdAt,
                updatedAt, voucherType, customerId);
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        objectHashMap.put("discount", voucher.getDiscount());
        objectHashMap.put("voucherType", voucher.getVoucherType().toString());
        objectHashMap.put("voucherStatus", voucher.getVoucherStatus().toString());
        objectHashMap.put("createdAt", voucher.getCreatedAt());
        objectHashMap.put("updatedAt", voucher.getUpdatedAt());
        objectHashMap.put("customerId", voucher.getCustomerId().toString().getBytes());

        return objectHashMap;

    }

    // CRUD

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            int insert = jdbcTemplate.update(
                "insert into Voucher(voucher_id, discount, status, type, created_at, updated_at, customer_id)"
                    +
                    " values(UUID_TO_BIN(:voucherId), :discount, :voucherStatus, :voucherType, :createdAt, :updatedAt, UUID_TO_BIN(:customerId))",
                toParamMap(voucher));
            if (insert != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
            return voucher;
        } catch (DataAccessException e) {
            logger.error("Get error during insert voucher : ", e);
            throw e;
        }
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return jdbcTemplate.query("select * from Voucher", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate
                .queryForObject("select * from Voucher where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result :", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        try {
            return jdbcTemplate
                .query("select * from Voucher where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId),
                    voucherRowMapper);
        } catch (DataAccessException e) {
            logger.error("Get error during find customer's voucher : ", e);
            throw e;
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        try {
            return jdbcTemplate.query("select * from Voucher where type = :voucherType",
                Collections.singletonMap("voucherType", voucherType.toString()), voucherRowMapper);
        } catch (DataAccessException e) {
            logger.error("Get error during find customer's voucher : ", e);
            throw e;
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        try {
            int update = jdbcTemplate.update("update Voucher "
                + "set discount = :discount, type = :voucherType, status = :voucherStatus, created_at = :createdAt, updated_at = :updatedAt "
                + "where voucher_id = UUID_TO_BIN(:voucherId)", toParamMap(voucher));
            if (update != 1) {
                throw new RuntimeException("Nothing was updated");
            }
            return voucher;
        } catch (DataAccessException e) {
            logger.error("Get error during insert voucher : ", e);
            throw e;
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("delete  from Voucher where voucher_id = UUID_TO_BIN(:voucherId)",
            Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }


}
