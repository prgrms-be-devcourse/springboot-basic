package org.prgrms.orderapp.service;

import org.prgrms.orderapp.FixAmountVoucher;
import org.prgrms.orderapp.PercentDiscountVoucher;
import org.prgrms.orderapp.Voucher;
import org.prgrms.orderapp.VoucherType;
import org.prgrms.orderapp.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public Voucher createVoucher(String type, long value) {
        if (type.equalsIgnoreCase(VoucherType.FIXED.name())) {
            return new FixAmountVoucher(UUID.randomUUID(), value);
        } else if (type.equalsIgnoreCase(VoucherType.PERCENT.name())) {
            return new PercentDiscountVoucher(UUID.randomUUID(), value);
        } else {
            throw new RuntimeException(MessageFormat.format("Invalid type of voucher: {0}", type));
        }
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public boolean checkValidity(String type) {
        return VoucherType.contains(type);
    }
    public boolean checkValidity(String type, long value) {
        if (!VoucherType.contains(type) || value < 0) return false;
        if (type.equalsIgnoreCase(VoucherType.PERCENT.name()) && value > 100) return false;
        return true;
    }
}
