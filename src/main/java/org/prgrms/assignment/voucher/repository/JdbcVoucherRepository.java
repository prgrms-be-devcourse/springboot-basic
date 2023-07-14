package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.entity.VoucherEntity;
import org.prgrms.assignment.voucher.entity.VoucherHistoryEntity;
import org.prgrms.assignment.voucher.model.VoucherStatus;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
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
    private static final String INSERT_HISTORY_SQL = "INSERT INTO voucher_history(voucher_id, history_id, voucher_status, recorded_time) VALUES(UUID_TO_BIN(:voucherId), UUID_TO_BIN(:historyId), :voucherStatus, :recordedTime)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET benefit = :benefit WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_HISTORY_BY_ID_SQL = "SELECT * FROM voucher_history WHERE history_id = UUID_TO_BIN(:historyId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_ALL_COUNT_SQL = "select count(*) from vouchers";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE voucher_type = :voucherType";

    private static final RowMapper<VoucherEntity> voucherRowMapper = (resultSet, rowNumber) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.of(resultSet.getString("voucher_type"));
        Long benefit = resultSet.getLong("benefit");
        LocalDateTime expireDate = resultSet.getTimestamp("expire_date").toLocalDateTime();
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new VoucherEntity(voucherId, voucherType, createdAt, benefit, expireDate);
    };

    private static final RowMapper<VoucherHistoryEntity> voucherHistoryRowMapper = (resultSet, rowNumber) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID historyId = toUUID(resultSet.getBytes("history_id"));
        VoucherStatus voucherStatus = VoucherStatus.valueOf(resultSet.getString("voucher_status"));
        LocalDateTime recordedTime = resultSet.getTimestamp("recorded_time").toLocalDateTime();

        return new VoucherHistoryEntity(voucherId, historyId, voucherStatus, recordedTime);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(VoucherEntity voucherEntity, VoucherHistoryEntity voucherHistoryEntity) {
        int entityUpdate = jdbcTemplate.update(INSERT_SQL,
            toMapSqlParams(voucherEntity));
        int historyUpdate = jdbcTemplate.update(INSERT_HISTORY_SQL,
            toMapSqlParams(voucherHistoryEntity));
        if (entityUpdate != 1 || historyUpdate != 1) {
            logger.error("insert error");
            throw new RuntimeException("Nothing was inserted!");
        }
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Optional<VoucherEntity> findVoucherEntityById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
            Collections.singletonMap("voucherId", voucherId.toString()),
            voucherRowMapper));
    }

    @Override
    public Optional<VoucherHistoryEntity> findVoucherHistoryEntityById(UUID historyId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_HISTORY_BY_ID_SQL,
            Collections.singletonMap("historyId", historyId.toString()),
            voucherHistoryRowMapper));
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity, VoucherHistoryEntity voucherHistoryEntity) {
        int entityUpdate = jdbcTemplate.update(UPDATE_SQL,
            toMapSqlParams(voucherEntity));
        int historyUpdate = jdbcTemplate.update(INSERT_HISTORY_SQL,
            toMapSqlParams(voucherHistoryEntity));
        if(entityUpdate != 1 || historyUpdate != 1) {
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
            Collections.singletonMap("voucherId", voucherId.toString()));
    }

    @Override
    public List<VoucherEntity> findVouchersByType(VoucherType voucherType) {
        return jdbcTemplate.query(
            FIND_BY_TYPE_SQL,
            Collections.singletonMap("voucherType", voucherType.toString()),
            voucherRowMapper
        );
    }

    public int count() {
        return jdbcTemplate.queryForObject(SELECT_ALL_COUNT_SQL, Collections.emptyMap(), Integer.class);
    }

    private MapSqlParameterSource toMapSqlParams(VoucherEntity voucherEntity) {
        return new MapSqlParameterSource().addValue("voucherId", voucherEntity.voucherId().toString())
            .addValue("voucherType", voucherEntity.voucherType().toString())
            .addValue("createdAt", voucherEntity.createdAt())
            .addValue("benefit", voucherEntity.benefit())
            .addValue("expireDate", voucherEntity.expireDate())
            .addValue("recordedTime", LocalDateTime.now());
    }

    private MapSqlParameterSource toMapSqlParams(VoucherHistoryEntity voucherHistoryEntity) {
        return new MapSqlParameterSource().addValue("voucherId", voucherHistoryEntity.voucherId().toString())
            .addValue("historyId", voucherHistoryEntity.historyId().toString())
            .addValue("voucherStatus", voucherHistoryEntity.voucherStatus().toString())
            .addValue("recordedTime", voucherHistoryEntity.recordedTime());
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
