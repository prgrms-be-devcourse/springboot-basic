package kdt.vouchermanagement.domain.voucher.service;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public void deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new IllegalArgumentException("바우처가 존재하지 않습니다."));
        voucherRepository.deleteById(voucher.getVoucherId());
    }

    @Override
    public Voucher findVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new IllegalArgumentException("바우처가 존재하지 않습니다."));
        return voucher;
    }

    @Override
    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public List<Voucher> findVouchersByTypeAndDate(VoucherType type, LocalDate date) {
        return voucherRepository.findByTypeAndDate(type, date);
    }

    @Override
    public List<Voucher> findVouchersByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public List<Voucher> findVouchersByDate(LocalDate date) {
        return voucherRepository.findByDate(date);
    }
}
