package org.prgrms.kdt.voucher;

import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:52 오후
 */
@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public Voucher createVoucher(Voucher voucher) {
        logger.info("{} 바우처가 생성되었습니다.", voucher);
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}

