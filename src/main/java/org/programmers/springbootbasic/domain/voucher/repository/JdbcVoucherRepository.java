package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherDBOutputDto;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.exception.CanNotDeleteException;
import org.programmers.springbootbasic.exception.CanNotInsertException;
import org.programmers.springbootbasic.exception.CanNotUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

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
                    voucher.getVoucherType(),
                    voucher.getAmount()
            );
            if(insertRow != 1) throw new CanNotInsertException("Voucher를 데이터베이스에 쓸 수 없습니다.");
            return voucher;
        } catch (DataAccessException e) {
            logger.error("Voucher를 데이터베이스에 쓸 수 없습니다.");
            throw new CanNotInsertException(e.getMessage(), e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherDBOutputDtoRowMapperRowMapper);
    }

    @Override
    public void update(Voucher voucher) {
        try {
            int updateRow = jdbcTemplate.update("update vouchers set type = ?, amount = ? where voucher_id = UUID_TO_BIN(?)",
                    voucher.getVoucherType(),
                    voucher.getAmount(),
                    voucher.getVoucherId()
            );
            if(updateRow != 1) {
                throw new CanNotInsertException("Voucher를 업데이트할 수 없습니다.");
            }
        } catch (DataAccessException e) {
            logger.error("Voucher를 업데이트할 수 없습니다.");
            throw new CanNotUpdateException("Voucher를 업데이트할 수 없습니다.");
        }

    }

    @Override
    public void deleteAll() {
        try {
            int deleteRow = jdbcTemplate.update("delete from vouchers");
            if (deleteRow == 0) throw new CanNotDeleteException("Voucher들을 삭제할 수 없습니다.");
        } catch (DataAccessException e) {
            throw new CanNotDeleteException("Voucher들을 삭제할 수 없습니다.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        try {
            int deleteRow = jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(?)",
                    voucherId.toString().getBytes());
            if (deleteRow != 1) throw new CanNotDeleteException("Voucher를 삭제할 수 없습니다.");
        } catch (DataAccessException e) {
            throw new CanNotInsertException("Voucher를 삭제할 수 없습니다.");
        }
    }
}
