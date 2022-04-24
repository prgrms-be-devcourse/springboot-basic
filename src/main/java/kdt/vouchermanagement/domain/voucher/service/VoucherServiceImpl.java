package kdt.vouchermanagement.domain.voucher.service;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.exception.DuplicateVoucherException;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException("애플리케이션이 null을 사용하려고 합니다.");
        }

        voucher.validateValueRange();
        validateDuplicateVoucher(voucher);
        return voucherRepository.save(voucher);
    }

    private void validateDuplicateVoucher(Voucher voucher) {
        Optional<Voucher> foundVoucher = voucherRepository.findByVoucherTypeAndDiscountValue(voucher.getVoucherType(), voucher.getDiscountValue());
        foundVoucher.ifPresent(v -> {
            throw new DuplicateVoucherException("입력한 VoucherType과 Discount 값이 중복되었습니다.");
        });
    }
}
