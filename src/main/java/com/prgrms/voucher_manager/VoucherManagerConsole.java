package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.customer.CustomerService;
import com.prgrms.voucher_manager.exception.EmptyVoucherException;
import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.io.Input;
import com.prgrms.voucher_manager.io.Output;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class VoucherManagerConsole {
    private static Input input;
    private static Output output;
    private static VoucherService voucherService;
    private static CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagerConsole.class);

    public VoucherManagerConsole(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() throws IOException {
        while(true){
            output.consoleMenu();
            String command = input.selectOption();
            switch (command){
                case "create":
                    output.selectVoucher();
                    String voucherType = input.selectOption();
                    try{
                        checkType(voucherType);
                        logger.info("create voucher");
                    }catch (IllegalArgumentException e){
                        logger.error("잘못된 입력 값");
                        output.wrongInput();
                    }catch (WrongVoucherValueException e){
                        logger.error("해당 voucher에 알맞은 value가 아닙니다.");
                        output.wrongInput();
                    }
                    break;
                case "list":
                    try{
                        voucherService.getFindAllVoucher();
                    }catch(EmptyVoucherException e){
                        logger.info("voucher repository에 값이 없습니다.");
                        output.emptyVoucherRepository();
                    }
                    break;
                case "exit":
                    logger.info("프로그램 종료");
                    output.exitProgram();
                    System.exit(0);
                case "black-list":
                    logger.info("블랙리스트 출력");
                    try{
                        customerService.findAll();
                    }catch(IllegalArgumentException e){
                        logger.info("블랙리스트가 비어있습니다.");
                        output.emptyBlackList();
                    }
                    break;
                default:
                    logger.error("wrong input {}",command);
                    output.wrongInput();
                    break;
            }
        }
    }

    public static Voucher checkType(String voucherType) throws IOException {
        if(!voucherType.equals("1") && !voucherType.equals("2")) {
            logger.error("VoucherType check fail! - wrong input {}",voucherType);
            throw new IllegalArgumentException();
        }
        long value;
        if(voucherType.equals("1")){
            value = Long.parseLong(input.input("Please enter a amount : "));
            return voucherService.createFixedAmountVoucher(value)
                    .orElseThrow(()-> new WrongVoucherValueException("amount 값은 0 보다 커야 합니다."));
        }else{
            value = Long.parseLong(input.input("Please enter a percentage : "));
            return voucherService.createPercentDiscountVoucher(value)
                    .orElseThrow(()-> new WrongVoucherValueException("percentage 는 100% 보다 클 수 없습니다."));
        }
    }
}
