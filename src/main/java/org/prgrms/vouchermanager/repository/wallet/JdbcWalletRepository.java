package org.prgrms.vouchermanager.repository.wallet;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.MenuType;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.WalletRequest;
import org.prgrms.vouchermanager.util.UuidUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
@Profile("jdbc")
public class JdbcWalletRepository implements WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        int walletId = resultSet.getInt("id");
        String customerEmail = resultSet.getString("customer_email");
        UUID voucherId = UuidUtil.toUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(walletId, customerEmail, voucherId);
    };
    @Override
    public WalletRequest save(WalletRequest walletRequest) {
        String customerEmail = walletRequest.getCustomerEmail();
        UUID voucherId = walletRequest.getVoucher().getVoucherId();
        jdbcTemplate.update("INSERT INTO wallet(customer_email, voucher_id) VALUES(?, UUID_TO_BIN(?))", customerEmail, voucherId.toString());
        return walletRequest;
    }
    @Override
    public Optional<Wallet> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallet where customer_email = ?", walletRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
    @Override
    public Optional<Wallet> deleteByEmail(String email) {
        jdbcTemplate.update("delete from wallet where customer_email = ?", email);
        return findByEmail(email);
    }
    @Override
    public Optional<Wallet> findByVoucher(Voucher voucher) {
        MenuType type = voucher.getType();
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallet w join voucher v on w.voucher_id = v.id " +
                    "where v.voucher_type = ?", walletRowMapper, type.toString()));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
}
