package org.prgrms.kdtspringdemo.domain.mapping;

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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public MappingController(Output output, Input input, VoucherService voucherService, CustomerService customerService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    // Customer 에게 Voucher 을 할당하고 관리하게 한다.
    public void mappingCustomerToVoucher() {
        System.out.println(output.initMappingMessage());
        MappingType mappingType = input.inputMappingType();
        switch (mappingType) {
            case ASSIGN -> {
                // customer 1명을 고른다
                Customer customer = chooseOneCustomer();

                // 할당할 voucher 1개를 고른다.
                Voucher assignedVoucher = chooseOneVoucher();

                // voucher 의 customer id 를 업데이트 한다.
                voucherService.updateCustomerId(assignedVoucher, customer.getCustomerId());
                System.out.println("Update voucher's customer id");
            }
            case LISTASCUSTOMER -> {
                MappingType.LISTASVOUCHER.writeStateInfo();

                // customer 1명을 고른다
                Customer customer = chooseOneCustomer();

                // 모든 voucher 들 중 같은 customer id 를 가진 voucher 들을 나열한다.
                listAllVoucherHavingSameCustomerId(customer);
            }
            case DELETEVOUCHER -> {
                MappingType.DELETEVOUCHER.writeStateInfo();

                // customer 1명을 고른다
                Customer customer = chooseOneCustomer();

                // 해당 고객이 가진 voucher 를 모두 보여준다.
                Optional<Voucher> vouchers = showAllVouchers(customer);

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
                Voucher assignedVoucher = chooseOneVoucher();
                UUID customerId = assignedVoucher.getCustomerId();

                // 해당 customerId 를 가진 Customer 조회
                Customer customer = findCustomer(customerId);
                System.out.println(customer);
            }
        }
    }

    private Customer findCustomer(UUID customerId) {
        Customer customer = null;
        try{
            customer = customerService.findById(customerId).get();
        }catch (UncategorizedSQLException e){
            logger.error("해당하는 id 의 바우처가 없어 UncategorizedSQLException 예외 발생");
            System.out.println("해당하는 id 의 바우처가 없습니다.");
        }

        return customer;
    }

    private Optional<Voucher> showAllVouchers(Customer customer) {
        System.out.println("This is all voucher related to the customer");
        Optional<Voucher> voucherList = Optional.empty();

        try{
            voucherList = voucherService.findVoucherListByCustomerId(customer.getCustomerId());
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("customer 가 지닌 voucher 가 없어 InvalidDataAccessApiUsageException 예외 발생");
            System.out.println("customer 가 지닌 voucher 가 없습니다.");
        }

        AtomicInteger index = new AtomicInteger(1);
        voucherList.stream().forEach((voucher) -> System.out.println((index.getAndIncrement()) + " : " + voucher));

        return voucherList;
    }

    private Optional<Voucher> listAllVoucherHavingSameCustomerId(Customer customer) {
        System.out.println("This is all voucher related to the customer");
        Optional<Voucher> voucherList = Optional.empty();

        try{
            voucherList = voucherService.findVoucherListByCustomerId(customer.getCustomerId());

        }catch (InvalidDataAccessApiUsageException e){
            logger.error("voucher 가 지닌 customer 가 없어 InvalidDataAccessApiUsageException 예외 발생");
            System.out.println("voucher 가 지닌 customer 가 없습니다.");
        }

        AtomicInteger index = new AtomicInteger(1);
        voucherList.stream().forEach((voucher) -> System.out.println((index.getAndIncrement()) + " : " + voucher));

        return voucherList;
    }

    private Voucher chooseOneVoucher() {
        System.out.println("this is list of voucher");
        List<Voucher> all = voucherService.findAll();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        all.stream()
                .forEach((voucher) -> System.out.println((atomicInteger.getAndIncrement()) + " : " + voucher));

        System.out.println("choose index");
        int index = input.inputNumber();

        Voucher assignedVoucher = null;

        try {
            assignedVoucher = voucherService.findByOrder(index - 1);
        } catch (IndexOutOfBoundsException e) {
            logger.error("Voucher 선택에서 IndexOutOfBoundsException 예외 발생");
            System.out.println("잘못된 범위의 숫자를 입력하였습니다.");
        }

        return assignedVoucher;
    }

    private Customer chooseOneCustomer() {
        MappingType.ASSIGN.writeStateInfo();

        System.out.println("this is list of customer");
        customerService.findAll();

        System.out.println("choose index");
        int index = input.inputNumber();

        Customer customer = null;

        try {
            customer = customerService.findByOrder(index - 1);
        } catch (IndexOutOfBoundsException e) {
            logger.error("Customer 선택에서 IndexOutOfBoundsException 예외 발생");
            System.out.println("잘못된 범위의 숫자를 입력하였습니다.");
        }

        return customer;
    }
}
