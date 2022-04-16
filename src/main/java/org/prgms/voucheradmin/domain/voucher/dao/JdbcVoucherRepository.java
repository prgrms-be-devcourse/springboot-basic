package org.prgms.voucheradmin.domain.voucher.dao;

import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.CreationFailException;
import org.prgms.voucheradmin.global.exception.UpdateFailException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * 생성된 바우처를 DB에 CRUD를 하는 클래스 입니다.
 **/
@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 바우처 생성 메서드
     **/
    @Override
    public Voucher create(Voucher voucher) {
        int update = jdbcTemplate.update("insert into vouchers(voucher_id, voucher_type, voucher_amount) value(UUID_TO_BIN(?), ?, ?)",
                voucher.getVoucherId().toString().getBytes(), voucher.getVoucherType().name(), voucher.getAmount());

        if(update != 1) {
            throw new CreationFailException();
        }

        return voucher;
    }

    /**
     * 바우처 전체 조회 메서드
     **/
    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    /**
     * voucherId에 의한 바우처 조회 메서드
     **/
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(?)", voucherRowMapper, voucherId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    /**
     * 바우처 수정(voucher type, amount or percent)
     **/
    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("update vouchers set voucher_amount = ?, voucher_type = ? where voucher_id = UUID_TO_BIN(?)",
                voucher.getAmount(),
                voucher.getVoucherType().name(),
                voucher.getVoucherId().toString().getBytes());

        if(update != 1) {
            throw new UpdateFailException();
        }

        return voucher;
    }

    /**
     * 전달 받은 Entity를 이용하여 바우처르르 삭제합니다.
     */
    @Override
    public void delete(Voucher voucher) {
        jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(?)",
                voucher.getVoucherId().toString().getBytes());
    }

    /**
     * 조회 결과를 entity에 매핑하는 메서드
     **/
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));

        switch (voucherType) {
            case FIXED_AMOUNT:
                long amount = resultSet.getLong("voucher_amount");
                return new FixedAmountVoucher(voucherId, amount);
            default:
                long percent = resultSet.getLong("voucher_amount");
                return new PercentageDiscountVoucher(voucherId, percent);
        }
    };

    /**
     * UUID 변환 메서드
     **/
    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
