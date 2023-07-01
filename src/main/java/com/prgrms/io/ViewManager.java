package com.prgrms.io;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.VoucherList;
import com.prgrms.model.voucher.VoucherPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ViewManager {

    private final Output output;
    private final Input input;

    public Menu guideStartVoucher() {
        output.outputView(GuideMessage.START.toString());
        return input.enterMenu().orElseThrow(() -> new IllegalArgumentException("올바른 선택지가 아닙니다."));
    }

    public VoucherRequest guideCreateVoucher() {
        Arrays.stream(VoucherPolicy.values())
                .forEach(voucherPolicy -> output.outputView(voucherPolicy.voucherPolicyOptionGuide()));

        return input.enterVoucherPolicy()
                .map(this::guideVoucherPolicy)
                .orElseThrow(() -> new IllegalArgumentException("등록된 바우처 정책이 아닙니다."));
    }

    public VoucherRequest guideVoucherPolicy(VoucherPolicy voucherPolicy) {
        output.outputView(voucherPolicy.discountGuide());
        Discount discount = new Discount(input.enterDiscount());
        output.outputView(GuideMessage.COMPLETE_CREATE.toString());

        return VoucherRequest.of(voucherPolicy, discount);
    }

    public void guideClose() {
        output.outputView(GuideMessage.CLOSE.toString());
    }

    public void viewVoucherList(VoucherList list) {
        output.outputView(list.toString());
    }

    public void viewError(String exceptionMessage) {
        output.outputView(exceptionMessage);
    }
}
