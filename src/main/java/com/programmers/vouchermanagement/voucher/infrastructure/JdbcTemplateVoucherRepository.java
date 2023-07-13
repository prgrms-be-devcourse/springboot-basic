package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher (id, type, amount) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                voucher.getId().toString(),
                voucher.getDiscountPolicy().getType().toString(),
                voucher.getDiscountPolicy().getAmount());
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = "select * from voucher where id = ?";
        try {
            Voucher voucher = jdbcTemplate.queryForObject(sql, voucherRowMapper(), id.toString());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update voucher set type = ?, amount = ? where id = ?";
        jdbcTemplate.update(sql,
                voucher.getDiscountPolicy().getType().toString(),
                voucher.getDiscountPolicy().getAmount(),
                voucher.getId().toString());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from voucher where id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    public boolean existById(UUID id) {
        String sql = "select exists(select 1 from voucher where id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id.toString()));
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            DiscountType type = DiscountType.valueOf(rs.getString("type"));
            int amount = rs.getInt("amount");
            DiscountPolicy discountPolicy = type.createDiscountPolicy(amount);
            return new Voucher(id, discountPolicy);
        };
    }
}
