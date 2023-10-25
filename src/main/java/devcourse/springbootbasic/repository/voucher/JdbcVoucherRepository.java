package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("prod")
@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String ID = "id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String DISCOUNT_VALUE = "discount_value";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Voucher> findAll() {
        String query = """
                        SELECT *
                        FROM voucher
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> mapVoucherFromResultSet(rs));
    }

    @Override
    public Voucher save(Voucher voucher) {
        String query = """
                        INSERT INTO voucher (id, voucher_type, discount_value)
                        VALUES (UUID_TO_BIN(:id), :voucher_type, :discount_value)
                """;

        jdbcTemplate.update(query, mapVoucherParameters(voucher));
        return voucher;
    }

    private Map<String, Object> mapVoucherParameters(Voucher voucher) {
        Map<String, Object> params = new HashMap<>();

        params.put(ID, voucher.getId().toString());
        params.put(VOUCHER_TYPE, voucher.getVoucherType().toString());
        params.put(DISCOUNT_VALUE, voucher.getDiscountValue());

        return params;
    }

    private Voucher mapVoucherFromResultSet(ResultSet rs) throws SQLException {
        UUID id = UUIDUtil.byteToUUID(rs.getBytes(ID));
        VoucherType voucherType = VoucherType.valueOf(rs.getString(VOUCHER_TYPE));
        long discountValue = rs.getLong(DISCOUNT_VALUE);

        return Voucher.createVoucher(id, voucherType, discountValue);
    }
}
