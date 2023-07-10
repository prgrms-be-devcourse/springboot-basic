package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import org.springframework.context.annotation.Primary;
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
    private static final String INSERT_SQL = "INSERT INTO voucher(id, type, amount) VALUES(?, ?, ?)";
    private static final String FIND_ALL_SQL = "SELECT * FROM voucher";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM voucher WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE voucher SET type = ?, amount = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM voucher WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        jdbcTemplate.update(INSERT_SQL, voucher.getId().toString(), voucher.getType().name(), voucher.getAmount());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, voucherRowMapper(), voucherId.toString())
                .stream()
                .findFirst();
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_SQL, voucher.getType().name(), voucher.getAmount(), voucher.getId().toString());
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        jdbcTemplate.update(DELETE_SQL, voucherId.toString());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> new Voucher(
                UUID.fromString(rs.getString("id")),
                VoucherType.valueOf(rs.getString("type")),
                rs.getInt("amount"));
    }
}
