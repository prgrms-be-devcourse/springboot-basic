package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, Long discountInformation) {
        Voucher voucher = switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(UUID.randomUUID(), discountInformation, voucherType);
            case PERCENT -> PercentDiscountVoucher.of(UUID.randomUUID(), discountInformation, voucherType);
            default -> throw new IllegalStateException("Unexpected value: " + voucherType);
        };

        voucherRepository.insert(voucher);
        return voucher;
    }

    public Optional<Voucher> findVoucher(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public boolean deleteVoucher(UUID voucherId) {
        return voucherRepository.deleteVoucherById(voucherId);
    }

    public Voucher provideVoucherToCustomer(Voucher voucher, Customer customer){
        voucher.provideToCustomer(customer.getCustomerId());
        return voucherRepository.update(voucher);
    }

    public List<Voucher> voucherListByCustomer(Customer customer) {
        return voucherRepository.findVoucherListByCustomer(customer);
    }

    public void deleteVoucherByCustomer(Voucher voucher, Customer customer) {
        voucherRepository.findVoucherListByCustomer(customer);
    }
}
