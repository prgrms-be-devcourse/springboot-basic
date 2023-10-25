package org.prgrms.vouchermanager.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.MenuType;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.util.UuidUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
@Profile("jdbc")
//@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {
    private final String INSERT_VOUCHER = "INSERT INTO voucher(id, amount, voucher_type) VALUES(UUID_TO_BIN(?), ?, ?)";
    private final String SELECT_BY_ID = "select * from voucher where id = UUID_TO_BIN(?)";
    private final String SELECT_ALL = "select * from voucher";
    private final String DELETE_ALL = "delete from voucher";
    private final String DELETE_BY_ID = "delete from voucher where id = UUID_TO_BIN(?)";

    private final JdbcTemplate jdbcTemplate;


    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = UuidUtil.toUUID(resultSet.getBytes("id"));
        int amount = resultSet.getInt("amount");
        String type = resultSet.getString("voucher_type");
        MenuType menuType = MenuType.fromValue(type);
        if(menuType == MenuType.FIXED){
            return new FixedAmountVoucher(voucherId, amount, menuType);
        }
        else if(menuType == MenuType.PERCENT){
            return new PercentDiscountVoucher(voucherId, amount, menuType);
        }
        return null;
    };
    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_VOUCHER, voucher.getVoucherId().toString(), voucher.getAmount(), voucher.getType().toString());
        return voucher;
    }

    @Override
    public Optional<Voucher> findByID(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, voucherRowMapper, voucherId.toString()));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exist");
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_ID, voucherId.toString());
        Optional<Voucher> voucher = findByID(voucherId);
        return voucher;
    }
}
