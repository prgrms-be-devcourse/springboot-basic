package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;
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
    public void update(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        var update = jdbcTemplate.update("UPDATE voucher SET voucher_type = ?, amount = ? WHERE voucher_id = UUID_TO_BIN(?)",
                voucherRequestDto.getVoucherPolicy(),
                voucherRequestDto.getAmount(),
                voucherId.toString());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
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
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByPolicy(String policy) {
        return jdbcTemplate.query("select * from voucher where voucher_type = ?",
                voucherRowMapper,
                policy);
    }

    @Override
    public List<Voucher> findUnallocatedVoucher() {
        return jdbcTemplate.query("SELECT voucher.voucher_id, voucher.voucher_type, voucher.amount FROM voucher LEFT JOIN wallet_customer_voucher ON voucher.voucher_id = wallet_customer_voucher.voucher_id WHERE wallet_customer_voucher.voucher_id IS NULL",
                voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("delete from wallet_customer_voucher where voucher_id = UUID_TO_BIN(?)",
                voucherId.toString());
        jdbcTemplate.update("delete from voucher where voucher_id = UUID_TO_BIN(?)",
                voucherId.toString());
    }
}
