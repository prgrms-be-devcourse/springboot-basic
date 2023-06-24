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
import java.util.stream.Collectors;

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
        Voucher createdVoucher = switch (requestDto.getType()) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, requestDto.getAmount());
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, requestDto.getAmount());
        };
        voucherRepository.save(voucherId, createdVoucher);
        logger.info("바우처 생성에 성공했습니다.");

        return createdVoucher.getVoucherInfo();
    }

    public String listAllVoucher() {
        logger.info("생성된 바우처를 조회합니다...");
        List<Voucher> vouchers = voucherRepository.listAll();
        if (vouchers.isEmpty()) {
            return VoucherValue.NO_SAVED_VOUCHER;
        }

        return formatVoucherInfo(vouchers);
    }

    private String formatVoucherInfo(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(voucher -> VoucherUtils.formatVoucherResponseDto(voucher.getVoucherInfo()))
                .collect(Collectors.joining());
    }
}
