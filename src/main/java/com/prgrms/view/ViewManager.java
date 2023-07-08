package com.prgrms.view;

import com.prgrms.view.message.GuideMessage;
import com.prgrms.view.view.Input;
import com.prgrms.view.view.Output;
import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.discount.DiscountCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewManager {

    private final Output output;
    private final Input input;
    private final DiscountCreator discountCreator = new DiscountCreator();

    public Menu guideStartVoucher() {
        output.write(GuideMessage.START.toString());
        String option = input.enterOption();
        return Menu.findByMenu(option);
    }

    public VoucherRequest guideCreateVoucher() {
        Arrays.stream(VoucherType.values())
                .forEach(voucherPolicy -> output.write(voucherPolicy.voucherPolicyOptionGuide()));

        String option = input.enterOption();
        VoucherType voucherType = VoucherType.findByPolicy(option);

        return guideVoucherPolicy(voucherType);
    }

    public VoucherRequest guideVoucherPolicy(VoucherType voucherType) {
        output.write(voucherType.discountGuide());
        double discountAmount = input.enterDiscount();

        Discount discount = discountCreator.createDiscount(discountAmount,voucherType);

        output.write(GuideMessage.COMPLETE_CREATE.toString());

        return new VoucherRequest(voucherType, discount);
    }

    public void guideClose() {
        output.write(GuideMessage.CLOSE.toString());
    }

    public void viewVoucherList(List<VoucherResponse> vouchers) {
        vouchers.forEach(v -> output.write(v.toString()));
    }

    public void viewError(String exceptionMessage) {
        output.write(exceptionMessage);
    }
}
