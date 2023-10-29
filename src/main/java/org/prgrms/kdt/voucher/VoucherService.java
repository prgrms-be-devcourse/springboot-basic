package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.Dto.FixedAmountVoucherDto;
import org.prgrms.kdt.voucher.Dto.PercentDiscountVoucherDto;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_FIND_VOUCHER;
import static org.prgrms.kdt.io.SystemMessage.FOUND_VOUCHER_IS_EMPTY;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private List<Voucher> voucherList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format( "{0}" + EXCEPTION_FIND_VOUCHER.getMessage(), voucherId);
                    logger.error(errorMessage);
                    return new RuntimeException(errorMessage);
                });
    }

    public void createVoucher(FixedAmountVoucherDto fixedAmountVoucherDto) {
        var voucher = new FixedAmountVoucher(fixedAmountVoucherDto.getVoucherId(), fixedAmountVoucherDto.getAmount());
        voucherRepository.insert(voucher);
    }

    public void createVoucher(PercentDiscountVoucherDto percentDiscountVoucherDto) {
        var voucher = new PercentDiscountVoucher(percentDiscountVoucherDto.getVoucherId(), percentDiscountVoucherDto.getPercent());
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers() {
        var voucherList = voucherRepository.findAll();
        if (voucherList.isEmpty()) {
            System.out.println(FOUND_VOUCHER_IS_EMPTY.getMessage());
        }
        return voucherList;
    }

    public void useVoucher(Voucher voucher) {
    }
}
