package com.prgrms.vouchermanagement.core.voucher.repository.jdbc;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        long amount = resultSet.getLong("amount");
        VoucherType voucherType = VoucherType.getType(resultSet.getString("voucher_type"));
        return new Voucher(id, name, amount, voucherType);
    };

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(id, name, amount, voucher_type) VALUES (?, ?, ?, ?)",
                voucher.getId(),
                voucher.getName(),
                voucher.getAmount(),
                voucher.getVoucherType().toString());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    @Override
    public Optional<Voucher> findById(String id) {
        List<Voucher> vouchers = jdbcTemplate.query("select * from vouchers where id = ?",
                voucherRowMapper,
                id);
        if (vouchers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(vouchers.get(0));
    }

    @Override
    public List<Voucher> findAllByIds(List<String> idList) {
        if (idList.isEmpty()) {
            return Collections.emptyList();
        }

        StringJoiner joiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < idList.size(); i++) {
            joiner.add("?");
        }

        String sql = "SELECT * FROM vouchers WHERE id IN " + joiner.toString();

        return jdbcTemplate.query(sql, idList.toArray(), voucherRowMapper);
    }
}
