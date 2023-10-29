package team.marco.voucher_management_system.repository.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.voucher.FixedAmountVoucher;
import team.marco.voucher_management_system.model.voucher.PercentDiscountVoucher;
import team.marco.voucher_management_system.model.voucher.Voucher;
import team.marco.voucher_management_system.model.voucher.VoucherType;
import team.marco.voucher_management_system.repository.custromer.CustomerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static team.marco.voucher_management_system.util.UUIDUtil.bytesToUUID;
import static team.marco.voucher_management_system.util.UUIDUtil.uuidToBytes;

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, amount, percent, owner_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_BY_OWNER_SQL = "SELECT * FROM vouchers WHERE owner_id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = ?";
    private static final String UPDATE_BY_ID_SQL = "UPDATE vouchers SET amount = ?, percent = ?, owner_id = ? WHERE voucher_id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE From vouchers WHERE voucher_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate, CustomerRepository customerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRepository = customerRepository;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = 0;
        switch (voucher.getType()) {
            case FIXED -> {
                FixedAmountVoucher amountVoucher = (FixedAmountVoucher) voucher;
                update = jdbcTemplate.update(INSERT_SQL,
                        uuidToBytes(voucher.getId()),
                        voucher.getType().name(),
                        amountVoucher.getAmount(),
                        null,
                        voucher.getOwnerId() == null ? null : uuidToBytes(voucher.getOwnerId()));
            }
            case PERCENT -> {
                PercentDiscountVoucher percentVoucher = (PercentDiscountVoucher) voucher;
                update = jdbcTemplate.update(INSERT_SQL,
                        uuidToBytes(voucher.getId()),
                        voucher.getType().name(),
                        null,
                        percentVoucher.getPercent(),
                        voucher.getOwnerId() == null ? null : uuidToBytes(voucher.getOwnerId()));
            }
        }

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
    public List<Voucher> findByOwner(UUID ownerId) {
        List<Voucher> vouchers = new ArrayList<>();
        jdbcTemplate.query(SELECT_BY_OWNER_SQL
                , (resultSet, rowNum) -> vouchers.add(resultSetToVoucher(resultSet))
                , uuidToBytes(ownerId));

        return Collections.unmodifiableList(vouchers);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL
                , (resultSet, rowNum) -> resultSetToVoucher(resultSet)
                , uuidToBytes(voucherId)));
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = 0;
        switch (voucher.getType()) {
            case FIXED -> {
                FixedAmountVoucher amountVoucher = (FixedAmountVoucher) voucher;
                update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
                        amountVoucher.getAmount(),
                        null,
                        amountVoucher.getOwnerId() != null ?  uuidToBytes(amountVoucher.getOwnerId()) : null,
                        uuidToBytes(voucher.getId()));
            }
            case PERCENT -> {
                PercentDiscountVoucher percentVoucher = (PercentDiscountVoucher) voucher;
                update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
                        null,
                        percentVoucher.getPercent(),
                        percentVoucher.getOwnerId() != null ? uuidToBytes(percentVoucher.getOwnerId()) : null,
                        uuidToBytes(voucher.getId()));
            }
        }

        if(update != 1) {
            logger.error("쿠폰을 변경하는 과정에서 오류가 발생했습니다.");
            throw new RuntimeException("쿠폰을 변경하는 과정에서 오류가 발생했습니다.");
        }

        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, uuidToBytes(voucherId));
    }

    private Voucher resultSetToVoucher(ResultSet resultSet) throws SQLException {
        UUID voucherId = bytesToUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        Integer amount = resultSet.getInt("amount");
        Integer percent = resultSet.getInt("percent");
        UUID ownerId = bytesToUUID(resultSet.getBytes("owner_id"));

        LoadedJsonVoucher jsonVoucher = new LoadedJsonVoucher(voucherId, voucherType, amount, percent, ownerId);
        return jsonVoucher.jsonVoucherToVoucher();
    }
}
