package prgms.springbasic.voucher;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import prgms.springbasic.repository.VoucherRepository;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@Qualifier("voucherService")
public class VoucherServiceImpl implements VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, String value) {
        if (voucherType == VoucherType.FIXEDAMOUNTVOUCHER) {
            return createFixedAmountVoucher(voucherId, Integer.parseInt(value));
        } else if (voucherType == VoucherType.PERCENTDISCOUNTVOUCHER) {
            return createPercentDiscountVoucher(voucherId, Long.parseLong(value));
        }
        return null;
    }

    @Override
    public List<Voucher> getVoucherList() throws IOException {
        return voucherRepository.getVoucherList();
    }

    public Voucher createFixedAmountVoucher(UUID voucherId, int amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    public Voucher createPercentDiscountVoucher(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    public Voucher getVoucher(UUID voucherId) throws IOException {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("해당 바우처를 찾을 수 없습니다. 바우처 ID = {0}", voucherId))
                );
    }
}
