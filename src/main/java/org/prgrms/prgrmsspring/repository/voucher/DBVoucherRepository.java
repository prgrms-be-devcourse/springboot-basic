package org.prgrms.prgrmsspring.repository.voucher;


import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.utils.BinaryToUUIDConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("prod")
@Repository
public class DBVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO VOUCHERS VALUES(UUID_TO_BIN(?), ?, ?)";
        int update = jdbcTemplate.update(sql, voucher.getVoucherId().toString(), voucher.getAmount(), voucher.getType());
        if (update != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM VOUCHERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            VoucherType voucherType = VoucherType.of(rs.getString("TYPE"));
            return voucherType.constructVoucher(new BinaryToUUIDConverter().run(rs.getBytes("VOUCHER_ID")), rs.getLong("AMOUNT"));
        });
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM VOUCHERS WHERE VOUCHER_ID = UUID_TO_BIN(?)";
        try {
            Voucher voucher = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                VoucherType voucherType = VoucherType.of(rs.getString("TYPE"));
                return voucherType.constructVoucher(new BinaryToUUIDConverter().run(rs.getBytes("VOUCHER_ID")), rs.getLong("AMOUNT"));
            }, voucherId);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
