package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherDBOutputDto;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.exception.CanNotDeleteException;
import org.programmers.springbootbasic.exception.CanNotInsertException;
import org.programmers.springbootbasic.exception.CanNotUpdateException;
import org.programmers.springbootbasic.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherDBOutputDtoRowMapperRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long amount = resultSet.getLong("amount");
        return new VoucherDBOutputDto(voucherId, type, amount).toVoucher();
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher save(Voucher voucher) {
        int insertRow = 0;
        try {
            insertRow = jdbcTemplate.update("insert into vouchers(voucher_id, type, amount) values (UUID_TO_BIN(?), ?, ?)",
                    voucher.getVoucherId().toString().getBytes(),
                    voucher.getVoucherType().getType(),
                    voucher.getAmount()
            );
            if (insertRow != 1) throw new CanNotInsertException("Voucher를 데이터베이스에 쓸 수 없습니다.");
            return voucher;
        } catch (DataAccessException e) {
            logger.warn(e.getMessage());
            throw new CanNotInsertException("Voucher를 데이터베이스에 쓸 수 없습니다.", e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherDBOutputDtoRowMapperRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(?)",
                    voucherDBOutputDtoRowMapperRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("검색 결과가 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        return jdbcTemplate.query("select * from vouchers where type = ?",
                voucherDBOutputDtoRowMapperRowMapper,
                voucherType);
    }

    @Override
    public void update(Voucher voucher) {
        try {
            int updateRow = jdbcTemplate.update("update vouchers set type = ?, amount = ? where voucher_id = UUID_TO_BIN(?)",
                    voucher.getVoucherType().getType(),
                    voucher.getAmount(),
                    voucher.getVoucherId().toString().getBytes()
            );
            if (updateRow != 1) {
                throw new CanNotInsertException("Voucher를 업데이트할 수 없습니다.");
            }
        } catch (DataAccessException e) {
            logger.warn("Voucher를 업데이트할 수 없습니다.");
            throw new CanNotUpdateException("Voucher를 업데이트할 수 없습니다.", e);
        }

    }

    @Override
    public void deleteAll() {
        try {
            int deleteRow = jdbcTemplate.update("delete from vouchers");
        } catch (DataAccessException e) {
            throw new CanNotDeleteException("Voucher들을 삭제할 수 없습니다.", e);
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        try {
            int deleteRow = jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(?)",
                    voucherId.toString().getBytes());
            if (deleteRow != 1) throw new CanNotDeleteException("Voucher를 삭제할 수 없습니다.");
        } catch (DataAccessException e) {
            throw new CanNotDeleteException("Voucher를 삭제할 수 없습니다.", e);
        }
    }
}
