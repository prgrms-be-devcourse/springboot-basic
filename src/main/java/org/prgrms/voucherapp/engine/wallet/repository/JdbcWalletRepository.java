package org.prgrms.voucherapp.engine.wallet.repository;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.repository.JdbcCustomerRepository;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.repository.JdbcVoucherRepository;
import org.prgrms.voucherapp.engine.wallet.dto.CustomerVoucherDto;
import org.prgrms.voucherapp.engine.wallet.vo.Wallet;
import org.prgrms.voucherapp.exception.SqlStatementFailException;
import org.prgrms.voucherapp.global.Util;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcWalletRepository implements  WalletRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<CustomerVoucherDto> walletRowMapper = (resultSet, rowNum) -> {
        var customerId = Util.toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var status = resultSet.getString("status");
        var type = resultSet.getString("type");
        var amount = resultSet.getInt("amount");
        var voucherId = Util.toUUID(resultSet.getBytes("voucher_id"));
        return new CustomerVoucherDto(customerId, voucherId, name, status, type, amount);
    };


    private Map<String, Object> toParamMap(CustomerVoucherDto walletDto){
        return new HashMap<String, Object>(){{
            put("customerId", walletDto.getCustomerId().toString().getBytes());
            put("name", walletDto.getName());
            put("status", walletDto.getStatus());
            put("voucherId", walletDto.getVoucherId());
            put("type", walletDto.getVoucherType());
            put("amount", walletDto.getDiscountAmount());
        }};
    }

    private Map<String, Object> toParamMap(Wallet wallet){
        return new HashMap<String, Object>(){{
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("walletId", wallet.getWalletId().toString().getBytes());
        }};
    }

    @Override
    public Wallet insert(Wallet wallet) {
        int insert = jdbcTemplate.update("insert into voucher_wallets(customer_id, voucher_id, wallet_id) values(UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), UUID_TO_BIN(:walletId))", toParamMap(wallet));
        if (insert != 1) throw new SqlStatementFailException("정상적으로 삽입되지 않았습니다.");
        return wallet;
    }

    @Override
    public List<CustomerVoucherDto> findAll() {
        return jdbcTemplate.query("select w.voucher_id, type, amount, w.customer_id, name, status from voucher_wallets w join customers c on w.customer_id=c.customer_id join vouchers v on w.voucher_id=v.voucher_id",walletRowMapper);
    }

    @Override
    public Optional<CustomerVoucherDto> findById(UUID walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select w.voucher_id, type, amount, w.customer_id, name, status from voucher_wallets w join customers c on w.customer_id=c.customer_id join vouchers v on w.voucher_id=v.voucher_id where wallet_id = UUID_TO_BIN(:walletId)",
                    Collections.singletonMap("walletId", walletId.toString().getBytes()),
                    walletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select v.* from voucher_wallets w join vouchers v on w.voucher_id=v.voucher_id where customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                JdbcVoucherRepository.getVoucherRowMapper());
    }

    @Override
    public List<Customer> findCustomersByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("select c.* from voucher_wallets w join customers c on w.customer_id=c.customer_id where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                JdbcCustomerRepository.getCustomerRowMapper());
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        int delete = jdbcTemplate.update("delete from voucher_wallets where customer_id = UUID_TO_BIN(:customerId)",Collections.singletonMap("customerId", customerId.toString().getBytes()));
        if (delete <= 0) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        int delete = jdbcTemplate.update("delete from voucher_wallets where voucher_id = UUID_TO_BIN(:voucherId)",Collections.singletonMap("customerId", voucherId.toString().getBytes()));
        if (delete <= 0) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }

    @Override
    public void deleteByWalletId(UUID walletId) {
        int delete = jdbcTemplate.update("delete from voucher_wallets where wallet_id = UUID_TO_BIN(:walletId)",Collections.singletonMap("walletId", walletId.toString().getBytes()));
        if (delete != 1) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }
}
