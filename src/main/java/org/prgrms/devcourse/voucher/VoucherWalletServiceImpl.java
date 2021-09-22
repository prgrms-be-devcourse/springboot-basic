package org.prgrms.devcourse.voucher;

import org.prgrms.devcourse.customer.Customer;
import org.prgrms.devcourse.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherWalletServiceImpl implements VoucherWalletService {

    private final CustomerRepository customerRepository;
    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletServiceImpl(CustomerRepository customerRepository, VoucherWalletRepository voucherWalletRepository) {
        this.customerRepository = customerRepository;
        this.voucherWalletRepository = voucherWalletRepository;
    }

    @Override
    public VoucherUseInfo issueVoucher(Customer customer, Voucher voucher) {
        return voucherWalletRepository.insert(new VoucherUseInfo(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
    }

    @Override
    public List<VoucherUseInfo> findCustomerVoucherWallet(Customer customer) {
        return voucherWalletRepository.findByCustomerId(customer.getCustomerId());
    }

    @Override
    public List<Customer> findCustomerList(Voucher voucher) {
        var voucherUseInfoList = voucherWalletRepository.findByVoucherId(voucher.getVoucherId());
        List<Customer> customerList = new ArrayList<>();
        voucherUseInfoList.forEach(i -> customerList.add(customerRepository.findById(i.getCustomerId()).get()));
        return customerList;
    }

    @Override
    public UUID recallVoucher(VoucherUseInfo voucherUseInfo) {
        return voucherWalletRepository.delete(voucherUseInfo.getVoucherUseInfoId());
    }

    @Override
    public VoucherUseInfo extendVoucherExpirationDate(VoucherUseInfo voucherUseInfo, LocalDateTime expirationDate) {
        voucherUseInfo.setExpiredAt(expirationDate);
        return voucherWalletRepository.update(voucherUseInfo);
    }
}
