package com.prgrms.management.voucher.repository;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.config.exception.NotExistException;
import com.prgrms.management.util.ToUUID;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.prgrms.management.config.ErrorMessageType.NOT_EXECUTE_QUERY;
import static com.prgrms.management.config.ErrorMessageType.NOT_EXIST_ID;

@Repository
@Profile({"jdbc", "test"})
public class JdbcVoucherRepository implements VoucherRepository {
    private final String SAVE_SQL = "insert into vouchers(voucher_id, created_at, amount, voucher_type, customer_id) values (UUID_TO_BIN(?), ?, ?, ?, UUID_TO_BIN(?))";
    private final String FIND_ALL_SQL = "select * from vouchers";
    private final String FIND_CUSTOMER_BY_TYPE_SQL = "select * from vouchers where voucher_type = ?";
    private final String FIND_BY_ID_SQL = "select * from vouchers where voucher_id = UUID_TO_BIN(?)";
    private final String UPDATE_SQL = "update vouchers set customer_id = UUID_TO_BIN(?) where voucher_id = UUID_TO_BIN(?)";
    private final String DELETE_BY_ID_SQL = "delete from vouchers where voucher_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "delete from vouchers";
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update(SAVE_SQL,
                voucher.getVoucherId().toString().getBytes(),
                Timestamp.valueOf(voucher.getCreatedAt()),
                voucher.getAmount(),
                voucher.getVoucherType().toString(),
                voucher.getCustomerId() != null ? voucher.getCustomerId().toString().getBytes() : null
        );
        if (update != 1) {
            throw new IllegalStateException(NOT_EXECUTE_QUERY.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date) {
        return dynamicQueryByVoucherTypeAndCreatedAt(voucherType, date);
    }

    private List<Voucher> dynamicQueryByVoucherTypeAndCreatedAt(VoucherType voucherType, LocalDate date) {
        if (voucherType != null && date != null) {
            return jdbcTemplate.query("select * from vouchers where (voucher_type = ?) AND (DATE(created_at) = ?)", voucherRowMapper, voucherType.toString(), date);
        } else if (voucherType != null && date == null) {
            return jdbcTemplate.query("select * from vouchers where (voucher_type = ?) ", voucherRowMapper, voucherType.toString());
        } else if (voucherType == null && date != null) {
            return jdbcTemplate.query("select * from vouchers where DATE(created_at) = ?", voucherRowMapper, date);
        } else {
            return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
        }
    }

    @Override
    public List<UUID> findCustomerByVoucherType(VoucherType voucherType) {
        List<Voucher> query = jdbcTemplate.query(FIND_CUSTOMER_BY_TYPE_SQL,
                voucherRowMapper,
                voucherType.toString());
        return query.stream().map(Voucher::getVoucherId).collect(Collectors.toList());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_ID_SQL, voucherRowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("NotFoundException:{}", ErrorMessageType.NOT_EXIST_EXCEPTION.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void updateByCustomerId(UUID voucherId, UUID customerId) {
        int update = 0;
        try {
            update = jdbcTemplate.update(UPDATE_SQL, customerId.toString().getBytes(), voucherId.toString().getBytes());
        } catch (DataIntegrityViolationException e) {
            throw new NotExistException(NOT_EXIST_ID.getMessage());
        }
        if (update != 1) {
            throw new IllegalStateException(NOT_EXECUTE_QUERY.getMessage());
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, voucherId.toString().getBytes());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = ToUUID.toUUId(resultSet.getBytes("voucher_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var amount = resultSet.getLong("amount");
        var voucherType = VoucherType.of(resultSet.getString("voucher_type"));
        var customerId = resultSet.getBytes("customer_id") != null
                ? ToUUID.toUUId(resultSet.getBytes("customer_id")) : null;
        return voucherType.create(voucherId, createdAt, amount, voucherType, customerId);
    };
}
