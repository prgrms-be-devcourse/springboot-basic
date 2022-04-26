package org.prgrms.kdtspringdemo.domain.mapping.controller;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.customer.CustomerController;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.domain.mapping.type.MappingType;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MappingDetailController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final Input input;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public MappingDetailController(Input input, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    protected Customer findCustomer(UUID customerId) {
        Customer customer = null;
        try{
            customer = customerService.findById(customerId).get();
        }catch (UncategorizedSQLException e){
            logger.error("해당하는 id 의 바우처가 없어 UncategorizedSQLException 예외 발생");
            System.out.println("해당하는 id 의 바우처가 없습니다.");
        }

        return customer;
    }

    protected Optional<Voucher> showAllVouchers(Customer customer) {
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

    protected Optional<Voucher> listAllVoucherHavingSameCustomerId(Customer customer) {
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

    protected Voucher chooseOneVoucher() {
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

    protected Customer chooseOneCustomer() {
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
