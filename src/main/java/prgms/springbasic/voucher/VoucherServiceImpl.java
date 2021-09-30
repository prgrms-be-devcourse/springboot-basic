package prgms.springbasic.voucher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import prgms.springbasic.io.Printer;
import prgms.springbasic.repository.VoucherRepository;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@Qualifier("voucherService")
public class VoucherServiceImpl implements VoucherService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(@Qualifier("fileVoucherRepository") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, String value) {
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER) {
            return createFixedAmountVoucher(voucherId, Long.parseLong(value));
        } else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER) {
            return createPercentDiscountVoucher(voucherId, Long.parseLong(value));
        }
        throw new RuntimeException(MessageFormat.format("바우처를 생성할 수 없습니다. 바우처 타입 = {0}, 바우처 아이디 = {0}, 할인값 = {0}", voucherType, voucherId, value));
    }

    @Override
    public List<Voucher> getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    @Override
    public void listIsEmpty() {
        Printer printer = new Printer();
        printer.printVoucherListEmpty();
    }

    public Voucher createFixedAmountVoucher(UUID voucherId, long amount) {
        Voucher newVoucher = new FixedAmountVoucher(voucherId, amount);
        return voucherRepository.save(newVoucher);
    }

    public Voucher createPercentDiscountVoucher(UUID voucherId, long percent) {
        Voucher newVoucher = new PercentDiscountVoucher(voucherId, percent);
        return voucherRepository.save(newVoucher);
    }

    public Voucher getVoucher(UUID voucherId) throws IOException {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("해당 바우처를 찾을 수 없습니다. 바우처 ID = {0}", voucherId)));
    }
}
