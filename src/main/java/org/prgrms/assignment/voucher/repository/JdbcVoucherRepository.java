package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherFactory;
import org.prgrms.kdt.JdbcCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("dev")
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, name, voucher_type, created_at, benefit) VALUES(UUID_TO_BIN(?), ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET name = ?, benefit = ? WHERE voucher_id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_ALL_SQL = "DELETE * FROM vouchers";
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNumber) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.of(resultSet.getString("voucher_type"));
        Long benefit = resultSet.getLong("benefit");
        String voucherName = resultSet.getString("name");

        return new VoucherFactory().createVoucher(voucherType, voucherId, voucherName, benefit);
    };

    public JdbcVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findByID(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
            voucherRowMapper,
            voucherId.toString().getBytes()));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_SQL,
            voucher.getVoucherId().toString().getBytes(),
            voucher.getVoucherName(),
            voucher.getVoucherType().toString(),
            voucher.getCreatedAt(),
            voucher.getBenefit());
        if(update != 1) {
            logger.error("insert error");
            throw new RuntimeException("Nothing was inserted!");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE_SQL,
            voucher.getVoucherName(),
            voucher.getBenefit(),
            voucher.getVoucherId().toString().getBytes());
        if(update != 1) {
            logger.error("update error");
            throw new RuntimeException("Nothing was inserted!");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
