package org.prgrms.kdt.service;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherWalletService {

    private final JdbcWalletRepository jdbcWalletRepository;

    public VoucherWalletService(JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public VoucherMap getVoucherListByCustomerEmail(String customerEmail) {
        Map<UUID, Voucher> vouchers = jdbcWalletRepository.getVoucherListByCustomerId(customerEmail);
        VoucherMap voucherMap = new VoucherMap(vouchers);
        return voucherMap;
    }
}
