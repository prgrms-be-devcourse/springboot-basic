package kr.co.programmers.springbootbasic.voucher.service;

import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("web")
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse createVoucher(VoucherType type, long amount) {
        logger.info("바우처를 생성합니다...");
        UUID voucherId = ApplicationUtils.createUUID();

        Voucher voucher = switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount);
        };
        Voucher createdVoucher = voucherRepository.create(voucher);
        logger.info("바우처 생성에 성공했습니다.");

        return ApplicationUtils.convertToVoucherResponse(createdVoucher);
    }

    public List<VoucherResponse> findByType(Integer typeId) {
        List<Voucher> response = voucherRepository.findByType(typeId);

        return response.stream()
                .map(ApplicationUtils::convertToVoucherResponse)
                .toList();
    }

    public List<VoucherResponse> findByDate(String formattedDate) {
        List<Voucher> response = voucherRepository.findByDate(formattedDate);

        return response.stream()
                .map(ApplicationUtils::convertToVoucherResponse)
                .toList();
    }

    public List<VoucherResponse> listAllVoucher() {
        logger.info("생성된 바우처를 조회합니다...");
        List<Voucher> vouchers = voucherRepository.listAll();

        return vouchers.stream()
                .map(ApplicationUtils::convertToVoucherResponse)
                .toList();
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public VoucherResponse findById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findVoucherById(voucherId);

        return voucher.map(ApplicationUtils::convertToVoucherResponse)
                .orElseGet(() -> new VoucherResponse(voucherId));
    }
}
