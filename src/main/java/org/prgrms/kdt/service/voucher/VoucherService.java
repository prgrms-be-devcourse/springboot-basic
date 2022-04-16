package org.prgrms.kdt.service.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherFactory;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(UUID voucherId, long voucherValue, VoucherType voucherType) {
        Voucher voucher = VoucherFactory.create(voucherId, voucherValue, voucherType);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
