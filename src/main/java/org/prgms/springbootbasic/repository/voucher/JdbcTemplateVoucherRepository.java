package org.prgms.springbootbasic.repository.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Primary
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private static final String[] VOUCHERS_COLUMNS = {"voucher_id", "voucher_type", "amount"};
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(JdbcTemplateVoucherRepository.class);

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .usingColumns(VOUCHERS_COLUMNS)
                .withTableName("VOUCHERS");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM VOUCHERS", voucherRowMapper());
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject
                    ("SELECT * FROM VOUCHERS WHERE VOUCHER_ID = :voucherId"
                            , Collections.singletonMap("voucherId", voucherId.toString())
                            , voucherRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query
                    ("SELECT * FROM VOUCHERS WHERE CUSTOMER_ID = :customerId"
                            , Collections.singletonMap("customerId", customerId.toString())
                            , voucherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            jdbcInsert.execute(new BeanPropertySqlParameterSource(voucher));
        } catch (DuplicateKeyException e) {
            logger.error("duplicated key exception occurred");
        }
        return voucher;
    }

    @Override
    public UUID deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM VOUCHERS WHERE VOUCHER_ID =:voucherId",
                Collections.singletonMap("voucherId", voucherId.toString()));
        return voucherId;
    }

    @Override
    public UUID deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update("DELETE FROM VOUCHERS WHERE CUSTOMER_ID =:customerId",
                Collections.singletonMap("customerId", customerId.toString()));
        return customerId;
    }

    @Override
    public Voucher updateByCustomerId(Voucher voucher) {
        jdbcTemplate.update("UPDATE VOUCHERS SET CUSTOMER_ID =:customerId WHERE VOUCHER_ID =:voucherId",
                objectMapper.convertValue(voucher, Map.class));
        return voucher;
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            VoucherType voucherType = VoucherType.findVoucherType(rs.getString(VOUCHERS_COLUMNS[1]));
            switch (voucherType) {
                case FIXED -> {
                    return new FixedAmountVoucher(UUID.fromString(rs.getString(VOUCHERS_COLUMNS[0])),
                            VoucherType.FIXED,
                            rs.getLong(VOUCHERS_COLUMNS[2]));
                }
                case PERCENT -> {
                    return new PercentDiscountVoucher(UUID.fromString(rs.getString(VOUCHERS_COLUMNS[0])),
                            VoucherType.PERCENT,
                            rs.getLong(VOUCHERS_COLUMNS[2]));

                }
                default -> throw new IllegalArgumentException("invalid voucher option");
            }
        };
    }
}
