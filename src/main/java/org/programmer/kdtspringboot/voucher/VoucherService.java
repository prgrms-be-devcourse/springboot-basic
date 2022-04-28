package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String type, Long discountValue) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        Voucher voucher = voucherType.create(discountValue);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public Optional<Voucher> findByIdVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }
}
