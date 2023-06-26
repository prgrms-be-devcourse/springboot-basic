package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.dto.VoucherRequestDto;
import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.util.VoucherUtils;
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

    public VoucherResponseDto createVoucher(VoucherRequestDto requestDto) throws RuntimeException {
        logger.info("바우처를 생성합니다...");
        UUID voucherId = UUID.randomUUID();
        VoucherType type = requestDto.getType();
        long amount = requestDto.getAmount();

        Voucher voucher = switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount);
        };
        voucherRepository.save(voucherId, voucher);
        logger.info("바우처 생성에 성공했습니다.");

        return VoucherUtils.convertToVoucherResponseDto(voucher);
    }

    public List<VoucherResponseDto> listAllVoucher() {
        logger.info("생성된 바우처를 조회합니다...");
        List<Voucher> vouchers = voucherRepository.listAll();

        return vouchers.stream()
                .map(VoucherUtils::convertToVoucherResponseDto)
                .toList();
    }
}
