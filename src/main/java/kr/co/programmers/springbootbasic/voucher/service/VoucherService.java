package kr.co.programmers.springbootbasic.voucher.service;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherCreationRequestDto;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
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

    public VoucherDto createVoucher(VoucherCreationRequestDto requestDto) throws RuntimeException {
        logger.info("바우처를 생성합니다...");
        UUID voucherId = UUID.randomUUID();
        VoucherType type = requestDto.getType();
        long amount = requestDto.getAmount();

        Voucher voucher = switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount);
        };
        Voucher savedVoucher = voucherRepository.save(voucher);
        logger.info("바우처 생성에 성공했습니다.");

        return ApplicationUtils.convertToVoucherResponseDto(savedVoucher);
    }

    public List<VoucherDto> listAllVoucher() throws RuntimeException {
        logger.info("생성된 바우처를 조회합니다...");
        List<Voucher> vouchers = voucherRepository.listAll();

        return vouchers.stream()
                .map(ApplicationUtils::convertToVoucherResponseDto)
                .toList();
    }
}
