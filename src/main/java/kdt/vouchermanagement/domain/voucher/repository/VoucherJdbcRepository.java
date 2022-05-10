package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String SAVE_SQL = "INSERT INTO voucher(voucher_type, discount_value) VALUES(:voucherType, :discountValue)";
    private final String FIND_ALL_SQL = "SELECT * FROM voucher";
    private final String SELECT_LAST_INSERT_VOUCHER_SQL = "SELECT * FROM voucher ORDER BY voucher.voucher_id DESC LIMIT 1;";
    private final String UPDATE_SQL = "UPDATE vouchers SET voucher_type = :type, value = :value WHERE voucher_id = :id";
    private final String SELECT_BY_ID = "SELECT * FROM voucher WHERE voucher_id = :voucherId";
    private final String DELETE_SQL = "DELETE FROM voucher WHERE voucher_id = :voucherId";
    private final String FIND_BY_TYPE_DATE_SQL = "SELECT * FROM voucher WHERE voucher_type = :type and DATE(created_at) = :date";
    private final String FIND_BY_TYPE_SQL = "SELECT * FROM voucher WHERE voucher_type = :type";
    private final String FIND_BY_DATE_SQL = "SELECT * FROM voucher WHERE DATE(created_at) = :date";


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

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        List<Voucher> foundVouchers = jdbcTemplate.query(SELECT_BY_ID, Map.of("voucherId", voucherId), voucherRowMapper);
        return foundVouchers.isEmpty() ? Optional.empty() : Optional.of(foundVouchers.get(0));
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, LocalDate date) {
        return jdbcTemplate.query(FIND_BY_TYPE_DATE_SQL, Map.of("type", type.toString(), "date", date), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return jdbcTemplate.query(FIND_BY_TYPE_SQL, Map.of("type", type.toString()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByDate(LocalDate date) {
        return jdbcTemplate.query(FIND_BY_DATE_SQL, Map.of( "date", date), voucherRowMapper);
    }

    @Override
    public void deleteById(Long voucherId) {
        int deleteNum = jdbcTemplate.update(DELETE_SQL, Collections.singletonMap("voucherId", voucherId));
        if (deleteNum != 1) {
            throw new IllegalStateException("Voucher delete query가 실패하였습니다.");
        }
    }

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
}
