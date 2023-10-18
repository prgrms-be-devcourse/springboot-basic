package team.marco.vouchermanagementsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.repository.VoucherRepository;
import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;

import java.util.List;

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

    public List<String> getVouchersInfo() {
        return voucherRepository.findAll().stream()
                .map(Voucher::getInfo)
                .toList();
    }
}
