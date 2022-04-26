package org.prgrms.kdtspringdemo.domain.voucher.controller;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherDMLMenuType;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class VoucherDMLTypeController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherChooseController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final VoucherUpdateController voucherUpdateController;

    public VoucherDMLTypeController(Output output, Input input, VoucherService voucherService, VoucherUpdateController voucherUpdateController) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
        this.voucherUpdateController = voucherUpdateController;
    }

    // Voucher 에서 mysql 에서 DML 선택
    public void chooseVoucherManagement() {
        System.out.println(output.chooseVoucherManagementMessage());
        VoucherDMLMenuType voucherDMLMenuType = input.inputVoucherManagementDMLType();

        switch (voucherDMLMenuType) {
            case UPDATE -> {
                // 기존의 voucher 보여주고 update 할거 선택하게 하기
                System.out.println("this is list of voucher");
                List<Voucher> all = voucherService.findAll();
                AtomicInteger atomicInteger = new AtomicInteger(1);
                all.stream()
                        .forEach((voucher) -> System.out.println((atomicInteger.getAndIncrement()) + " : " + voucher));


                System.out.println("choose index");
                int index = input.inputNumber();
                Voucher willUpdateVoucher = null;
                try {
                    willUpdateVoucher = voucherService.findByOrder(index - 1);
                } catch (IndexOutOfBoundsException e) {
                    logger.error("정수가 아닌 값 입력으로 인한 IndexOutOfBoundsException 예외발생");
                }

                System.out.println(output.chooseVoucherTypeMessage());
                VoucherType voucherType = input.inputVoucherType();
                voucherUpdateController.updateVoucherWithType(willUpdateVoucher, voucherType);
            }
            case COUNT -> System.out.println("All customers number : " + voucherService.count());
            case FINDALL -> {
                List<Voucher> all = voucherService.findAll();
                AtomicInteger atomicInteger = new AtomicInteger(1);
                all.stream()
                        .forEach((voucher) -> System.out.println((atomicInteger.getAndIncrement()) + " : " + voucher));

            }
            case FINDBYID -> System.out.println("sorry we do not support this service");
            case FINDBYTYPE -> System.out.println("sorry we do not support this service");
            case DELETEALL -> {
                voucherService.deleteAll();
                System.out.println("Delete all voucher data");
            }
        }
    }
}
