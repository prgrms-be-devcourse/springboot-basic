package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.voucher.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.impl.PercentAmountVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType type, long amount) throws RuntimeException {
        logger.info("바우처를 생성합니다...");
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount);
        };
        voucherRepository.save(voucherId, voucher);
        logger.info("바우처 생성에 성공했습니다.");

        return voucher;
    }

    public List<Voucher> listAllVoucher() {
        logger.info("생성된 바우처를 조회합니다...");
        return voucherRepository.listAll();
    }
}
