package com.prgrms.wallet.service;

import com.prgrms.custoemer.dto.CustomerConverter;
import com.prgrms.custoemer.dto.CustomerResponse;
import com.prgrms.voucher.service.mapper.VoucherConverter;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import com.prgrms.custoemer.model.Customer;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.wallet.model.Wallet;
import com.prgrms.wallet.repository.WalletRepository;
import com.prgrms.wallet.service.mapper.WalletConverter;
import com.prgrms.wallet.service.dto.WalletServiceRequest;
import com.prgrms.wallet.service.dto.WalletServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherConverter voucherConverter;
    private final WalletConverter walletConverter;
    private final CustomerConverter customerConverter;

    public WalletService(WalletRepository walletRepository,
            VoucherConverter voucherConverter, WalletConverter walletConverter,
            CustomerConverter customerConverter) {
        this.walletRepository = walletRepository;
        this.voucherConverter = voucherConverter;
        this.walletConverter = walletConverter;
        this.customerConverter = customerConverter;
    }

    public WalletServiceResponse giveVoucher(String walletId, WalletServiceRequest walletServiceRequest) {
        Wallet wallet = walletConverter.convertWallet(walletId, walletServiceRequest);
        walletRepository.insert(wallet);

        return new WalletServiceResponse(wallet);
    }

    public WalletServiceResponse takeVoucher(WalletServiceRequest walletServiceRequest) {
        String voucherId = walletServiceRequest.voucherId();
        String customerId = walletServiceRequest.customerId();
        walletRepository.deleteWithVoucherIdAndCustomerId(voucherId, customerId);

        return new WalletServiceResponse(customerId,voucherId,false);
    }

    public List<CustomerResponse> customerList(String voucherId) {
        List<Customer> customers = walletRepository.findAllCustomersByVoucher(voucherId);

        return customerConverter.convertCustomerResponse(customers);
    }

    public List<VoucherServiceResponse> voucherList(String customerId) {
        Vouchers vouchers = walletRepository.findAllVouchersByCustomer(customerId);

        return voucherConverter.convertVoucherResponses(vouchers);
    }

}
