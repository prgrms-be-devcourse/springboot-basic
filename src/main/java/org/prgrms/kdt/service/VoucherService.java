package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.exception.Message.CANNOT_FIND_VOUCHER;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(CANNOT_FIND_VOUCHER + "{0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
        //TODO
    }

    public Voucher create(String voucherType) {
        Voucher voucher = VoucherType.valueOf(voucherType).of();
        save(voucher);
        return voucher;
    }

    private void save(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

}
