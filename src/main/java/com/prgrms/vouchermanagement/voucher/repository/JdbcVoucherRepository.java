package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("database")
public class JdbcVoucherRepository implements VoucherRepository {

    public static final String INSERT_SQL = "INSERT INTO voucher(voucher_id, voucher_type, amount, created_at) VALUES (:voucherId, :voucherType, :amount, :createdAt)";
    public static final String SELECT_SQL = "SELECT * FROM voucher";
    public static final String SELECT_BY_ID_SQL = "SELECT * FROM voucher WHERE voucher_id=:voucherId";
    public static final String UPDATE_SQL = "UPDATE voucher SET amount=:amount WHERE voucher_id=:voucherId";
    public static final String SELECT_BY_TYPE_SQL = "SELECT * FROM voucher WHERE voucher_type=:voucherType";
    public static final String SELECT_BY_PERIOD_SQL = "SELECT * FROM voucher WHERE created_at BETWEEN :from AND :end";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) throws DataAccessException {
        jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() throws DataAccessException {
        return jdbcTemplate.query(SELECT_SQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) throws DataAccessException {
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, Collections.singletonMap("voucherType", voucherType.toString()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end) throws DataAccessException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("from", from);
        paramMap.put("end", end);
        return jdbcTemplate.query(SELECT_BY_PERIOD_SQL, paramMap, voucherRowMapper);
    }

    @Override
    public void update(Voucher voucher) throws DataAccessException {
        jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
    }

    @Override
    public void remove(UUID voucherId) throws DataAccessException {
        jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id=:voucherId", Collections.singletonMap("voucherId", voucherId.toString()));
    }

    /**
     * 테스트에서 사용
     */
    public void clear() {
        jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString());
        paramMap.put("voucherType", VoucherType.getVoucherType(voucher).toString());
        paramMap.put("amount", voucher.getAmount());
        paramMap.put("createdAt", voucher.getCreatedAt());
        return paramMap;
    }

    private RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
        VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        int amount = rs.getInt("amount");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return voucherType.constructor(voucherId, amount, createdAt);
    };
}
