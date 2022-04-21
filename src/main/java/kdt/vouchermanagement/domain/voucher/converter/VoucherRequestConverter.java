package kdt.vouchermanagement.domain.voucher.converter;

import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import org.springframework.stereotype.Component;

@Component
public class VoucherRequestConverter {

    public VoucherRequest of(int voucherTypeNum, int discountValue) {
        return new VoucherRequest(VoucherType.from(voucherTypeNum), discountValue);
    }
}
