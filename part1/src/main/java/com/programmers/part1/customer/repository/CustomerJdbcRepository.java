package com.programmers.part1.customer.repository;

import com.programmers.part1.domain.VoucherWallet;
import com.programmers.part1.domain.customer.Customer;
import com.programmers.part1.exception.NoUpdateException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static com.programmers.part1.util.JdbcUtil.toUUID;
import static com.programmers.part1.util.JdbcUtil.toLocalDateTime;

/**
 * 현재 VoucherRepository와 customerRepository에서 voucher_wallets에 접근합니다...
 * customer나 voucher를 불러오는 부분은 그렇다 치더라도
 * deleteVoucherWalletByCustomerAndVoucherId : customerId와 voucherId를 통해 voucher 해제 메서드
 * 가 조금 기존의 레포지토리에서 어색한 느낌이 들어서 어떠신지 궁금합니다...
 * */
@Repository
@Primary
public class CustomerJdbcRepository implements CustomerRepository<UUID, Customer> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id,name,email,created_at) VALUES(UUID_TO_BIN(:customerId),:name,:email,:createdAt)",
                toParamMap(customer));

        if (update != 1)
            throw new NoUpdateException("고객을 저장하는데 실패하였습니다.");

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM  customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                        Collections.singletonMap("customerId", customerId.toString().getBytes()),
                        customerRowMapper));
    }

    @Override
    public List<Customer> findAllCustomer() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }


    @Override
    public List<Customer> findCustomerByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("SELECT c.customer_id, c.name, c.email, c.created_at, c.last_login_at FROM voucher_wallets AS vw JOIN customers AS c ON vw.customer_id = c.customer_id WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (update != 1)
            throw new NoUpdateException("고객의 정보를 수정하는데 실패하였습니다.");

        return customer;
    }

    @Override
    public void deleteAll() {
        int update = jdbcTemplate.update("DELETE FROM customers",
                Collections.emptyMap());

        if (update != 1)
            throw new NoUpdateException("전체 고객 삭제를 실패하였습니다.");
    }

    @Override
    public void deleteById(UUID customerId) {
        int update = jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()));

        if (update != 1)
            throw new NoUpdateException("고객이 삭제되지 않았습니다.");
    }

    @Override
    public VoucherWallet insertVoucherWallet(VoucherWallet voucherWallet) {
        int update = jdbcTemplate.update("INSERT INTO voucher_wallets(voucher_wallet_id,customer_id,voucher_id) VALUES(UUID_TO_BIN(:voucherWalletId),UUID_TO_BIN(:customerId),UUID_TO_BIN(:voucherId))",
                Map.of("voucherWalletId", voucherWallet.getVoucherWalletId().toString().getBytes(),
                        "customerId", voucherWallet.getCustomerId().toString().getBytes(),
                        "voucherId", voucherWallet.getVoucherId().toString().getBytes()));

        if (update != 1)
            throw new NoUpdateException("바우처를 지갑에 저장하는데 실패하였습니다.");

        return voucherWallet;
    }

    @Override
    public void deleteVoucherWalletByCustomerAndVoucherId(UUID customerId, UUID voucherId) {
        int update = jdbcTemplate.update("DELETE FROM voucher_wallets " +
                        "WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of("customerId", customerId.toString().getBytes(),
                        "voucherId", voucherId.toString().getBytes()));
        if (update != 1)
            throw new NoUpdateException("바우처를 지갑에서 삭제하는데 실패하였습니다.");
    }
    
    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime lastLoginAt = toLocalDateTime(resultSet.getTimestamp("last_login_at"));

        return Customer.builder()
                .customerId(customerId)
                .name(customerName)
                .email(email)
                .createdAt(createdAt)
                .lastLoginAt(lastLoginAt)
                .build();
    };
}
