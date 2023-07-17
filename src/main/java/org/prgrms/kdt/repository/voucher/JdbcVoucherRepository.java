package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.utils.VoucherType;
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
        boolean status = resultSet.getBoolean("status");
        return new VoucherEntity(voucherId, voucherType, amount, status);
    };

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        int insert = jdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,discount_amount,status) VALUES (?,?,?,?)",
                voucherEntity.getVoucherEntityId(),
                voucherEntity.getVoucherEntityType(),
                voucherEntity.getEntityAmount(),
                voucherEntity.isEntityStatus());
        if (insert != 1) {
            throw new RuntimeException("추가된 데이터 내역이 없습니다.");
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
            throw new RuntimeException("결과값이 없습니다!");
        }
    }

    @Override
    public List<VoucherEntity> findByType(String voucherType) {
        try {
            return jdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_type = ?",
                    voucherRowMapper, voucherType);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("결과값이 없습니다!");
        }
    }

    @Override
    public void deleteById(Long voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = ?",
                voucherRowMapper,
                voucherId);
    }

}

