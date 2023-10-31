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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Profile({"web", "cli", "test"})
@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String ID = "id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String DISCOUNT_VALUE = "discount_value";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String CREATED_AT = "created_at";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Voucher> findAllWithFilter(VoucherType voucherType, LocalDate startDate, LocalDate endDate) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM voucher WHERE 1=1");

        Map<String, Object> queryParams = new HashMap<>();

        if (voucherType != null) {
            queryBuilder.append(" AND voucher_type = :voucher_type");
            queryParams.put("voucher_type", voucherType.toString());
        }

        if (startDate != null) {
            queryBuilder.append(" AND created_at >= :start_date");
            queryParams.put("start_date", startDate);
        }

        if (endDate != null) {
            queryBuilder.append(" AND created_at < DATE_ADD(:end_date, INTERVAL 1 DAY)");
            queryParams.put("end_date", endDate);
        }

        String query = queryBuilder.toString();

        return jdbcTemplate.query(query, queryParams, (rs, rowNum) -> mapVoucherFromResultSet(rs));
    }

    @Override
    public List<Voucher> findAll() {
        String query = """
                        SELECT *
                        FROM voucher
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> mapVoucherFromResultSet(rs));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String query = """
                        SELECT *
                        FROM voucher
                        WHERE id = UUID_TO_BIN(:id)
                        FOR UPDATE
                """;

        List<Voucher> vouchers = jdbcTemplate.query(
                query,
                Map.of(ID, voucherId.toString()),
                (rs, rowNum) -> mapVoucherFromResultSet(rs));

        return vouchers.stream().findFirst();
    }

    @Override
    public boolean update(Voucher voucher) {
        String query = """
                        UPDATE voucher
                        SET voucher_type = :voucher_type,
                            discount_value = :discount_value,
                            customer_id = UUID_TO_BIN(:customer_id)
                        WHERE id = UUID_TO_BIN(:id)
                """;

        int updatedRow = jdbcTemplate.update(query, mapVoucherParameters(voucher));
        return updatedRow == 1;
    }

    @Override
    public void delete(Voucher voucher) {
        String query = """
                        DELETE FROM voucher
                        WHERE id = UUID_TO_BIN(:id)
                """;

        jdbcTemplate.update(query, mapVoucherParameters(voucher));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        String query = """
                        SELECT *
                        FROM voucher
                        WHERE customer_id = UUID_TO_BIN(:customer_id)
                """;

        return jdbcTemplate.query(
                query,
                Map.of(CUSTOMER_ID, customerId.toString()),
                (rs, rowNum) -> mapVoucherFromResultSet(rs));
    }

    @Override
    public Voucher save(Voucher voucher) {
        String query = """
                        INSERT INTO voucher (id, voucher_type, discount_value, customer_id, created_at)
                        VALUES (UUID_TO_BIN(:id), :voucher_type, :discount_value, UUID_TO_BIN(:customer_id), :created_at)
                """;

        jdbcTemplate.update(query, mapVoucherParameters(voucher));
        return voucher;
    }

    private Map<String, Object> mapVoucherParameters(Voucher voucher) {
        Map<String, Object> params = new HashMap<>();

        params.put(ID, voucher.getId().toString());
        params.put(VOUCHER_TYPE, voucher.getVoucherType().toString());
        params.put(DISCOUNT_VALUE, voucher.getDiscountValue());
        if (voucher.isAssigned()) {
            params.put(CUSTOMER_ID, voucher.getCustomerId().toString());
        } else {
            params.put(CUSTOMER_ID, null);
        }
        params.put(CREATED_AT, voucher.getCreatedAt());

        return params;
    }

    private Voucher mapVoucherFromResultSet(ResultSet rs) throws SQLException {
        UUID id = UUIDUtil.byteToUUID(rs.getBytes(ID));
        VoucherType voucherType = VoucherType.valueOf(rs.getString(VOUCHER_TYPE));
        long discountValue = rs.getLong(DISCOUNT_VALUE);
        UUID customerId = rs.getBytes(CUSTOMER_ID) == null
                ? null
                : UUIDUtil.byteToUUID(rs.getBytes(CUSTOMER_ID));
        LocalDateTime createdAt = rs.getTimestamp(CREATED_AT).toLocalDateTime();

        return Voucher.createVoucher(id, voucherType, discountValue, customerId, createdAt);
    }
}
