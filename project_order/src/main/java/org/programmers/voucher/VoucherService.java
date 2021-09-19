package org.programmers.voucher;

import org.programmers.customer.Customer;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long value) {
        Voucher voucher = voucherFactory.createVoucherByType(voucherType, voucherId, value);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByOwnerId(UUID ownerId) {
        return voucherRepository.findByOwnerId(ownerId);
    }

    public List<Voucher> getVouchersByType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public void assignToCustomer(Customer customer, Voucher voucher) {
        voucherRepository.assignToCustomer(customer, voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public void useVoucher(Voucher voucher) {
    }

}
