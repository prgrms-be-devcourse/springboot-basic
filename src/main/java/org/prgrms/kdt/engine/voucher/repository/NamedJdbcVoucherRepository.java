package org.prgrms.kdt.engine.voucher.repository;

import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class NamedJdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(NamedJdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var rate = resultSet.getLong("rate");
        var type = VoucherType.valueOf(resultSet.getString("type"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return type.createVoucher(voucherId, rate, createdAt);
    };

    public NamedJdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                VoucherSql.SELECT_BY_ID.getSql(),
                Map.of("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Map<UUID, Voucher>> getAll() {
        Map<UUID, Voucher> vouchers = new HashMap<>();
        try {
            var voucherList = jdbcTemplate.query(VoucherSql.SELECT_ALL.getSql(), voucherRowMapper);
            voucherList.forEach((voucher) -> vouchers.put(voucher.getVoucherId(), voucher));
            return Optional.of(vouchers);
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var insert = jdbcTemplate.update(VoucherSql.INSERT.getSql(), toParamMap(voucher));
        if (insert != 1) throw new RuntimeException("Nothing was inserted");
        return voucher;
    }

    @Override
    public void setCustomerId(UUID voucherId, UUID customerId) {
        var update = jdbcTemplate.update(VoucherSql.UPDATE_CUSTOMER_ID.getSql(), toParamMap(voucherId, customerId));
        if (update != 1) throw new RuntimeException("Nothing was updated");
    }

    @Override
    public Optional<Map<UUID, Voucher>> getCustomerVoucher(UUID customerId) {
        Map<UUID, Voucher> vouchers = new HashMap<>();
        try {
            var voucherList = jdbcTemplate.query(
                VoucherSql.SELECT_CUSTOMER_VOUCHERS.getSql(),
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);
            voucherList.forEach((voucher) -> vouchers.put(voucher.getVoucherId(), voucher));
            return Optional.of(vouchers);
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId) {
        jdbcTemplate.update(
            VoucherSql.DELETE_CUSTOMER_VOUCHER.getSql(),
            Collections.singletonMap("customerId", customerId.toString().getBytes())
        );
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        jdbcTemplate.update(
            VoucherSql.DELETE_VOUCHER.getSql(),
            Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );
    }

    @Override
    public Optional<UUID> findCustomerByVoucher(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                VoucherSql.SELECT_CUSTOMERID_BY_VOUCHERID.getSql(),
                Collections.singletonMap("voucherId", voucherId),
                UUID.class));
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        try {
            return jdbcTemplate.query(
                VoucherSql.SELECT_VOUCHERS_BY_TYPE.getSql(),
                Collections.singletonMap("type", type.toString()),
                voucherRowMapper);
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Voucher> findByCreatedDate(LocalDateTime createdDate) {
        try {
            return jdbcTemplate.query(
                VoucherSql.SELECT_VOUCHERS_BY_CREATED_DATE.getSql(),
                Collections.singletonMap("createdDate", createdDate),
                voucherRowMapper);
        } catch (DataAccessException e) {
            logger.error("Got Data Access Error", e);
            return Collections.emptyList();
        }
    }

    public void deleteAll() {
        jdbcTemplate.update(VoucherSql.DELETE_ALL.getSql(), Collections.emptyMap());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("rate", voucher.getVoucherRate());
        paramMap.put("type", voucher.getVoucherType().toString());
        paramMap.put("created_at", voucher.getVoucherCreatedAt());
        return paramMap;
    }

    private Map<String, Object> toParamMap(UUID voucherId, UUID customerId) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucherId.toString().getBytes());
        paramMap.put("customerId", customerId.toString().getBytes());
        return paramMap;
    }
}
