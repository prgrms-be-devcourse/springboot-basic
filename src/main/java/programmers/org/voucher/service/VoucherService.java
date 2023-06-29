package programmers.org.voucher.service;

import org.springframework.stereotype.Service;
import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.FixedAmountVoucher;
import programmers.org.voucher.domain.PercentDiscountVoucher;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(int discount, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED: {
                Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
                saveVoucher(voucher);
                break;
            }
            case PERCENT: {
                Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
                saveVoucher(voucher);
                break;
            }
        }
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAll();
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
