package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException
                        (MessageFormat.format("바우처를 찾을 수 없습니다. {0}", voucherId)));
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, Long value) {
        var voucher = voucherType.create(value);
        return voucherRepository.insert(voucher);
    }
}
