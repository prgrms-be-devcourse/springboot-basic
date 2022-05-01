package org.prgrms.voucherapp.engine.wallet.repository;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.repository.JdbcCustomerRepository;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.repository.JdbcVoucherRepository;
import org.prgrms.voucherapp.engine.wallet.dto.CustomerVoucherDto;
import org.prgrms.voucherapp.engine.wallet.service.WalletService;
import org.prgrms.voucherapp.engine.wallet.vo.Wallet;
import org.prgrms.voucherapp.exception.SqlStatementFailException;
import org.prgrms.voucherapp.global.Util;
import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
// Q. findVouchersByCustomerId, findCustomersByVoucherId에서 다른 repository의 rowMapper를 사용했는데 이렇게 사용해도 문제가 없을까요?
// 가지고 오는 테이블 행이 vouchers와 customers와 똑같아서 같은 rowMapper를 불필요하게 정의하는 것을 막기 위해 사용했습니다.
public class JdbcWalletRepository implements  WalletRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final static Logger logger = getLogger(JdbcWalletRepository.class);


    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CustomerVoucherDto> walletRowMapper = (resultSet, rowNum) -> {
        UUID customerId = Util.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String status = resultSet.getString("status");
        String type = resultSet.getString("type");
        int amount = resultSet.getInt("amount");
        UUID voucherId = Util.toUUID(resultSet.getBytes("voucher_id"));
        return new CustomerVoucherDto(customerId, voucherId, name, status, type, amount);
    };

    private Map<String, Object> toParamMap(Wallet wallet){
        return new HashMap<>() {{
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("walletId", wallet.getWalletId().toString().getBytes());
        }};
    }

    private Map<String, Object> toParamMap(UUID voucherId, UUID customerId){
        return new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("customerId", customerId.toString().getBytes());
        }};
    }

    @Override
    public Wallet insert(Wallet wallet) {
        logger.info("walletId : {}, customerId : {}, voucherId : {}", wallet.getWalletId(), wallet.getCustomerId(), wallet.getVoucherId());

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
    public Optional<CustomerVoucherDto> findByBothId(UUID voucherId, UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select w.voucher_id, type, amount, w.customer_id, name, status from voucher_wallets w join customers c on w.customer_id=c.customer_id join vouchers v on w.voucher_id=v.voucher_id where w.voucher_id = UUID_TO_BIN(:voucherId) and w.customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(voucherId, customerId),
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
        if (delete < 0) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        int delete = jdbcTemplate.update("delete from voucher_wallets where voucher_id = UUID_TO_BIN(:voucherId)",Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (delete < 0) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }

    @Override
    public void deleteByWalletId(UUID walletId) {
        int delete = jdbcTemplate.update("delete from voucher_wallets where wallet_id = UUID_TO_BIN(:walletId)",Collections.singletonMap("walletId", walletId.toString().getBytes()));
        if (delete != 1) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }

    @Override
    public void deleteByBothId(UUID voucherId, UUID customerId){
        int delete = jdbcTemplate.update("delete from voucher_wallets where customer_id = UUID_TO_BIN(:customerId) and voucher_id = UUID_TO_BIN(:voucherId)", toParamMap(voucherId, customerId));
        if (delete != 1) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }
}
