package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.User;
import team.marco.vouchermanagementsystem.repository.BlacklistRepository;
import team.marco.vouchermanagementsystem.repository.VoucherRepository;
import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final BlacklistRepository blacklistRepository;

    public VoucherService(VoucherRepository voucherRepository, BlacklistRepository blacklistRepository) {
        this.voucherRepository = voucherRepository;
        this.blacklistRepository = blacklistRepository;
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

    public List<String> getBlacklistUsers() {
        return blacklistRepository.findAll().stream()
                .map(User::getInfo)
                .toList();
    }
}
