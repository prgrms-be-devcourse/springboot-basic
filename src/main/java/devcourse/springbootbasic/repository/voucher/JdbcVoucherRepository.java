package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("prod")
@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Voucher save(Voucher voucher) {
        String sql = """
                        INSERT INTO voucher (id, voucher_type, discount_value)
                        VALUES (UUID_TO_BIN(:id), :voucher_type, :discount_value)
                """;

        jdbcTemplate.update(sql, Map.of(
                "id", voucher.getId().toString(),
                "voucher_type", voucher.getVoucherType().toString(),
                "discount_value", voucher.getDiscountValue()
        ));

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = """
                        SELECT *
                        FROM voucher
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            byte[] uuidBytes = rs.getBytes("id");
            UUID id = parseUUIDFromBytes(uuidBytes);
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            long discountValue = rs.getLong("discount_value");

            return Voucher.createVoucher(id, voucherType, discountValue);
        });
    }

    private static UUID parseUUIDFromBytes(byte[] uuidBytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidBytes);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
