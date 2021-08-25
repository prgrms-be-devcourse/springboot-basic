package programmers.org.kdt.engine.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;
import programmers.org.kdt.engine.voucher.repository.VoucherRepository;
import programmers.org.kdt.engine.voucher.type.FixedAmountVoucher;
import programmers.org.kdt.engine.voucher.type.PercentDiscountVoucher;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(VoucherStatus voucherStatus, long value) {
        VoucherStatus voucherStatusData = VoucherStatus.NULL;
        Voucher voucher = null;
        try {
            voucherStatusData = VoucherStatus.valueOf(voucherStatus.name());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        switch (voucherStatusData) {
            case PercentDiscountVoucher -> voucher = new FixedAmountVoucher(UUID.randomUUID(), value);
            case FixedAmountVoucher -> voucher = new PercentDiscountVoucher(UUID.randomUUID(), value);
        }

        if (voucher == null || !voucher.conditionCheck()) {
            return Optional.empty();
        }

        logger.debug("Create Voucher success");
        return voucherRepository.insert(voucher);
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
