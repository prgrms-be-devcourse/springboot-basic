package org.programmers.springboot.basic.domain.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@ComponentScan(basePackages = {"org.programmers.springboot.basic.domain.voucher.mapper"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final DataSource dataSource;
    private final RowMapper<Voucher> voucherRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcVoucherRepository(@Qualifier("mysqlDataSource") DataSource dataSource, VoucherMapper voucherMapper) {
        this.dataSource = dataSource;
        this.voucherRowMapper = voucherMapper.voucherRowMapper();
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public void add(final Voucher voucher) {
        String sql = "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES(UUID_TO_BIN(?), ?, ?)";
        this.jdbcTemplate.update(sql, voucher.getVoucherId().toString().getBytes(), voucher.getVoucherTypeValue(), voucher.getDiscount());
    }

    @Override
    public Optional<Voucher> get(UUID voucherId) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, voucherRowMapper, (Object) voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No voucher found for voucherId: {}", voucherId);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
   }

    @Override
    public Optional<Voucher> findByTypeNDiscount(VoucherType voucherType, Long discount) {
        String sql = "SELECT * FROM vouchers WHERE voucher_type = ? AND discount = ?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, voucherRowMapper, voucherType.getValue(), discount));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No voucher found for voucherType {} and discount {}", voucherType, discount);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> getAll() {
        String sql = "SELECT * FROM vouchers ORDER BY voucher_id";
        return this.jdbcTemplate.query(sql, voucherRowMapper);
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE vouchers SET discount = ? WHERE voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, voucher.getDiscount(), voucher.getVoucherId().toString().getBytes());
    }

    @Override
    public void delete(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, (Object) voucherId.toString().getBytes());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        this.jdbcTemplate.update(sql);
    }
}
