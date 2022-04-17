package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.customer.CustomerService;
import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.utils.DiscountPolicyUtil;
import com.mountain.voucherApp.voucher.Voucher;
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
    private final CustomerService customerService;

    public Strategy(Console console,
                    VoucherService voucherService,
                    CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
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
            if (voucher.validate(discountAmount)) {
                // 5. [정책, 비율]에 해당하는 VoucherEntity 가 없다면 생성한다.
                voucherService.createVoucher(policyId, discountAmount);
            }
        } catch (Exception e) {
            console.printException(e);
        }
    }

    public void showVoucherList() {
        log.info(SHOW_VOUCHER_LIST);
        console.printVoucherList(voucherService.findAll());
    }

    public void exit() {
        log.info(PROGRAM_EXIT);
        console.close();
    }

    public void addVoucher() {
        log.info("addVoucher");
        // 1. 고객 리스트 조회
        showCustomerList();
        // 2. 고객 선택
        // 3. 바우처 리스트 조회
        // 4. 바우처 선택
        // 5. update
    }

    public void showCustomerList() {
        console.printCustomerList(customerService.findAll());
    }

    public void removeVoucher() {
        log.info("removeVoucher");
        // 1. voucherId가 null이 아닌 고객 조회
        // 2. 바우처를 제거할 고객 선택
        // 3. 바우처 제거 (customer.voucherId update null)
    }

    public void showByVoucher() {
        log.info("showByVoucher");
        // 1. 바우처 리스트 조회
        // 2. 바우처 선택
        // 3. 해당 바우처를 보유한 고객 조회 (from customer where voucherId = 선택된 바우처 Id)
    }
}
