package com.prgms.management.voucher_wallet.repository;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.repository.CustomerRepository;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.repository.VoucherRepository;
import com.prgms.management.voucher_wallet.entity.VoucherWallet;
import com.prgms.management.voucher_wallet.exception.VoucherWallerException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public VoucherWallet giveVoucherToCustomer(VoucherWallet voucherWallet) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("id", voucherWallet.getId().toString());
            put("voucherId", voucherWallet.getVoucher().getVoucherId().toString());
            put("customerId", voucherWallet.getCustomer().getId().toString());
        }};
        int result = jdbcTemplate.update("INSERT INTO voucher_wallet(id, customer_id, voucher_id) " +
                        "VALUES (UNHEX(REPLACE(:id, '-', '')), UNHEX(REPLACE(:customerId, '-', '')), UNHEX(REPLACE(:voucherId, '-', '')))",
                paramMap);
        if (result == 1) {
            return voucherWallet;
        }
        throw new VoucherWallerException("바우처 지갑 저장에 실패하였습니다.");
    }

    @Override
    public List<VoucherWallet> findByCustomer(Customer customer) {
        return jdbcTemplate.query("SELECT * from voucher_wallet where customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", customer.getId().toString()),
                (rs, rowNum) -> mapToVoucherWallet(rs));
    }

    @Override
    public Customer findCustomerByVoucherId(UUID voucherId) {
        VoucherWallet result = jdbcTemplate.queryForObject("SELECT * from voucher_wallet where voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", voucherId.toString()),
                (rs, rowNum) -> mapToVoucherWallet(rs));
        return result != null ? result.getCustomer() : null;
    }

    private UUID toUUID(byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    private VoucherWallet mapToVoucherWallet(ResultSet set) throws SQLException {
        UUID id = toUUID(set.getBytes("id"));
        UUID customerId = toUUID(set.getBytes("customer_id"));
        UUID voucherId = toUUID(set.getBytes("voucher_id"));
        Customer customer = customerRepository.findById(customerId);
        Voucher voucher = voucherRepository.findById(voucherId);
        return new VoucherWallet(id, customer, voucher);
    }
}
