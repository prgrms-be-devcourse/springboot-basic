package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.VoucherQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final VoucherQuery voucherQuery;
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(VoucherQuery voucherQuery, JdbcTemplate jdbcTemplate) {
        this.voucherQuery = voucherQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void loadVouchers() {
        log.debug("Called JdbcVoucherRepository's loadVouchers method.");
    }

    @Override
    public void persistVouchers() {
        log.debug("Called JdbcVoucherRepository's PersistVouchers method.");
    }

    @Override
    public Voucher save(Voucher voucher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(c -> {
                PreparedStatement statement = c.prepareStatement(voucherQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, voucher.getName());
                statement.setString(2, voucher.getDiscountPolicy().getType().toString());
                statement.setInt(3, voucher.getDiscountPolicy().getAmount());
                statement.setLong(4, voucher.getCustomerId());
                return statement;
            }, keyHolder);
            voucher.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            log.debug("Saved voucher({}) to repository.", voucher);
        } catch (DataAccessException ex) {
            voucher.setId(-1);
            log.error("DataAccessException occur on saving voucher({}) to repository: {}", voucher, ex.getMessage());
        }

        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        log.debug("Find all vouchers");
        return jdbcTemplate.query(voucherQuery.getSelect().getAll(), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(long id) {
        log.debug("Find voucher by id: {}", id);
        Voucher voucher;
        try {
            voucher = jdbcTemplate.queryForObject(voucherQuery.getSelect().getById(), voucherRowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            voucher = null;
        } // based on https://stackoverflow.com/questions/18503607/best-practice-to-select-data-using-spring-jdbctemplate

        return Optional.ofNullable(voucher);
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(voucherQuery.getUpdate().getById(),
                voucher.getName(),
                voucher.getDiscountPolicy().getType().toString(),
                voucher.getDiscountPolicy().getAmount(),
                voucher.getCustomerId(),
                voucher.getId());
        return voucher;
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(voucherQuery.getDelete().getById(), id);
    }

    @Override
    public List<Voucher> findAllByCustomer(long customerId) {
        log.debug("Find vouchers by customer(id: {})", customerId);
        return jdbcTemplate.query(voucherQuery.getSelect().getByCustomer(), voucherRowMapper, customerId);
    }

    private static RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> new Voucher(
            rs.getLong("voucher_id"),
            rs.getString("name"),
            new DiscountPolicy(rs.getInt("value"), DiscountPolicy.Type.of(rs.getString("type"))),
            rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
            rs.getLong("customer_id"));
}
