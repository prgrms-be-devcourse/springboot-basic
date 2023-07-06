package prgms.spring_week1.domain.voucher.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertFixedAmountVoucher(Long discountAmount) {
        voucherRepository.insert(new FixedAmountVoucher(discountAmount));
    }

    public void insertPercentDiscountVoucher(int discountPercent) {
        voucherRepository.insert(new PercentDiscountVoucher(discountPercent));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
