package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.customer.Customer;
import com.mountain.voucherApp.customer.CustomerService;
import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.utils.DiscountPolicyUtil;
import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherEntity;
import com.mountain.voucherApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

import static com.mountain.voucherApp.constants.Message.*;

@Component
public class MenuStrategy {

    private static final Logger log = LoggerFactory.getLogger(MenuStrategy.class);
    private final Console console;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public MenuStrategy(Console console,
                        VoucherService voucherService,
                        CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void create() throws RuntimeException, NumberFormatException {
        console.choiceDiscountPolicy();
        // 1. 할인정책 선택
        int policyId = Integer.valueOf(console.input());
        if (choiceNumberValidate(policyId, DiscountPolicy.values().length)) {
            // 2. 할인 양(금액 또는 비율) 입력받기.
            console.printMessage(PLEASE_AMOUNT);
            long discountAmount = Long.valueOf(console.input());
            // 3. 할인 정책에 해당되는 Voucher 인스턴스 가져오기
            Voucher voucher = DiscountPolicyUtil.getVoucher(policyId);
            // 4. 해당 정책에 입력 가능한 DiscountAmount에 대해 유효성을 검사한다.
            if (voucher.validate(discountAmount)) {
                // 5. [정책, 비율]에 해당하는 VoucherEntity 가 없다면 생성한다.
                voucherService.createVoucher(policyId, discountAmount);
            }
        }
    }

    public void showVoucherList() {
        console.printVoucherList(voucherService.findAll());
    }

    public void showCustomerVoucherInfo() {
        console.printCustomerVoucherInfo(customerService.findAll());
    }

    public void exit() {
        log.info(PROGRAM_EXIT);
        console.close();
    }

    public void addVoucher() {
        // 1. 고객 선택
        List<Customer> customerList = customerService.findAll();
        int customerIdx = choiceCustomer(customerList);
        if (choiceNumberValidate(customerIdx, customerList.size() - 1)) {
            Customer selectedCustomer = customerList.get(customerIdx);
            // 2. 바우처 선택
            List<VoucherEntity> voucherEntityList = voucherService.findAll();
            int voucherIdx = choiceVoucher(voucherEntityList);
            if (choiceNumberValidate(voucherIdx, voucherEntityList.size() - 1)) {
                VoucherEntity selectedVoucher = voucherEntityList.get(voucherIdx);
                // 3. update
                log.debug("before : {} , after : {}", selectedCustomer.getVoucherId(), selectedVoucher.getVoucherId());
                selectedCustomer.setVoucherId(selectedVoucher.getVoucherId());
                customerService.update(selectedCustomer);
            }
        }
    }

    public void removeVoucher() {
        // 1. voucherId가 null이 아닌 고객 조회
        List<Customer> customers = customerService.findByVoucherIdNotNull();
        // 2. 바우처를 제거할 고객 선택
        int idx = choiceCustomer(customers);
        if (choiceNumberValidate(idx, customers.size() - 1)) {
            Customer customer = customers.get(idx);
            // 3. 바우처 제거
            customer.setVoucherId(null);
            customerService.update(customer);
        }
    }

    public void showByVoucher() {
        // 1. 바우처 선택
        List<VoucherEntity> voucherEntityList = voucherService.findAll();
        int idx = choiceVoucher(voucherEntityList);
        if (choiceNumberValidate(idx, voucherEntityList.size() - 1)) {
            // 2. 해당 바우처를 보유한 고객 조회
            VoucherEntity voucherEntity = voucherEntityList.get(idx);
            List<Customer> customers = customerService.findByVoucherId(voucherEntity.getVoucherId());
            console.printMessage(MessageFormat.format(CUSTOMER_BY_VOUCHER_FORMAT, voucherEntity.getVoucherId()));
            console.printCustomerList(customers);
        }
    }


    private int choiceVoucher(List<VoucherEntity> voucherEntityList) {
        console.printMessage(PLEASE_INPUT_NUMBER);
        console.printVoucherList(voucherEntityList);
        return Integer.valueOf(console.input());
    }

    private boolean choiceNumberValidate(int choiceNumber, int maxSize) {
        if (choiceNumber > maxSize) {
            console.printMessage(WRONG_INPUT);
            return false;
        }
        return true;
    }

    private int choiceCustomer(List<Customer> customerList) {
        console.printMessage(PLEASE_INPUT_NUMBER);
        console.printCustomerList(customerList);
        return Integer.valueOf(console.input());
    }
}
