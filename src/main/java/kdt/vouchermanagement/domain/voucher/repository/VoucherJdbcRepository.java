package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String SAVE_SQL = "INSERT INTO voucher(voucher_type, discount_value) VALUES(:voucherType, :discountValue)";
    private final String FIND_ALL_SQL = "SELECT * FROM voucher";
    private final String SELECT_LAST_INSERT_VOUCHER_SQL = "SELECT * FROM voucher ORDER BY voucher.voucher_id DESC LIMIT 1;";
    private final String UPDATE_SQL = "UPDATE vouchers SET voucher_type = :type, value = :value WHERE voucher_id = :id";

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException("애플리케이션이 null을 사용하려고 합니다.");
        }

        if (voucher.getVoucherId() == null) {
            executeInsertVoucherQuery(voucher);
            Voucher voucherEntity = executeSelectLastVoucherQuery();
            return voucherEntity;
        }

        executeUpdateQuery(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper);
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherType", voucher.getVoucherType().toString());
        paramMap.put("discountValue", voucher.getDiscountValue());
        return paramMap;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        Long voucherId = resultSet.getLong("voucher_id");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        int discountValue = resultSet.getInt("discount_value");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return voucherType.createEntity(voucherId, discountValue, createdAt, updatedAt);
    };

    private void executeInsertVoucherQuery(Voucher voucher) {
        int insertNum = jdbcTemplate.update(SAVE_SQL, toParamMap(voucher));
        if (insertNum != 1) {
            throw new IllegalStateException("Voucher insert query가 실패하였습니다.");
        }
    }

    private Voucher executeSelectLastVoucherQuery() {
        Voucher voucherEntity = jdbcTemplate.queryForObject(SELECT_LAST_INSERT_VOUCHER_SQL, Collections.emptyMap(), voucherRowMapper);
        return voucherEntity;
    }

    private void executeUpdateQuery(Voucher voucher) {
        int updateNum = jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
        if (updateNum != 1) {
            throw new IllegalStateException("Vouvhcer update query가 실패하였습니다.");
        }
    }
}
