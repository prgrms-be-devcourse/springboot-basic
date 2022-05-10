package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.infra.Utils;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"default","test", "dev"})
public class JdbcVoucherRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, type, value, created_at) VALUES (UUID_TO_BIN(:voucherId), :type, :value, :createdAt)";
    private static final String UPDATE_VALUE_BY_ID_SQL = "UPDATE vouchers SET value = :value, type = :type WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE type = :type";
    private static final String SELECT_BY_DATE_SQL = "SELECT * FROM vouchers WHERE created_at between :start and :end ORDER BY created_at";
    private static final String SELECT_BY_DATE_TYPE_SQL = "SELECT * FROM vouchers WHERE (created_at between :start and :end) AND type = :type ORDER BY created_at";
    private static final String SELECT_COUNT_ALL_SQL = "SELECT COUNT(*) FROM vouchers";


    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = Utils.toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long value = resultSet.getLong("value");
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();
        if(type.equals("fix")) {
            return FixedAmountVoucher.builder()
                    .voucherId(voucherId)
                    .value(value)
                    .createdAt(createdAt)
                    .build();
        }
        else {
            return PercentDiscountVoucher.builder()
                    .voucherId(voucherId)
                    .value(value)
                    .createdAt(createdAt)
                    .build();
        }
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {

        int update = jdbcTemplate.update(INSERT_SQL, voucher.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {

        int update = jdbcTemplate.update(UPDATE_VALUE_BY_ID_SQL, voucher.toMap());
        if(update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_SQL, Collections.emptyMap(),Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(String type) {
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, Collections.singletonMap("type", type), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByDate(LocalDate start, LocalDate end) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("start", start);
            put("end", end);
        }};
        return jdbcTemplate.query(SELECT_BY_DATE_SQL, paramMap, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByDateAndType(LocalDate start, LocalDate end, String type) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("start", start);
            put("end", end);
            put("type", type);
        }};
        return jdbcTemplate.query(SELECT_BY_DATE_TYPE_SQL, paramMap, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info("JdbcVoucherRepository findById - 해당 데이터가 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Voucher delete(Voucher voucher) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, voucher.toMap());
        return voucher;
    }


}
