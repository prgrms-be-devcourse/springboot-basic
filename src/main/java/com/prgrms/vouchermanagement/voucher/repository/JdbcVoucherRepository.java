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

import static com.prgrms.vouchermanagement.voucher.repository.SelectCondition.*;

@Repository
@Profile("database")
public class JdbcVoucherRepository implements VoucherRepository {

    public static final String INSERT_SQL = "INSERT INTO voucher(voucher_type, amount, created_at) VALUES (:voucherType, :amount, :createdAt)";
    public static final String UPDATE_SQL = "UPDATE voucher SET amount=:amount WHERE voucher_id=:voucherId";;
    public static final String SELECT_BY_CUSTOMER = "SELECT v.voucher_id, v.voucher_type ,v.amount, v.created_at FROM voucher_wallet w INNER JOIN voucher v ON v.voucher_id = w.voucher_id WHERE w.customer_id=:customerId";

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
        return jdbcTemplate.query(selectQueryBuilder(), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(selectQueryBuilder(ID), Collections.singletonMap("voucherId", voucherId), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) throws DataAccessException {
        return jdbcTemplate.query(selectQueryBuilder(TYPE), Collections.singletonMap("voucherType", voucherType.toString()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end) throws DataAccessException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("from", from);
        paramMap.put("end", end);
        return jdbcTemplate.query(selectQueryBuilder(PERIOD), paramMap, voucherRowMapper);
    }

    @Override
    public List<Voucher> findVoucherByCustomer(Long customerId) throws DataAccessException {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER,
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

    private String selectQueryBuilder(SelectCondition ...conditions) {
        StringBuilder builder = new StringBuilder("SELECT * FROM voucher");

        if (conditions.length == 0) {
            return builder.toString();
        }

        builder.append(" WHERE ");

        for (int i = 0; i < conditions.length; i++) {
            SelectCondition condition = conditions[i];

            switch (condition) {
                case ID:
                    builder.append("voucher_id = :voucherId");
                    break;
                case TYPE:
                    builder.append(" WHERE voucher_type = :voucherType");
                    break;
                case PERIOD:
                    builder.append(" WHERE created_at BETWEEN :from AND :end");
                    break;
            }

            if (i < conditions.length-1) {
                builder.append(" & ");
            }
        }

        return builder.toString();
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
