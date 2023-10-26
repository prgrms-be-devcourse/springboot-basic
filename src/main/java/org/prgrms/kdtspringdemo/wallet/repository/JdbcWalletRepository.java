package org.prgrms.kdtspringdemo.wallet.repository;

import com.google.gson.Gson;
import org.prgrms.kdtspringdemo.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.domain.dto.WalletDBDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("DB")
public class JdbcWalletRepository implements WalletRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final Gson gson = new Gson();
    private static final RowMapper<WalletDBDto> customerRowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        return new WalletDBDto(walletId, customerId);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Autowired
    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, vouchers) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), ?)",
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes(),
                gson.toJson(wallet.getVouchers()));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }

    @Override
    public List<Voucher> findAllVoucherByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public void deleteVoucherByVoucherId(UUID voucherId) {

    }

    @Override
    public void findCustomerByVoucherId(UUID voucherId) {

    }
}
