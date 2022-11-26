package prgms.vouchermanagementapp.service;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.repository.VoucherRepository;

import java.util.List;

@Component
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(Amount fixedDiscountAmount) {
        Voucher fixedAmountVoucher = VoucherFactory.createVoucher(fixedDiscountAmount);
        save(fixedAmountVoucher);
    }

    public void createVoucher(Ratio fixedDiscountRatio) {
        Voucher percentDiscountVoucher = VoucherFactory.createVoucher(fixedDiscountRatio);
        save(percentDiscountVoucher);
    }

    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }
}
