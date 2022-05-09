package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("database")
public class JdbcVoucherRepository implements VoucherRepository {

    public static final String INSERT_SQL = "INSERT INTO voucher(voucher_type, amount, created_at) VALUES (:voucherType, :amount, :createdAt)";
    public static final String SELECT_SQL = "SELECT * FROM voucher";
    public static final String SELECT_BY_ID_SQL = "SELECT * FROM voucher WHERE voucher_id=:voucherId";
    public static final String UPDATE_SQL = "UPDATE voucher SET amount=:amount WHERE voucher_id=:voucherId";
    public static final String SELECT_BY_TYPE_SQL = "SELECT * FROM voucher WHERE voucher_type=:voucherType";
    public static final String SELECT_BY_PERIOD_SQL = "SELECT * FROM voucher WHERE created_at BETWEEN :from AND :end";
    public static final String FIND_VOUCHER_BY_CUSTOMER_SQL = "SELECT v.voucher_id, v.voucher_type ,v.amount, v.created_at FROM voucher_wallet w INNER JOIN voucher v ON v.voucher_id = w.voucher_id WHERE w.customer_id=:customerId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Voucher voucher) throws DataAccessException {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, toParameterSource(voucher), keyHolder, new String[]{"voucher_id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Voucher> findAll() throws DataAccessException {
        return jdbcTemplate.query(SELECT_SQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId), voucherRowMapper));
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
    public List<Voucher> findVoucherByCustomer(Long customerId) throws DataAccessException {
        return jdbcTemplate.query(FIND_VOUCHER_BY_CUSTOMER_SQL,
                Collections.singletonMap("customerId", customerId),
                voucherRowMapper);
    }

    @Override
    public void update(Voucher voucher) throws DataAccessException {
        jdbcTemplate.update(UPDATE_SQL, toParameterSource(voucher));
    }

    @Override
    public void remove(Long voucherId) throws DataAccessException {
        jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId));
    }

    /**
     * 테스트에서 사용
     */
    public void clear() {
        jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }

    private SqlParameterSource toParameterSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("voucherType", VoucherType.getVoucherType(voucher).toString())
                .addValue("amount", voucher.getAmount())
                .addValue("createdAt", voucher.getCreatedAt());
    }

    private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
        Long voucherId = rs.getLong("voucher_id");
        VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
        int amount = rs.getInt("amount");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return voucherType.constructor(voucherId, amount, createdAt);
    };
}
