package org.prgrms.prgrmsspring.repository.voucher;


import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("prod")
@Repository
public class DBVoucherRepository implements VoucherRepository {

    private JdbcTemplate jdbcTemplate;

    public DBVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO VOUCHERS VALUES(UUID_TO_BIN(?), ?)";
        int update = jdbcTemplate.update(sql, voucher.getVoucherId().toString(), voucher.getAmount());
        if (update != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
}
