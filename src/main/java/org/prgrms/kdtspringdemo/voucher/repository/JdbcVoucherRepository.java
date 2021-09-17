package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Profile("dev")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String VERSION_8_0_UUID = "UUID_TO_BIN(:voucherId)";
    private final String VERSION_5_7_UUID = "UNHEX(REPLACE(:voucherId, '-', ''))";
    private final String CURRENT_UUID = VERSION_8_0_UUID;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private String getVersion57UUID(String value) {
        return "UNHEX(REPLACE(:" + value + ", '-', ''))";
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherName = resultSet.getString("name");
        var dicount = resultSet.getLong("discount");
        var type = resultSet.getString("type");
        var typeString = VoucherType.valueOf(type);
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, voucherName, dicount, typeString, createdAt);
    };

    private Map<String, Object> toparamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("name", voucher.getName());
            put("type", voucher.getType().name());
            put("discount", voucher.getDiscount());
            put("createAt", Timestamp.valueOf(voucher.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)));
            put("usedAt", voucher.getUsedAt() != null ? Timestamp.valueOf(voucher.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)) : null);
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        System.out.println(toparamMap(voucher).toString());
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, name, discount, type, created_at) VALUES (" + CURRENT_UUID + ", :name, :discount, :type, :createAt);",
                toparamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = " + CURRENT_UUID,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE name = :name", Collections.singletonMap("name", name), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(String type) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE type = :type", Collections.singletonMap("type", type), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByCustomerId(String customerId) {
        return jdbcTemplate.query("SELECT * FROM vouchers LEFT JOIN wallets ON wallets.customer_id = " + getVersion57UUID("customerId") + " WHERE wallets.voucher_id = vouchers.voucher_id",
                Collections.singletonMap("customerId", customerId.getBytes()), voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update("UPDATE vouchers SET name = :name, type = :type, discount = :discount, created_at = :createAt WHERE voucher_id = " + CURRENT_UUID + ";",
                toparamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteByVoucherId(String voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = " + CURRENT_UUID,
                Collections.singletonMap("voucherId", voucherId.getBytes()));
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM vouchers", Collections.emptyMap() ,Integer.class);
    }
}
