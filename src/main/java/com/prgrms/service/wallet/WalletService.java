package com.prgrms.service.wallet;

import com.prgrms.dto.customer.CustomerConverter;
import com.prgrms.dto.customer.CustomerResponse;
import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.dto.wallet.WalletConverter;
import com.prgrms.dto.wallet.WalletRequest;
import com.prgrms.model.KeyGenerator;
import com.prgrms.model.customer.Customer;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.wallet.Wallet;
import com.prgrms.repository.wallet.WalletRepository;
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

    public Wallet giveVoucher(int id, WalletRequest walletRequest) {
        Wallet wallet = walletConverter.convertWallet(id, walletRequest);

        return walletRepository.insert(wallet);
    }

    public Wallet takeVoucher(WalletRequest walletRequest) {
        int voucherId = walletRequest.voucherId();
        int customerId = walletRequest.customerId();

        return walletRepository.deleteWithVoucherIdAndCustomerId(voucherId, customerId);
    }

    public List<CustomerResponse> customerList(int voucherId) {
        List<Customer> customers = walletRepository.findAllCustomersByVoucher(voucherId);

        return customerConverter.convertCustomerResponse(customers);
    }

    public List<VoucherResponse> voucherList(int customerId) {
        Vouchers vouchers = walletRepository.findAllVouchersByCustomer(customerId);

        return voucherConverter.convertVoucherResponse(vouchers);
    }

}
