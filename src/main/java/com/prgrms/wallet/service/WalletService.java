package com.prgrms.wallet.service;

import com.prgrms.custoemer.repository.CustomerConverter;
import com.prgrms.custoemer.dto.CustomerResponse;
import com.prgrms.voucher.service.VoucherConverter;
import com.prgrms.voucher.service.VoucherResponse;
import com.prgrms.wallet.dto.WalletRequest;
import com.prgrms.common.KeyGenerator;
import com.prgrms.custoemer.model.Customer;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.wallet.model.Wallet;
import com.prgrms.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherConverter voucherConverter;
    private final WalletConverter walletConverter;
    private final CustomerConverter customerConverter;

    public WalletService(WalletRepository walletRepository, KeyGenerator keyGenerator,
            VoucherConverter voucherConverter, WalletConverter walletConverter,
            CustomerConverter customerConverter) {
        this.walletRepository = walletRepository;
        this.voucherConverter = voucherConverter;
        this.walletConverter = walletConverter;
        this.customerConverter = customerConverter;
    }

    public WalletResponse giveVoucher(int id, WalletRequest walletRequest) {
        Wallet wallet = walletConverter.convertWallet(id, walletRequest);
        walletRepository.insert(wallet);

        return new WalletResponse(wallet);
    }

    public WalletResponse takeVoucher(WalletRequest walletRequest) {
        int voucherId = walletRequest.voucherId();
        int customerId = walletRequest.customerId();
        walletRepository.deleteWithVoucherIdAndCustomerId(voucherId, customerId);

        return new WalletResponse(customerId,voucherId,false);
    }

    public List<CustomerResponse> customerList(int voucherId) {
        List<Customer> customers = walletRepository.findAllCustomersByVoucher(voucherId);

        return customerConverter.convertCustomerResponse(customers);
    }

    public List<VoucherResponse> voucherList(int customerId) {
        Vouchers vouchers = walletRepository.findAllVouchersByCustomer(customerId);

        return voucherConverter.convertVoucherResponses(vouchers);
    }

}
