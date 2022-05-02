package kdt.vouchermanagement.domain.voucher.service;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public void deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new IllegalArgumentException("바우처가 존재하지 않습니다."));
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public Voucher findVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new IllegalArgumentException("바우처가 존재하지 않습니다."));
        return voucher;
    }
}
