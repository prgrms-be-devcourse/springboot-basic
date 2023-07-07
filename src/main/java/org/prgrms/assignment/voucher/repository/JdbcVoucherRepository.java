package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherStatus;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.kdt.JdbcCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("dev")
@Profile("dev")
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, created_at, benefit, expire_date) VALUES(UUID_TO_BIN(:voucherId), :voucherType, :createdAt, :benefit, :expireDate)";
    private static final String INSERT_STATUS_SQL = "INSERT INTO voucher_history(voucher_id, voucher_status, recorded_time) VALUES(UUID_TO_BIN(:voucherId), :voucherStatus, :recordedTime)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET benefit = :benefit WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String DELETE_ALL_SQL = "DELETE * FROM vouchers";
    private static final String DELETE_BY_ID_SQL = "DELETE * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";

    private final DataClassRowMapper<VoucherEntity> voucherRowMapper = new DataClassRowMapper<>(VoucherEntity.class);
    private final DataClassRowMapper<VoucherHistoryEntity> voucherHistoryRowMapper = new DataClassRowMapper<>(VoucherHistoryEntity.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity, VoucherHistoryEntity voucherHistoryEntity) {
        int voucherUpdate = jdbcTemplate.update(INSERT_SQL,
            toMapSqlParams(voucherEntity));
        int historyUpdate = jdbcTemplate.update(INSERT_STATUS_SQL,
            toMapSqlParams(voucherHistoryEntity));
        if(voucherUpdate != 1 || historyUpdate != 1) {
            logger.error("insert error");
            throw new RuntimeException("Nothing was inserted!");
        }
        return voucherEntity;
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Optional<VoucherEntity> findByID(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
            voucherRowMapper));
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity, VoucherHistoryEntity voucherHistoryEntity) {
        int voucherUpdate = jdbcTemplate.update(UPDATE_SQL,
            toMapSqlParams(voucherEntity));
        int historyUpdate = jdbcTemplate.update(INSERT_STATUS_SQL,
            toMapSqlParams(voucherHistoryEntity));
        if(voucherUpdate != 1 || historyUpdate != 1) {
            logger.error("update error");
            throw new RuntimeException("Nothing was updated!");
        }
        return voucherEntity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public void delete(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL,
            Collections.singletonMap("voucher_id", voucherId.toString().getBytes()));
    }

    private MapSqlParameterSource toMapSqlParams(VoucherEntity voucherEntity) {
        return new MapSqlParameterSource().addValue("voucherId", voucherEntity.voucherId())
            .addValue("voucherType", voucherEntity.voucherType().toString())
            .addValue("createdAt", voucherEntity.createdAt())
            .addValue("benefit", voucherEntity.benefit())
            .addValue("expireDate", voucherEntity.expireDate())
            .addValue("recordedTime", LocalDateTime.now());
    }

    private MapSqlParameterSource toMapSqlParams(VoucherHistoryEntity voucherHistoryEntity) {
        return new MapSqlParameterSource().addValue("voucherId", voucherHistoryEntity.voucherId())
            .addValue("voucherStatus", voucherHistoryEntity.voucherStatus().toString())
            .addValue("recordedTime", voucherHistoryEntity.recordedTime());
    }
}
