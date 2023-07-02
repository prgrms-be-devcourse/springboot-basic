package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository .class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        final UUID voucherId = convertUUID(resultSet.getBytes("voucher_id"));
        final String voucherType = resultSet.getString("voucher_type");
        Long amount = resultSet.getLong("discount_amount");

        return VoucherType.of(voucherType).makeVoucher(amount);
    };

    @Override
    public Voucher insert(Voucher voucher) {
        int insert = jdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,discount_amount) VALUES (UUID_TO_BIN(?),?,?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherType().name(),
                voucher.getDiscountAmount());
        if (insert != 1) {
            throw new RuntimeException("추가된 데이터 내역이 없습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers",voucherRowMapper);
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("결과값이 없습니다!");
            return Optional.empty();
        }
    }


    public static UUID convertUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

