package org.programers.vouchermanagement.voucher.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
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
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher (id, type, voucher_value) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                voucher.getId().toString(),
                voucher.getType().toString(),
                voucher.getPolicy().getValue());
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
        String sql = "update voucher set type = ?, voucher_value = ? where id = ?";
        jdbcTemplate.update(sql,
                voucher.getType().toString(),
                voucher.getPolicy().getValue(),
                voucher.getId().toString());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from voucher where id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            int value = rs.getInt("voucher_value");
            return type.createVoucher(UUID.fromString(rs.getString("id")), value);
        };
    }
}
