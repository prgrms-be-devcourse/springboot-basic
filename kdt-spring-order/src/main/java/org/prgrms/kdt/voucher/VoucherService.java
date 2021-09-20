package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Map<UUID, Voucher> getVoucherAll() {
        return voucherRepository.getVoucherAll();
    }

    public Voucher createFixVoucher(String amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, Long.parseLong(amount));
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentVoucher(String percent) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId, Long.parseLong(percent));
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createVoucher(VoucherType type, String amount) {
        if (type == VoucherType.FIXED) {
            return createFixVoucher(amount);
        } else {
            return createPercentVoucher(amount);
        }
    }

    public HashMap<UUID, Voucher> getVoucherByCustomerEmail(String inputCustomerEmail) {
        return voucherRepository.getVoucherListByCustomerEmail(inputCustomerEmail);
    }

    public void updateAssignVoucher(Voucher voucher) {
        voucherRepository.updateAssignVoucher(voucher);
    }

}
