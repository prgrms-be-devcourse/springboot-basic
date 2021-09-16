package prgms.springbasic.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String INSERT_QUERY = "INSERT INTO vouchers(voucher_type, voucher_id, discount_value) VALUES ?, (UUID_TO_BIN(?), ?)";
    private static final String UPDATE_QUERY = "UPDATE vouchers SET voucher_type = ?, discount_value = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM vouchers WHERE voucher_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM customers";


    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcVoucherRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        List<Voucher> result = jdbcTemplate.query(FIND_BY_ID_QUERY, rowMapper(), voucherId);
        return result.stream().findAny();
    }

    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_QUERY,
                voucher.getVoucherId().toString().getBytes(),
                voucher.getClass().getSimpleName(),
                voucher.getDiscountValue());
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return voucher;
    }

    @Override
    public Voucher save(Voucher voucher) throws IOException {
        int update = jdbcTemplate.update(INSERT_QUERY,
                voucher.getClass().getSimpleName(),
                voucher.getVoucherId().toString().getBytes(),
                voucher.getDiscountValue());
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() throws IOException {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper());
    }

    private RowMapper<Voucher> rowMapper() {
        return (resultSet, rowNum) -> {
            String voucherType = resultSet.getString("voucher_type");
            UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
            long discountValue = Long.parseLong(resultSet.getString("discount_value"));

            return makeTempVoucher(voucherType, voucherId, discountValue);
        };
    }

    private Voucher makeTempVoucher(String voucherType, UUID voucherId, long discountValue) {
        if (voucherType.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(voucherId, discountValue);
        }
        return new PercentDiscountVoucher(voucherId, discountValue);
    }
}
