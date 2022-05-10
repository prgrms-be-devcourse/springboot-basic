package com.prgrms.voucher_manager.infra.facade;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import com.prgrms.voucher_manager.wallet.Wallet;
import com.prgrms.voucher_manager.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Profile({"default","test","dev"})
public class VoucherServiceFacade {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;

    public VoucherServiceFacade(VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }
    public List<CustomerDto> findCustomerByVoucherType(String voucherType) {
        List<VoucherDto> vouchers = voucherService.findByType(voucherType);
        List<UUID> walletIds = walletService.getWalletIdByVoucherType(vouchers);
        return customerService.findCustomerByWallet(walletIds);
    }

    public List<VoucherDto> findVoucherByCustomerId(UUID customerId) {
        List<Wallet> wallets = walletService.findAllWallet(customerId);
        List<VoucherDto> vouchers = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        wallets.forEach(e -> {
            VoucherDto voucher = voucherService.findById(e.getVoucherId());
            vouchers.add(voucher);
            logger.info(i.getAndIncrement() + " : " + voucher.toString());
        });
        return vouchers;
    }


}

