package org.programmers.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(@Qualifier("fileVoucherRepository") VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(String voucherType, long value) {
        Voucher voucher = voucherFactory.getVoucherType(voucherType, UUID.randomUUID(), value);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllVouchers();
    }

    public void useVoucher(Voucher voucher) {
    }

}
