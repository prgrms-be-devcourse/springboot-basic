package org.prgrms.vouchermanager.repository.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
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
    private final String INSERT_Wallet = "INSERT INTO wallet(customer_email, voucher_id) VALUES(?, UUID_TO_BIN(?))";
    private final String SELECT_ALL = "select * from wallet";
    private final String SELECT_BY_CUSTOMER = "select * from wallet where customer_email = ?";
    private final String DELETE_BY_CUSTOMER = "delete from wallet where customer_email = ?";
    private final String SELECT_BY_VOUCHER = "select * from wallet w join voucher v on w.voucher_id = v.id " +
                                             "where v.voucher_type = ?";
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
    public WalletRequestDto save(WalletRequestDto walletRequestDto) {
        String customerEmail = walletRequestDto.getCustomerEmail();
        UUID voucherId = walletRequestDto.getVoucher().getVoucherId();
        jdbcTemplate.update(INSERT_Wallet, customerEmail, voucherId.toString());
        return walletRequestDto;
    }
    @Override
    public Optional<Wallet> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_CUSTOMER, walletRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
    @Override
    public Optional<Wallet> deleteByEmail(String email) {
        jdbcTemplate.update(DELETE_BY_CUSTOMER, email);
        return findByEmail(email);
    }
    @Override
    public Optional<Wallet> findByVoucher(Voucher voucher) {
        VoucherType type = voucher.getType();
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_VOUCHER, walletRowMapper, type.toString()));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
}
