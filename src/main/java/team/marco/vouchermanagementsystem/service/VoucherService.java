package team.marco.vouchermanagementsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.marco.vouchermanagementsystem.model.voucher.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.Voucher;
import team.marco.vouchermanagementsystem.repository.voucher.VoucherRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public void createFixedAmountVoucher(int amount) {
        Voucher voucher = new FixedAmountVoucher(amount);
        voucherRepository.save(voucher);
    }

    @Transactional
    public void createPercentDiscountVoucher(int percent) {
        Voucher voucher = new PercentDiscountVoucher(percent);
        voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchers(UUID customerId) {
        return voucherRepository.findByOwner(customerId);
    }

    @Transactional
    public void assignVoucherOwner(UUID voucherId, UUID customerId) {
        logger.debug("[VoucherService] Call assignVoucherOwner()");

        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow();
        voucher.assigneOwner(customerId);

        voucherRepository.update(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow();
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
