package org.prgrms.orderapp.service;

import org.prgrms.orderapp.model.FixAmountVoucher;
import org.prgrms.orderapp.model.PercentDiscountVoucher;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.model.VoucherType;
import org.prgrms.orderapp.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)));
    }

    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public Optional<Voucher> createVoucher(String type, long value) {
        if (!checkValidity(type, value))
            return Optional.empty();
        else if (type.equalsIgnoreCase(VoucherType.FIXED.name()))
            return Optional.of(new FixAmountVoucher(UUID.randomUUID(), value));
        else if (type.equalsIgnoreCase(VoucherType.PERCENT.name()))
            return Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), value));
        else
            return Optional.empty();
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public boolean checkValidity(String type, long value) {
        if (!VoucherType.contains(type) || value < 0) {
            return false;
        }
        if (type.equalsIgnoreCase(VoucherType.PERCENT.name()) && value > 100) {
            return false;
        }
        return true;
    }
}
