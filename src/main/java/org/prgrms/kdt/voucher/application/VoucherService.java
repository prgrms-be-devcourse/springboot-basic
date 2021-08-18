package org.prgrms.kdt.voucher.application;

import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.prgrms.kdt.exception.NotFoundException.ErrorMessage.NOT_FOUND_VOUCHER_MESSAGE;
import static org.prgrms.kdt.voucher.VoucherType.*;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("file") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_VOUCHER_MESSAGE));
    }

    public Voucher insert(Voucher voucher) throws IOException {
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> allVoucher() {
        return voucherRepository.findByAllVoucher();
    }

    public VoucherType choiceVoucher(String type) {
        return findByVoucherType(type);
    }

    public Voucher createVoucher(VoucherType type, String value) {
        if (type == FIXED) {
            return new FixedAmountVoucher(UUID.randomUUID(), parseLong(value));
        }
        return new PercentDiscountVoucher(UUID.randomUUID(), parseLong(value));
    }

    private long parseLong(String value) {
        return Long.parseLong(value);
    }


}
