package org.programmers.springorder.service;

import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.dto.wallet.WalletRequestDto;
import org.programmers.springorder.model.Wallet;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletService(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void assignVoucher(WalletRequestDto walletDto) {
        Customer customer = customerService.findById(walletDto.getCustomerId());
        Voucher voucher = voucherService.findById(walletDto.getVoucherId());
        voucherService.assignVoucherToCustomer(walletDto.getCustomerId(), walletDto.getVoucherId());

        Wallet wallet = new Wallet(customer);
        wallet.addVoucher(voucher);
    }

    public List<VoucherResponseDto> getVoucher(UUID customerId) {
        customerService.findById(customerId);
        return voucherService.getVoucherByCustomerId(customerId);
    }
}
