package org.prgrms.vouchermanager.voucher.repository;

import org.prgrms.vouchermanager.customer.repository.CustomerJDBCRepository;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.prgrms.vouchermanager.voucher.domain.VoucherFactory;
import org.prgrms.vouchermanager.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 위클리미션 목록 중 JdbcVoucherRepository 구현 입니다!
 */
@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private final JdbcTemplate jdbcTemplate;

    /**
     * 데이터베이스에서 읽어온 ResultSet을 Voucher로 매핑하기 위한 Mapper입니다.
     */
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, voucher) -> {
        UUID voucher_id = UUIDBytesToUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("type").toUpperCase());
        Long amount = resultSet.getLong("amount");
        return VoucherFactory.getVoucher(voucher_id, voucherType, amount);
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UUID UUIDBytesToUUID(byte[] customer_ids) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(customer_ids);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error(MessageFormat.format("findById: {0} 반환 결과가 1개 행이 아닙니다.", voucherId));
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int theNumberOfRowAffected;
        try {
            theNumberOfRowAffected = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, amount) VALUES (UUID_TO_BIN(?), ?, ?)",
                    voucher.getVoucherId().toString().getBytes(),
                    voucher.getVoucherType().toString(),
                    voucher.getDiscountValue());
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 존재하는 voucherId입니다.");
        }
        if (theNumberOfRowAffected != 1) {
            throw new IllegalArgumentException("잘못된 삽입입니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        return jdbcTemplate.query("SELECT * from vouchers", voucherRowMapper);
    }
}
