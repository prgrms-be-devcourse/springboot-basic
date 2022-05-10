package org.prgrms.kdtspringvoucher.repository.voucher;

import org.prgrms.kdtspringvoucher.entity.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringvoucher.entity.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;
import org.prgrms.kdtspringvoucher.entity.voucher.VoucherTypeNum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.ByteBuffer;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
@Profile({"local", "default"})
public class VoucherJDBCRepository implements VoucherRepository {

    public static final String SELECT_VOUCHER_ALL = "SELECT * FROM voucher ORDER BY created_at DESC";
    public static final String SELECT_VOUCHER_BY_ID = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String SELECT_VOUCHER_BY_CUSTOMER = "SELECT voucher.voucher_id as voucher_id, voucher_amount, voucher_type FROM voucher INNER JOIN (SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)) wallet ON voucher.voucher_id = wallet.voucher_id";
    public static final String INSERT_VOUCHER = "INSERT INTO voucher(voucher_id, voucher_amount, voucher_type, created_at) VALUES (UUID_TO_BIN(:voucherId), :voucherAmount, :voucherType, :createdAt)";
    public static final String ALLOCATE_VOUCHER_TO_CUSTOMER = "INSERT INTO wallet(customer_id, voucher_id) VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))";
    public static final String DELETE_VOUCHER_FROM_WALLET = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String DELETE_VOUCHER = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String COUNT_VOUCHER = "SELECT count(*) FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String COUNT_VOUCHER_FROM_WALLET = "SELECT count(*) FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String UPDATE_VOUCHER = "UPDATE voucher SET voucher_type = :voucherType, voucher_amount = :voucherAmount WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    public static final String SELECT_VOUCHER_BY_DATE = "SELECT * FROM voucher WHERE DATE(created_at) > (NOW() - INTERVAL 7 DAY)";
    public static final String SELECT_VOUCHER_BY_TYPE = "SELECT * FROM voucher WHERE voucher_type = :voucherType";

    private static final Logger logger = LoggerFactory.getLogger(VoucherJDBCRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_VOUCHER_BY_ID,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_VOUCHER_ALL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findVouchersByCustomer(UUID customerId) {
        return jdbcTemplate.query(SELECT_VOUCHER_BY_CUSTOMER,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        var update = jdbcTemplate.update(INSERT_VOUCHER, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return Optional.of(voucher);
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        var update = jdbcTemplate.update(UPDATE_VOUCHER, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return Optional.of(voucher);
    }

    @Override
    public void allocateVoucherToCustomer(UUID customerId, UUID voucherId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId.toString().getBytes());
        paramMap.put("voucherId", voucherId.toString().getBytes());

        var update = jdbcTemplate.update(ALLOCATE_VOUCHER_TO_CUSTOMER, paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public boolean isExistVoucher(UUID voucherId) {
        var countOfVoucher = jdbcTemplate.queryForObject(COUNT_VOUCHER,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                Integer.class);
        if (countOfVoucher < 1) {
            return false;
        }
        return true;
    }

    public boolean isExistVoucherFromWallet(UUID voucherId) {
        var countOfVoucher = jdbcTemplate.queryForObject(COUNT_VOUCHER_FROM_WALLET,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                Integer.class);
        if (countOfVoucher < 1) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        boolean isVoucher = isExistVoucher(voucherId);
        if (isVoucher == false) {
            return;
        }
        var update = jdbcTemplate.update(DELETE_VOUCHER,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Delete exception");
        }
    }

    @Override
    public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId) {
        boolean isVoucher = isExistVoucherFromWallet(voucherId);
        if (isVoucher == false) {
            return;
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId.toString().getBytes());
        paramMap.put("voucherId", voucherId.toString().getBytes());

        var update = jdbcTemplate.update(DELETE_VOUCHER_FROM_WALLET, paramMap);
        if (update != 1) {
            throw new RuntimeException("Delete exception");
        }
    }

    @Override
    public List<Voucher> findByCreatedAt() {
        return jdbcTemplate.query(SELECT_VOUCHER_BY_DATE, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByVoucherType(int voucherTypeNum) {
        return jdbcTemplate.query(SELECT_VOUCHER_BY_TYPE,
                Collections.singletonMap("voucherType", voucherTypeNum),
                voucherRowMapper);
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherAmount = resultSet.getLong("voucher_amount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        int voucherTypeNum = resultSet.getInt("voucher_type");
        if (voucherTypeNum == VoucherTypeNum.FIXED.ordinal()) {
            return new FixedAmountVoucher(voucherId, voucherAmount, createdAt);
        }
        return new PercentDiscountVoucher(voucherId, voucherAmount, createdAt);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        hashMap.put("voucherAmount", voucher.getAmount());
        hashMap.put("voucherType", voucher.getVoucherTypeNum());
        hashMap.put("createdAt", voucher.getCreatedAt());

        return hashMap;
    }
}
