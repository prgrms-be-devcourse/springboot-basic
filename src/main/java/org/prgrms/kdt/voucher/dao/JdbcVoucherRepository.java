package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Profile("operation")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.getTypeByStr(resultSet.getString("voucher_type"));
        DiscountPolicy discountPolicy = voucherType.createPolicy(resultSet.getDouble("amount"));
        return new Voucher(voucherId, voucherType, discountPolicy);
    };

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * forom vouchers WHERE voucher_id = UUID_To_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, amount) VALUES (UUID_TO_BIN(?), ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherType().getName(),
                voucher.getDiscountPolicy().getAmount());
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
