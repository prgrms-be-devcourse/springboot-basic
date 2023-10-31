package team.marco.voucher_management_system.repository.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static team.marco.voucher_management_system.util.UUIDUtil.bytesToUUID;
import static team.marco.voucher_management_system.util.UUIDUtil.uuidToBytes;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, discount_value, code, name) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE From vouchers WHERE voucher_id = ?";
    private static final String SELECT_MAXIMUM_ID = "SELECT v.voucher_id From vouchers v ORDER BY voucher_id DESC LIMIT 1";
    private static final String SELECT_TOTAL_COUNTS = "SELECT count(*) From vouchers v";

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = 0;

        update = jdbcTemplate.update(INSERT_SQL,
                voucher.getId(),
                voucher.getVoucherType().name(),
                voucher.getDiscountValue(),
                uuidToBytes(voucher.getCode()),
                voucher.getName());

        if(update != 1) {
            logger.error("쿠폰을 추가하는 과정에서 오류가 발생했습니다.");
            throw new RuntimeException("쿠폰을 추가하는 과정에서 오류가 발생했습니다.");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        jdbcTemplate.query(SELECT_ALL_SQL
                , (resultSet, rowNum) -> vouchers.add(resultSetToVoucher(resultSet)));

        return Collections.unmodifiableList(vouchers);
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL
                , (resultSet, rowNum) -> resultSetToVoucher(resultSet)
                , voucherId));
    }

    @Override
    public void deleteById(Long voucherId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, voucherId);
    }

    @Override
    public Optional<Long> findLatestVoucherId() {
        Long size = jdbcTemplate.queryForObject(SELECT_TOTAL_COUNTS, Long.class);
        if(size == 0) return Optional.empty();

        return Optional.of(jdbcTemplate.queryForObject(SELECT_MAXIMUM_ID, Long.class));
    }

    private Voucher resultSetToVoucher(ResultSet resultSet) throws SQLException {
        Long voucherId = resultSet.getLong("voucher_id");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        int discountValue = resultSet.getInt("discount_value");
        UUID code = bytesToUUID(resultSet.getBytes("code"));
        String name = resultSet.getString("name");

        return new Voucher.Builder(voucherId, voucherType, discountValue)
                .code(code)
                .name(name)
                .build();
    }
}
