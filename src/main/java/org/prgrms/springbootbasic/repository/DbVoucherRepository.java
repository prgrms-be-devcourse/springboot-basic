package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;


@Primary
@Repository
public class DbVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(DbVoucherRepository.class);
    public static final String INSERT_SQL = "insert into vouchers(voucher_id, voucher_type, quantity) values (UUID_TO_BIN(:voucherId), :voucherType, :quantity)";
    public static final String SELECT_SQL = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String SELECT_ALL_SQL = "select * from vouchers";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    DbVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParaMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
            put("voucherType", VoucherType.ClassToVoucherType(voucher).name());
            put("quantity", voucher.getQuantity());
        }};
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long quantity = resultSet.getLong("quantity");

        try {
            return VoucherType.valueOf(voucherType)
                    .getVoucherClass()
                    .getConstructor(UUID.class, long.class)
                    .newInstance(voucherId, quantity);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    };

    private static UUID toUUID(byte[] bytes) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public void insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_SQL,
                toParaMap(voucher));
        if (update != 1) {
            logger.error("Nothing was inserted");
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_SQL,
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }
}
