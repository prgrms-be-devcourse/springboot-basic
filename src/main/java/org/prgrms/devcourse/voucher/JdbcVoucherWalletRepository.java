package org.prgrms.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Primary
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<VoucherUseInfo> voucherUseInfoRowMapper = (rs, rowNum) -> {
        var voucherUseInfoId = toUUID(rs.getBytes("voucher_use_info_id"));
        var customerId = toUUID(rs.getBytes("customer_id"));
        var voucherId = toUUID(rs.getBytes("voucher_id"));
        var issuedAt = rs.getTimestamp("issued_at").toLocalDateTime();
        var expiredAt = rs.getTimestamp("expired_at").toLocalDateTime();
        var isUsed = rs.getBoolean("is_used");
        return new VoucherUseInfo(voucherUseInfoId, customerId, voucherId, issuedAt, expiredAt, isUsed);
    };

    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VoucherUseInfo insert(VoucherUseInfo voucherUseInfo) {
        var update = jdbcTemplate.update("insert into voucher_use_infos(voucher_use_info_id, customer_id, voucher_id, issued_at, expired_at, is_used) values(UUID_TO_BIN(:voucherUseInfoId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :issuedAt, :expiredAt, :isUsed)",
                toParamMap(voucherUseInfo));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucherUseInfo;
    }

    @Override
    public Optional<VoucherUseInfo> findOne(UUID voucherUseInfoId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from voucher_use_infos where voucher_use_info_id = UUID_TO_BIN(:voucherUseInfoId)",
                    Collections.singletonMap("voucherUseInfoId", voucherUseInfoId.toString().getBytes()),
                    voucherUseInfoRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public VoucherUseInfo update(VoucherUseInfo voucherUseInfo) {
        var update = jdbcTemplate.update("update voucher_use_infos set expired_at = :expiredAt, is_used = :isUsed where voucher_use_info_id = UUID_TO_BIN(:voucherUseInfoId)",
                toParamMap(voucherUseInfo));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucherUseInfo;
    }

    @Override
    public List<VoucherUseInfo> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("select * from voucher_use_infos where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherUseInfoRowMapper);
    }

    @Override
    public List<VoucherUseInfo> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select * from voucher_use_infos where cutomer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherUseInfoRowMapper);
    }

    @Override
    public List<VoucherUseInfo> findByEmail(String email) {
        return jdbcTemplate.query("select * from voucher_use_infos where email = :email",
                Collections.singletonMap("email", email),
                voucherUseInfoRowMapper);
    }

    @Override
    public UUID delete(UUID voucherUseInfoId) {
        var update = jdbcTemplate.getJdbcTemplate().update("delete from voucher_use_infos where voucher_use_info_id = UUID_TO_BIN(:voucherUseInfoId)");
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
        return voucherUseInfoId;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("delete from voucher_use_infos");
    }

    private HashMap<String, Object> toParamMap(VoucherUseInfo voucherUseInfo) {
        HashMap<String, Object> paramMap =  new HashMap<>();
        paramMap.put("voucherUseInfoId", voucherUseInfo.getVoucherUseInfoId().toString().getBytes());
        paramMap.put("customerId", voucherUseInfo.getCustomerId().toString().getBytes());
        paramMap.put("voucherId", voucherUseInfo.getVoucherId().toString().getBytes());
        paramMap.put("issuedAt", Timestamp.valueOf(voucherUseInfo.getIssuedAt()));
        paramMap.put("expiredAt", Timestamp.valueOf(voucherUseInfo.getExpiredAt()));
        paramMap.put("isUsed", voucherUseInfo.isUsed());

        return paramMap;
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
