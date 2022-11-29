package org.prgrms.voucherapplication.domain.voucher.repository;

import org.prgrms.voucherapplication.domain.customer.exception.NothingInsertException;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.exception.EmptyResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.voucherapplication.global.exception.ExceptionCode.EMPTY_RESULT;
import static org.prgrms.voucherapplication.global.exception.ExceptionCode.NOTHING_INSERT;

@Repository
@Profile("prod")
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        int discount = resultSet.getInt("discount");
        String voucherTypeName = resultSet.getString("voucher_type");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        VoucherType voucherType = VoucherType.of(voucherTypeName);
        return voucherType.createVoucher(voucherId, discount, createdAt);
    };

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paraMap.put("discount", voucher.getDiscount());
        paraMap.put("voucherType", voucher.getVoucherType().name());
        paraMap.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));

        int update = namedParameterJdbcTemplate.update("INSERT INTO vouchers(voucher_id, discount, voucher_type, created_at) VALUES (UUID_TO_BIN(:voucherId), :discount, :voucherType, :createdAt)",
                paraMap);
        if (update != 1) {
            throw new NothingInsertException(NOTHING_INSERT);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public int deleteAll() {
        return namedParameterJdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.info(EMPTY_RESULT.getMessege(), e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        namedParameterJdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        List<Voucher> voucherList = namedParameterJdbcTemplate.query("select * from vouchers where voucher_type = :voucherType",
                Collections.singletonMap("voucherType", type.name()),
                voucherRowMapper);
        if (voucherList.isEmpty()) {
            logger.info(EMPTY_RESULT.getMessege(), EmptyResultException.class);
            throw new EmptyResultException(EMPTY_RESULT);
        }
        return voucherList;
    }

    @Override
    public List<Voucher> findByTypeAndCreatedAt(VoucherType type, LocalDateTime createdAt) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("voucherType", type.name());
        paraMap.put("createdAt", Timestamp.valueOf(createdAt));

        List<Voucher> voucherList = namedParameterJdbcTemplate.query("select * from vouchers where voucher_type = :voucherType and created_at >= :createdAt",
                paraMap, voucherRowMapper);
        if (voucherList.isEmpty()) {
            logger.info(EMPTY_RESULT.getMessege(), EmptyResultException.class);
            throw new EmptyResultException(EMPTY_RESULT);
        }
        return voucherList;
    }

    @Override
    public List<Voucher> findByCreatedAtAfter(LocalDateTime createdAt) {
        List<Voucher> voucherList = namedParameterJdbcTemplate.query("select * from vouchers where created_at >= :createdAt",
                Collections.singletonMap("createdAt", createdAt), voucherRowMapper);
        if (voucherList.isEmpty()) {
            logger.info(EMPTY_RESULT.getMessege(), EmptyResultException.class);
            throw new EmptyResultException(EMPTY_RESULT);
        }
        return voucherList;
    }
}
