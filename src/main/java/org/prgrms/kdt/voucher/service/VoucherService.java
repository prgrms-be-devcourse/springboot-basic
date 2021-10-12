package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(final VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(final String voucherType, final long discountValue) {
        System.out.println(MessageFormat.format("{0}가 생성되었습니다.", voucherType));
        Voucher voucher = null;

        if (voucherType.equals("FixedAmountVoucher")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), discountValue);
            voucherRepository.insert(voucher);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountValue);
            voucherRepository.insert(voucher);
        } else {
            System.out.println("Voucher Type 입력값이 잘못되었습니다.");
        }

        return voucher;
    }

    public Voucher setCustomerEmail(final Voucher voucher, final String email) {
        return voucherRepository.updateEmail(voucher, email);
    }

    public List<Customer> getCustomer(final UUID voucherId) {
        return voucherRepository.findCustomer(voucherId);
    }

    public Optional<Voucher> getVoucher(final UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Optional<List<Voucher>> getAllVoucherOfCustomer(final String email) {
        return voucherRepository.findByEmail(email);
    }

    public void deleteVoucher(final UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public void useVoucher(Voucher voucher) {
    }
}
