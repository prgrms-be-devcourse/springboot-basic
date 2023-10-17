package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(int amount) {
        Voucher voucher = new FixedAmountVoucher(amount);
        voucherRepository.save(voucher);
    }

    public void createPercentDiscountVoucher(int percent) {
        Voucher voucher = new PercentDiscountVoucher(percent);
        voucherRepository.save(voucher);
    }
}
