package org.prgrms.kdt.voucher.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.controller.dto.VoucherSearchCriteria;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("voucher_id"));
        long value = resultSet.getLong("value");
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        return type.convert(id, value, createdAt);
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = this.jdbcTemplate.update(
            "INSERT INTO vouchers(voucher_id, value, type, created_at)"
                + " VALUES(UUID_TO_BIN(:id), :value, :type, :createdAt)"
            , toParamMap(voucher));

        if (update == 0) {
            throw new RuntimeException("Noting was inserted"); // 커스텀 예외로 수정해보자
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> searchVoucher(VoucherSearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM vouchers");
        Map<String, Object> paramMap = new HashMap<>();

        if (criteria.getType() != null) {
            sb.append(" WHERE type=  :type");
            paramMap.put("type", criteria.getType());
            if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                sb.append(" AND created_at BETWEEN :startDate AND :endDate");
                paramMap.put("startDate", criteria.getStartDate());
                paramMap.put("endDate", criteria.getEndDate());
            } else if (criteria.getStartDate() != null) {
                sb.append(" AND created_at >= :startDate");
                paramMap.put("startDate", criteria.getStartDate());
            } else if (criteria.getEndDate() != null) {
                sb.append(" AND created_at <= :endDate");
                paramMap.put("endDate", criteria.getEndDate());
            }
        } else if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
            sb.append(" WHERE created_at BETWEEN :startDate AND :endDate");
            paramMap.put("startDate", criteria.getStartDate());
            paramMap.put("endDate", criteria.getEndDate());
        } else if (criteria.getStartDate() != null) {
            sb.append(" WHERE created_at >= :startDate");
            paramMap.put("startDate", criteria.getStartDate());
        } else if (criteria.getEndDate() != null) {
            sb.append(" WHERE created_at <= :endDate");
            paramMap.put("endDate", criteria.getEndDate());
        }

       return this.jdbcTemplate.query(sb.toString(), paramMap, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(
                this.jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:id)",
                    Collections.singletonMap("id", id.toString().getBytes()),
                    voucherRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(UUID id, long value) {
        int update = this.jdbcTemplate.update("UPDATE vouchers SET value = :value WHERE voucher_id = UUID_TO_BIN(:id)",
            new HashMap<String, Object>() {{
                put("id", id.toString().getBytes());
                put("value", value);
            }}
        );

        if (update == 0) {
            throw new RuntimeException("수정이 일어나지 않았습니다.");
        }
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID id) {
        this.jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(:id)",
            Collections.singletonMap("id", id.toString().getBytes()));
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", voucher.getId().toString().getBytes());
        paramMap.put("value", voucher.getValue());
        paramMap.put("type", voucher.getVoucherType().toString());
        paramMap.put("createdAt", voucher.getCreatedAt());

        return paramMap;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

}
