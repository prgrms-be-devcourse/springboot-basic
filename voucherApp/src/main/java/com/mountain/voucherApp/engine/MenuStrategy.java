package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.customer.Customer;
import com.mountain.voucherApp.customer.CustomerService;
import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.InputConsole;
import com.mountain.voucherApp.io.OutputConsole;
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
import static com.mountain.voucherApp.constants.Number.NOT_EXIST_DATA_NUMBER;

@Component
public class MenuStrategy {

    private static final Logger log = LoggerFactory.getLogger(MenuStrategy.class);
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public MenuStrategy(InputConsole inputConsole,
                        OutputConsole outputConsole,
                        VoucherService voucherService,
                        CustomerService customerService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void create() throws RuntimeException, NumberFormatException {
        outputConsole.choiceDiscountPolicy();
        // 1. 할인정책 선택
        int policyId = Integer.valueOf(inputConsole.input());
        if (choiceNumberValidate(policyId, DiscountPolicy.values().length)) {
            // 2. 할인 양(금액 또는 비율) 입력받기.
            outputConsole.printMessage(PLEASE_AMOUNT);
            long discountAmount = Long.valueOf(inputConsole.input());
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
        outputConsole.printVoucherList(voucherService.findAll());
    }

    public void showCustomerVoucherInfo() {
        outputConsole.printCustomerVoucherInfo(customerService.findAll());
    }

    public void exit() {
        log.info(PROGRAM_EXIT);
        outputConsole.close();
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
            outputConsole.printMessage(MessageFormat.format(CUSTOMER_BY_VOUCHER_FORMAT, voucherEntity.getVoucherId()));
            if (validateList(customers))
                outputConsole.printCustomerList(customers);
        }
    }

    private boolean choiceNumberValidate(int choiceNumber, int maxSize) {
        if (choiceNumber == NOT_EXIST_DATA_NUMBER)
            return false;
        if (choiceNumber > maxSize) {
            outputConsole.printMessage(WRONG_INPUT);
            return false;
        }
        return true;
    }

    private boolean validateList(List dataList) {
        if (dataList == null || dataList.size() == 0) {
            outputConsole.printMessage(EMPTY_DATA);
            return false;
        }
        return true;
    }

    private int choiceVoucher(List<VoucherEntity> voucherEntityList) {
        if (validateList(voucherEntityList)) {
            outputConsole.printVoucherList(voucherEntityList);
            return Integer.valueOf(inputConsole.input());
        }
        return NOT_EXIST_DATA_NUMBER;
    }

    private int choiceCustomer(List<Customer> customerList) {
        if (validateList(customerList)) {
            outputConsole.printCustomerList(customerList);
            return Integer.valueOf(inputConsole.input());
        }
        return NOT_EXIST_DATA_NUMBER;
    }
}
