package com.programmers.kdtspringorder.command;

import com.programmers.kdtspringorder.customer.model.Customer;
import com.programmers.kdtspringorder.customer.service.CustomerService;
import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.service.VoucherService;
import com.programmers.kdtspringorder.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public record CustomerListCommandAction(Input input,
                                        Output output,
                                        VoucherService voucherService,
                                        CustomerService customerService) implements CommandAction {


    @Override
    public void act() {
        List<Customer> customerList = customerService.findAll();
        showCustomers(customerList);

        UUID customerId = getCustomerIdToLookUp(customerList);
        List<Voucher> customerVouchers = voucherService.findByCustomerId(customerId);

        showVouchers(customerVouchers);
        allocateOrDeallocateProcessFromCustomer(customerId, customerVouchers, customerVouchers.size() > 0);
    }

    private void allocateOrDeallocateProcessFromCustomer(UUID customerId, List<Voucher> customerVouchers, boolean notEmpty) {
        StringBuilder stringBuilder = new StringBuilder();
        if (notEmpty) {
            stringBuilder.append(", 삭제하려면 2번을 ");
        }
        output.printMessage("고객에게 바우처를 할당하려면 1번" + stringBuilder + "입력해주세요.");

        int inputNum = Integer.parseInt(input.inputText());

        switch (inputNum) {
            case 1 -> allocateVoucherProcess(customerId);
            case 2 -> removeVoucherProcess(customerVouchers);
        }
    }

    private UUID getCustomerIdToLookUp(List<Customer> customerList) {
        output.printMessage("바우처를 조회할 고객의 번호를 선택하세요.");
        int index = Integer.parseInt(input.inputText()) - 1;
        return customerList.get(index).getCustomerId();
    }

    private void allocateVoucherProcess(UUID customerId) {
        List<Voucher> allVouchers = voucherService.findAllWithoutCustomerId();
        showVouchers(allVouchers);

        output.printMessage("할당할 바우처의 번호를 선택해주세요");
        int voucherIndex = Integer.parseInt(input.inputText()) - 1;
        UUID voucherId = allVouchers.get(voucherIndex).getVoucherId();
        voucherService.allocateVoucher(voucherId, customerId);
    }

    private void removeVoucherProcess(List<Voucher> customerVouchers) {
        output.printMessage("삭제할 바우처의 번호를 선택해주세요");
        int voucherIndex = Integer.parseInt(input.inputText()) - 1;
        UUID voucherId = customerVouchers.get(voucherIndex).getVoucherId();
        voucherService.deallocateVoucher(voucherId);
    }

    private void showVouchers(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            output.printMessage("해당 고객은 소유한 바우처가 없습니다");
            return;
        }

        for (int i = 0; i < voucherList.size(); i++) {
            System.out.println(MessageFormat.format("{0}. {1}", (i + 1), voucherList.get(i).toString()));
        }
    }

    private void showCustomers(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            output.printMessage("현재 고객이 존재하지 않습니다.");
            return;
        }

        for (int i = 0; i < customerList.size(); i++) {
            System.out.println(MessageFormat.format("{0}. {1}", (i + 1), customerList.get(i).toString()));
        }
    }
}
