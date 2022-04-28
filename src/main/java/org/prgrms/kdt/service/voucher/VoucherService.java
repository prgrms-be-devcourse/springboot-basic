package org.prgrms.kdt.service.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(UUID voucherId, long voucherValue, VoucherType voucherType) {
        Voucher voucher = voucherType.createVoucher(voucherId, voucherValue);
        return voucherRepository.insert(voucher);
    }

    public Voucher update(UUID voucherId, long voucherValue) {
        checkArgument(voucherId != null, "voucherId must be provided.");

        Voucher voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new IllegalArgumentException("Could not found voucher with voucherId=" + voucherId));

        voucher.changeValue(voucherValue);

        return voucherRepository.update(voucher);
    }

    public void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
