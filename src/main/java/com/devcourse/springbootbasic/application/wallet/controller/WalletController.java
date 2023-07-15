package com.devcourse.springbootbasic.application.wallet.controller;

import com.devcourse.springbootbasic.application.customer.controller.CustomerDto;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.devcourse.springbootbasic.application.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void assignVoucherToCustomer(UUID voucherId, UUID customerId) {
        walletService.updateCustomerIdOfVoucher(voucherId, customerId);
    }

    public void withdrawVoucherFromCustomer(UUID voucherId) {
        walletService.updateCustomerIdNullOfVoucher(voucherId);
    }

    public List<VoucherDto> voucherListOfCustomer(UUID customerId) {
        return walletService.findVouchersByCustomerId(customerId).stream()
                .map(VoucherDto::of)
                .toList();
    }

    public CustomerDto customerHasVoucher(UUID voucherId) {
        return CustomerDto.of(walletService.findCustomerByVoucherId(voucherId));
    }

    public List<CustomerDto> customerListHasVoucherType(VoucherType voucherType) {
        return walletService.findCustomersByVoucherType(voucherType).stream()
                .map(CustomerDto::of)
                .toList();
    }
}
