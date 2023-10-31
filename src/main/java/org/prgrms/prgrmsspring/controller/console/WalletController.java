package org.prgrms.prgrmsspring.controller.console;

import org.prgrms.prgrmsspring.dto.WalletDto;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController implements ApplicationController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public Wallet create(WalletDto walletDto) {
        return walletService.allocateVoucherToCustomer(walletDto.getCustomerId(), walletDto.getVoucherId());
    }

    public List<Voucher> findCustomerVouchers(UUID customerId) {
        return walletService.findVoucherListByCustomerId(customerId);
    }

    public void deleteCustomerVouchers(UUID customerId) {
        walletService.deleteVouchersByCustomerId(customerId);
    }

    public Customer findCustomerHasVoucher(UUID voucherId) {
        return walletService.findCustomerByVoucherId(voucherId);
    }
}
