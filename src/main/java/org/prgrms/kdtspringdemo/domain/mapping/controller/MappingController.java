package org.prgrms.kdtspringdemo.domain.mapping.controller;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.customer.CustomerController;
import org.prgrms.kdtspringdemo.domain.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.mapping.type.MappingType;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final MappingDetailController mappingDetailController;

    public MappingController(Output output, Input input, VoucherService voucherService, CustomerService customerService, MappingDetailController mappingDetailController) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.mappingDetailController = mappingDetailController;
    }

    // Customer 에게 Voucher 을 할당하고 관리하게 한다.
    public void mappingCustomerToVoucher() {
        System.out.println(output.initMappingMessage());
        MappingType mappingType = input.inputMappingType();
        switch (mappingType) {
            case ASSIGN -> {
                // customer 1명을 고른다
                Customer customer = mappingDetailController.chooseOneCustomer();
                // 할당할 voucher 1개를 고른다.
                Voucher assignedVoucher = mappingDetailController.chooseOneVoucher();
                // voucher 의 customer id 를 업데이트 한다.
                voucherService.updateCustomerId(assignedVoucher, customer.getCustomerId());

                System.out.println("Update voucher's customer id");
            }
            case LISTASCUSTOMER -> {
                MappingType.LISTASVOUCHER.writeStateInfo();
                // customer 1명을 고른다
                Customer customer = mappingDetailController.chooseOneCustomer();
                // 모든 voucher 들 중 같은 customer id 를 가진 voucher 들을 나열한다.
                mappingDetailController.listAllVoucherHavingSameCustomerId(customer);
            }
            case DELETEVOUCHER -> {
                MappingType.DELETEVOUCHER.writeStateInfo();
                // customer 1명을 고른다
                Customer customer = mappingDetailController.chooseOneCustomer();
                // 해당 고객이 가진 voucher 를 모두 보여준다.
                Optional<Voucher> vouchers = mappingDetailController.showAllVouchers(customer);
                // 1개의 voucher 를 고른다
                if(vouchers.isEmpty()){
                    return;
                }

                System.out.println("choose index");
                int intIndex = input.inputNumber();
                List<Voucher> voucherList = vouchers.stream().toList();
                Voucher voucher = voucherList.get(intIndex-1);
                // 바우처의 customerId 를 UUID.nameUUIDFromBytes("null".getBytes()) 로 바꾼다.
                voucherService.updateCustomerId(voucher, UUID.nameUUIDFromBytes("null".getBytes()));
            }
            case LISTASVOUCHER -> {
                MappingType.LISTASCUSTOMER.writeStateInfo();
                // 모든 voucher를 보여주고 1개의 voucher 를 고른다.
                Voucher assignedVoucher = mappingDetailController.chooseOneVoucher();
                UUID customerId = assignedVoucher.getCustomerId();
                // 해당 customerId 를 가진 Customer 조회
                Customer customer = mappingDetailController.findCustomer(customerId);

                System.out.println(customer);
            }
        }
    }
}
