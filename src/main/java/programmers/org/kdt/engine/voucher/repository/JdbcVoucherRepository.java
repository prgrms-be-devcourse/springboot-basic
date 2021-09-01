package programmers.org.kdt.engine.voucher.repository;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import programmers.org.kdt.engine.voucher.type.FixedAmountVoucher;
import programmers.org.kdt.engine.voucher.type.PercentDiscountVoucher;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long value = resultSet.getInt("value");
        String voucherStatusString = resultSet.getString("voucher_status");
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        switch (VoucherStatus.fromString(voucherStatusString)) {
            case FIXEDAMOUNTVOUCHER -> {
                return new FixedAmountVoucher(voucherId, value);
            }
            case PERCENTDISCOUNTVOUCHER -> {
                return new PercentDiscountVoucher(voucherId, value);
            }
        }
        LoggerFactory.getLogger(JdbcVoucherRepository.class).warn("status error"+VoucherStatus.fromString(voucherStatusString));
        return null;
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> ret;
        if(storage.containsKey(voucherId)) return Optional.ofNullable(storage.get(voucherId));
        try {
            ret = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
        updateStorage();
        return ret;
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        var update= jdbcTemplate.update(
            "INSERT INTO vouchers(voucher_id, value, voucher_status) VALUES (UUID_TO_BIN(:voucherId), :value, :voucherStatus)",
            toParamMap(voucher));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public Set<Entry<UUID, Voucher>> getAllEntry() {
        updateStorage();
        return storage.entrySet();
    }

    @Override
    public void deleteAll() {
        storage.clear();
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update(
            "UPDATE vouchers SET voucher_status = :voucherStatus, value = :value WHERE voucher_id = UUID_TO_BIN(:voucherId)",
            toParamMap(voucher)
        );
        return voucher;
    }

    private void updateStorage() {
        List<Voucher> query = jdbcTemplate.query("select * from vouchers", voucherRowMapper);
        if(query.isEmpty()) return;
        storage.clear();
        for(Voucher i : query) {
            storage.put(i.getVoucherId(), i);
        }
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("value", voucher.getValue());
            put("voucherStatus", voucher.getVoucherStatus().toString());
        }};
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
