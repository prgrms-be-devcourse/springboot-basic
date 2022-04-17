package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.utils.DiscountPolicyUtil;
import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherEntity;
import com.mountain.voucherApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.constants.Message.*;

@Component
public class Strategy {

    private static final Logger log = LoggerFactory.getLogger(Strategy.class);
    private final Console console;
    private final VoucherService voucherService;

    public Strategy(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void create() {
        console.choiceDiscountPolicy();
        try {
            // 1. 할인정책 선택
            int policyId = Integer.valueOf(console.input());
            if (policyId > DiscountPolicy.values().length) {
                console.printWrongInput();
                return ;
            }
            // 2. 할인 양(금액 또는 비율) 입력받기.
            console.printAmount();
            long discountAmount = Long.valueOf(console.input());
            // 3. 할인 정책에 해당되는 Voucher 인스턴스 가져오기
            Voucher voucher = DiscountPolicyUtil.getVoucher(policyId);
            // 4. 해당 정책에 입력 가능한 DiscountAmount 에 대해 유효성을 검사한다.
            //       불가능할 경우 Exception 이 발생한다.
            if (voucher.validate(discountAmount)) {
                // 5. [정책, 비율]에 해당하는 VoucherEntity 가 없다면 생성한다. 있다면 기존것을 조회한다.
                voucherService.createVoucher(policyId, discountAmount);
            }
        } catch (Exception e) {
            console.printException(e);
        }
    }

    public void showVoucherList() {
        log.info(SHOW_VOUCHER_LIST);
        console.printAllList(voucherService.findAll());
    }

    public void exit() {
        log.info(PROGRAM_EXIT);
        console.close();
    }
}
