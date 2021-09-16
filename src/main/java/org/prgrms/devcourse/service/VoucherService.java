package org.prgrms.devcourse.service;

import org.prgrms.devcourse.domain.Voucher;
import org.prgrms.devcourse.repository.VoucherRepository;
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
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }
}
