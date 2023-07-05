package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.kdt.JdbcCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
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
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, created_at, benefit) VALUES(UUID_TO_BIN(:voucherId), :voucherType, :createdAt, :benefit)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET benefit = :benefit WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_SQL = "DELETE * FROM vouchers";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, resultCount) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.of(resultSet.getString("voucher_type"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        Long benefit = resultSet.getLong("benefit");

        return new Voucher(voucherId, voucherType, benefit, createdAt);
    };

    public JdbcVoucherRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private MapSqlParameterSource toMapSqlParams(Voucher voucher) {
        return new MapSqlParameterSource().addValue("voucherId", voucher.getVoucherId().toString().getBytes())
            .addValue("voucherType", voucher.getVoucherType().toString())
            .addValue("createdAt", Timestamp.valueOf(voucher.getCreatedAt()))
            .addValue("benefit", voucher.getBenefit());
    }

    @Override
    public Optional<Voucher> findByID(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
            voucherRowMapper));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_SQL,
            toMapSqlParams(voucher));
        if(update != 1) {
            logger.error("insert error");
            throw new RuntimeException("Nothing was inserted!");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_SQL,
            toMapSqlParams(voucher));
        if(update != 1) {
            logger.error("update error");
            throw new RuntimeException("Nothing was updated!");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
