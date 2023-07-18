package com.wonu606.vouchermanager.controller.voucher;

import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final VoucherWalletService walletService;

    public VoucherController(VoucherService voucherService, CustomerService customerService,
            VoucherWalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return voucherService.createVoucher(voucherDto);
    }

    public List<Voucher> getVoucherList() {
        return voucherService.getVoucherList();
    }

    public List<Customer> getCustomersOwnedByVoucherId(String voucherId) {
        List<String> customerEmailAddressList =
                walletService.findEmailAddressesByVoucherId(UUID.fromString(voucherId));
        return customerService.getCustomerList(
                customerEmailAddressList.stream()
                        .map(Email::new)
                        .collect(Collectors.toList()));
    }

    public void assignWallet(VoucherWalletDto walletDto) {
        walletService.save(walletDto);
    }
}
