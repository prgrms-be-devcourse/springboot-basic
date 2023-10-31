package team.marco.voucher_management_system.service.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.voucher.FixedAmountVoucher;
import team.marco.voucher_management_system.domain.voucher.PercentDiscountVoucher;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.repository.voucher.VoucherRepository;

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
    public Voucher createFixedAmountVoucher(int amount) {
        Voucher voucher = new FixedAmountVoucher(amount);
        return voucherRepository.save(voucher);
    }

    @Transactional
    public Voucher createPercentDiscountVoucher(int percent) {
        Voucher voucher = new PercentDiscountVoucher(percent);
        return voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchers(UUID customerId) {
        return voucherRepository.findByOwner(customerId);
    }

    @Transactional
    public Voucher assignVoucherOwner(UUID voucherId, UUID customerId) {
        logger.debug("[VoucherService] Call assignVoucherOwner()");

        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow();
        voucher.assigneOwner(customerId);

        return voucherRepository.update(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow();
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
