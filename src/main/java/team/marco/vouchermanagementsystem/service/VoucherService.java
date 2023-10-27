package team.marco.vouchermanagementsystem.service;

import java.util.List;
import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.repository.VoucherRepository;

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

    public List<Voucher> getVouchersInfo() {
        return voucherRepository.findAll();
    }
}
