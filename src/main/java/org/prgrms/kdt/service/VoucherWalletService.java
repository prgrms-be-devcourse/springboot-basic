package org.prgrms.kdt.service;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherWalletService {

    JdbcWalletRepository jdbcWalletRepository;

    public VoucherWalletService(JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public Optional<Map<UUID, Voucher>> getVoucherListByCustomerEmail(String customerEmail) {
        Map<UUID, Voucher> voucherList = jdbcWalletRepository.getVoucherListByCustomerId(customerEmail);
        if(voucherList.size() == 0) {
            new OutputConsole().printMessage("WRONG : invalid input");
            return Optional.empty();
        }
        return Optional.of(voucherList);
    }
}
