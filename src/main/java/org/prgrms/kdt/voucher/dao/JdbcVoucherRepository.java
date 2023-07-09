package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.exception.NotUpdateException;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Profile({"default", "test"})
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = UUID.fromString(resultSet.getString("id"));
        VoucherType voucherType = VoucherType.getTypeByStr(resultSet.getString("type"));
        DiscountPolicy discountPolicy = voucherType.createPolicy(resultSet.getDouble("amount"));
        return new Voucher(voucherId, voucherType, discountPolicy);
    };

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    voucherRowMapper,
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO voucher(id, type, amount) VALUES (?, ?, ?)";
        int update = jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getVoucherType().getName(),
                voucher.getDiscountPolicy().getAmount());
        if (update != 1) {
            throw new NotUpdateException("insert가 제대로 이루어지지 않았습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }
}
