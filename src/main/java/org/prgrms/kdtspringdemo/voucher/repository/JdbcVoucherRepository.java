package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("DB")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var amount = Long.parseLong(resultSet.getString("amount"));
        var voucherType = resultSet.getString("voucher_type");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));

        var voucherTypeEnum = VoucherTypeFunction.findByCode(voucherType);
        return voucherTypeEnum.create(voucherId, amount);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Autowired
    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_type, amount) VALUES (UUID_TO_BIN(?), ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherPolicy().getVoucherType(),
                voucher.getVoucherPolicy().getAmount());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from voucher WHERE voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM voucher");
    }

    @Override
    public Optional<List<Voucher>> findAll() {
        return Optional.of(jdbcTemplate.query("select * from voucher", voucherRowMapper));
    }
}
