package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.common.exception.HaveNoVoucherException;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.dto.VoucherInfo;
import com.prgrms.springbootbasic.voucher.dto.VoucherResponse;
import com.prgrms.springbootbasic.voucher.storage.VoucherStorage;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class VoucherManager {

    private final VoucherStorage voucherStorage;

    public VoucherManager(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public UUID create(VoucherInfo voucherInfo) {
        Voucher voucher = voucherInfo
                .getType()
                .construct(voucherInfo);
        return voucherStorage.save(voucher);
    }

    /**
     * @throws HaveNoVoucherException 저장한 Voucher가 하나도 없는 상황에서 Voucher를 조회하였을 때 발생하는 예외입니다.
     */
    public List<VoucherResponse> list() {
        List<Voucher> vouchers = voucherStorage.findAll();
        if (vouchers.isEmpty()) {
            throw new HaveNoVoucherException();
        }
        return vouchers.stream()
                .map(voucher -> new VoucherResponse(voucher.getVoucherType(), voucher.getDiscountRate()))
                .collect(Collectors.toList());
    }
}
