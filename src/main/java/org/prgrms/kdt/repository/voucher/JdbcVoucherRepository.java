package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
@Profile("release")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherBuilder voucherBuilder;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, VoucherBuilder voucherBuilder) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
        this.voucherBuilder = voucherBuilder;
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> mapToVoucher(resultSet);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void insert(Voucher voucher) {
        Map<String, Object> paramMap = toParamMap(voucher);
        int insertCount = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, discount_value, voucher_type, created_at) VALUES (UUID_TO_BIN(:voucherId), :discountValue, :voucherType, :createdAt)",
                paramMap);

        if (insertCount != 1) {
            throw new IllegalArgumentException("삽일할 바우처가 없습니다.");
        }
    }

    @Override
    public List<Voucher> getAllStoredVoucher() {
        RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> mapToVoucher(resultSet);
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public UUID remove(UUID voucherId) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );

        if (deleteCount != 1) {
            throw new IllegalArgumentException("삭제할 바우처가 없습니다.");
        }

        return voucherId;
    }

    @Override
    public Voucher update(Voucher voucher) {
        Map<String, Object> paramMap = toParamMap(voucher);
        int updateCount = jdbcTemplate.update(getUpdateSqlCommand(voucher), paramMap);

        if (updateCount != 1) {
            throw new IllegalArgumentException("업데이트할 바우처가 없습니다.");
        }

        return voucher;
    }

    private String getUpdateSqlCommand(Voucher voucher) {
        if (voucher.getOwnedCustomerId().isEmpty()) {
            return "UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType, owned_customer_id = null, created_at = :createdAt WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        }
        return "UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType, owned_customer_id = UUID_TO_BIN(:ownedCustomerId), created_at = :createdAt WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    }

    @Override
    public void clear() {
        jdbcTemplate.update("TRUNCATE FROM vouchers", Collections.emptyMap());
    }

    private Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String discountValue = resultSet.getString("discount_value");
        String voucherType = resultSet.getString("voucher_type");
        String ownedCustomerId = resultSet.getBytes("owned_customer_id") != null ? toUUID(resultSet.getBytes("owned_customer_id")).toString() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return voucherBuilder.create()
                .setVoucherId(voucherId)
                .setDiscountAmount(discountValue)
                .setVoucherType(VoucherType.of(voucherType))
                .setOwnedCustomerId(ownedCustomerId)
                .setCreatedAt(createdAt)
                .build();
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        hashMap.put("discountValue", voucher.getDiscountAmount());
        hashMap.put("voucherType", voucher.getVoucherType().getClassName());
        hashMap.put("ownedCustomerId", voucher.getOwnedCustomerId().isPresent() ? voucher.getOwnedCustomerId().get().toString().getBytes() : null);
        hashMap.put("createdAt", voucher.getCreatedAt());

        return hashMap;
    }
}
