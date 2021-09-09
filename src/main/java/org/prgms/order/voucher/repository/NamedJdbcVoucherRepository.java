package org.prgms.order.voucher.repository;


import org.prgms.order.voucher.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;


@Primary
@Repository
@Profile({"default"})
public class NamedJdbcVoucherRepository implements VoucherRepository{
    private final Logger logger = LoggerFactory.getLogger(NamedJdbcVoucherRepository.class); //this.getClass()

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public NamedJdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var type = VoucherIndexType.indexToType(resultSet.getInt("type"));
        var amount = resultSet.getInt("amount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var expiredAt = resultSet.getTimestamp("expired_at") != null ?
                resultSet.getTimestamp("expired_at").toLocalDateTime() : null;
        return new VoucherCreateStretage().createVoucher(type, new VoucherData(voucherId, amount, createdAt, expiredAt));
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Voucher voucher){
        return new HashMap<>() {{
            put("voucher_id", voucher.getVoucherId() != null?voucher.getVoucherId().toString().getBytes() : null);
            put("type", voucher.getType().ordinal());
            put("amount", voucher.getAmount());
            put("created_at", voucher.getCreatedAt());
            put("expired_at", voucher.getExpiredAt() != null ? Timestamp.valueOf(voucher.getExpiredAt()) : null);
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UNHEX(REPLACE(:voucher_id,'-',''))",
                    Collections.singletonMap("voucher_id",voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherIndexType type) {
        try {
            return jdbcTemplate.query("select * from vouchers where type = :type",
                    Collections.singletonMap("type",type.ordinal()),
                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public List<Voucher> findByTypeAmount(VoucherIndexType type, long amount) {
        try {
            return jdbcTemplate.query("select * from vouchers where type = :type and amount = :amount",
                    toParamMap(new VoucherCreateStretage().createVoucher(type,new VoucherData(null, amount))),
                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public List<Voucher> findAvailable() {
        try {
            return jdbcTemplate.query("select * from vouchers where expired_at > LOCALTIME",
                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("insert into vouchers(voucher_id, type, amount, created_at, expired_at) values " +
                        "(UNHEX(REPLACE(:voucher_id,'-','')), :type, :amount, :created_at, :expired_at)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was insert");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update("update vouchers set type = :type, amount = :amount, created_at = :created_at, expired_at = :expired_at where voucher_id = UNHEX(REPLACE(:voucher_id,'-',''))",
                toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("delete from vouchers where voucher_id = UNHEX(REPLACE(:voucher_id,'-',''))",
                Collections.singletonMap("voucher_id",voucherId.toString().getBytes()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers",Collections.emptyMap());
    }

    @Override
    public String getVoucherInfoById(UUID voucherId) {
        Voucher voucher= findById(voucherId).get();
        return MessageFormat.format("{0}, VoucherId = {1}, Discount = {2}", voucher.getType(), voucher.getVoucherId(), voucher.getAmount());
    }
}
