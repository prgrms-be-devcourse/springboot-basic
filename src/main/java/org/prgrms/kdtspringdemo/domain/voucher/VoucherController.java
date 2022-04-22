package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherDMLType;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;

    public VoucherController(Output output, Input input, VoucherService voucherService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
    }


    // Voucher 타입 선택(Fixed, Percent)
    public void chooseVoucher() {
        System.out.println(output.chooseVoucherTypeMessage());
        VoucherType voucherType = input.inputVoucherType();

        switch (voucherType) {
            case FIXED -> {
                VoucherType.FIXED.writeStateInfo();
                System.out.println(output.FixedDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);

                // mysql db 에 저장하기
                voucherService.saveVoucherInDB(amount, VoucherType.FIXED);
            }
            case PERCENT -> {
                VoucherType.PERCENT.writeStateInfo();
                System.out.println(output.PercentDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);

                // mysql db 에 저장하기
                voucherService.saveVoucherInDB(amount, VoucherType.PERCENT);
            }
            case None -> VoucherType.None.writeStateInfo();
        }
    }

    // Voucher 에서 mysql 에서 DML 선택
    public void chooseVoucherManagement() {
        System.out.println(output.chooseVoucherManagementMessage());
        VoucherDMLType voucherDMLType = input.inputVoucherManagementDMLType();

        switch (voucherDMLType) {
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
                updateVoucherWithType(willUpdateVoucher, voucherType);
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

    private void updateVoucherWithType(Voucher willUpdateVoucher, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> {
                VoucherType.FIXED.writeStateInfo();
                System.out.println(output.FixedDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                // mysql db 에 update
                voucherService.updateAmount(willUpdateVoucher, voucherType, amount);
            }
            case PERCENT -> {
                VoucherType.PERCENT.writeStateInfo();
                System.out.println(output.PercentDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                // mysql db 에 update
                voucherService.updateAmount(willUpdateVoucher, voucherType, amount);
            }
            case None -> VoucherType.None.writeStateInfo();
        }
    }


    public void showVoucherList() {
        List<Voucher> vouchers = voucherService.showVoucherList();
        vouchers.stream().forEach(System.out::println);
    }
}
