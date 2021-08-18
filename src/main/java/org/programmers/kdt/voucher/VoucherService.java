package org.programmers.kdt.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private VoucherFactory voucherFactory;

    @Autowired
    public VoucherService(@Qualifier("Memory") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow( () -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)) );
    }

    public void useVoucher(Voucher voucher) {
    }

    public void addVoucher(Voucher voucher) {
        this.voucherRepository.save(voucher);
    }

    public Voucher createVoucher(UUID voucherId, long discount) {
        Voucher voucher = this.voucherFactory.createVoucher(voucherId, discount);
        this.addVoucher(voucher);
        return voucher;
    }

    public void setVoucherRepository(VoucherFactory voucherFactory) {
        this.voucherFactory = voucherFactory;
    }

    public List<Voucher> getAllVoucher() {
        return this.voucherRepository.findAll();
    }
}
