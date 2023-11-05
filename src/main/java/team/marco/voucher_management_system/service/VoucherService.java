package team.marco.voucher_management_system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.PercentDiscountVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.VoucherRepository;

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

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }
}
