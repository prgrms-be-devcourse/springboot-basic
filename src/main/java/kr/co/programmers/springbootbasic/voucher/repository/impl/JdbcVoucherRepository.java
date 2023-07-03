package kr.co.programmers.springbootbasic.voucher.repository.impl;

import kr.co.programmers.springbootbasic.voucher.exception.VoucherSaveFailException;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@Profile({"deploy", "dev"})
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update("INSERT INTO voucher (id, type_id, amount) VALUES (UUID_TO_BIN(?), ?, ?)",
                voucher.getId().toString().getBytes(),
                voucher.getType().getTypeId(),
                voucher.getAmount());
        return findByVoucherId(voucher.getId())
                .orElseThrow(() -> new VoucherSaveFailException("바우처를 저장하는데 실패했습니다."));
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE id = UUID_TO_BIN(?)",
                    voucherRowMapper(),
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("voucher id : {}가 존재하지 않습니다.", voucherId);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> listAll() {
        return jdbcTemplate.query("SELECT * FROM voucher",
                voucherRowMapper());
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM voucher WHERE id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            var voucherId = toUUID(rs.getBytes("id"));
            var type = VoucherType.resolveTypeId(rs.getInt("type_id"));
            var amount = rs.getLong("amount");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return switch (type) {
                case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount, createdAt);
                case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount, createdAt);
            };
        };
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
