package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.global.common.JdbcRepositoryManager;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String CREATE = "INSERT INTO voucher(voucher_id, discount, voucher_type) VALUES(UUID_TO_BIN(?), (?), (?))";
    private static final String READ = "SELECT * FROM voucher";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, index) -> {

        UUID voucherId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("voucher_id"));
        Long discount = resultSet.getLong("discount");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));

        return new Voucher(voucherId, discount, voucherType, voucherType.getVoucherPolicy());
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {

        jdbcTemplate.update(CREATE,
                voucher.getVoucherId().toString(),
                voucher.getDiscount(),
                voucher.getVoucherType().toString());
    }

    @Override
    public List<Voucher> findAll() {

        List<Voucher> vouchers = jdbcTemplate.query(READ, voucherRowMapper);

        return vouchers;
    }

    private static UUID bytesToUUID(byte[] bytes) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
