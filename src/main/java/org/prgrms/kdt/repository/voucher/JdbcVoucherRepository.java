package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.VoucherException;
import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.exception.ErrorMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;


    public JdbcVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<VoucherEntity> voucherRowMapper = (resultSet, i) -> {
        final Long voucherId = resultSet.getLong("voucher_id");
        final String voucherType = resultSet.getString("voucher_type");
        Long amount = resultSet.getLong("discount_amount");
        String status = resultSet.getString("status");
        return new VoucherEntity(voucherId, voucherType, amount, status);
    };

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        int insert = jdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,discount_amount,status) VALUES (?,?,?,?)",
                voucherEntity.getVoucherEntityId(),
                voucherEntity.getVoucherEntityType(),
                voucherEntity.getEntityAmount(),
                voucherEntity.getStatus());
        if (insert != 1) {
            throw new VoucherException(ErrorMessage.INSERT_FAILED);
        }
        return voucherEntity;
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers",voucherRowMapper);
    }


    @Override
    public VoucherEntity findById(Long voucherId){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = ?",
                    voucherRowMapper, voucherId);
        } catch (EmptyResultDataAccessException e) {
            throw new VoucherException(ErrorMessage.NOT_FOUND_VOUCHER);
        }
    }

    @Override
    public List<VoucherEntity> findByType(String voucherType) {
        try {
            return jdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_type = ?",
                    voucherRowMapper, voucherType);
        } catch (EmptyResultDataAccessException e) {
            throw new VoucherException(ErrorMessage.NOT_FOUND_VOUCHER);
        }
    }

    @Override
    public void deleteById(Long voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = ?",
                voucherRowMapper,
                voucherId);
    }

}

