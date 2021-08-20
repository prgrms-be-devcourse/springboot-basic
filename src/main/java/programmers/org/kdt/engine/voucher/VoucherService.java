package programmers.org.kdt.engine.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;
import programmers.org.kdt.engine.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // eunu fix
    public Optional<Voucher> createVoucher(VoucherStatus voucherStatus, long value) {
        if (voucherStatus == VoucherStatus.FixedAmountVoucher) {
            var voucher = new FixedAmountVoucher(UUID.randomUUID(), value);
            if (voucher.conditionCheck()) {
                return voucherRepository.insert(voucher);
            }
        } else if (voucherStatus == VoucherStatus.PercentDiscountVoucher) {
            var voucher = new PercentDiscountVoucher(UUID.randomUUID(), value);
            if (voucher.conditionCheck()) {
                return voucherRepository.insert(voucher);
            }
        }
        return Optional.empty();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(()-> new RuntimeException(
                        MessageFormat.format("Can't find a voucher for {0}", voucherId)
                ));
    }

    public Set<Map.Entry<UUID, Voucher>> getRepositoryEntry() {
        return voucherRepository.getAllEntry();
    }

    public void useVoucher(Voucher voucher) {

    }
}
