package org.prgrms.voucherapp;

import lombok.AllArgsConstructor;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.global.enums.Command;
import org.prgrms.voucherapp.global.enums.ModuleCommand;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Navigator implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(Navigator.class);

    //    TODO : 지금은 모든 exception에 대해서 프로그램 초기부터 시작함. create에서 예외 발생시, create부터 다시 시작할 수 있도록 변경해보자.
    //    TODO : 테스트 코드 작성
    //    TODO : 고객리스트 심화 과제
    //    TODO : properties yaml 외부 설정 주입
    @Override
    public void run() {
        while (true) {
            try{
                output.informModuleMenu();
                ModuleCommand selectedModule = input.moduleInput("Type a module name : ");
                switch(selectedModule){
                    case EXIT -> {
                        output.exitMessage();
                        return;
                    }
                    case VOUCHER -> executeVoucherCommand();
                    case CUSTOMER -> executeCustomerCommand();
                    case WALLET -> executeWalletCommand();
                }
            } catch(Exception e){
                logger.error("Main : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }

    private void executeVoucherCommand(){
        while(true){
            try {
                output.informCommand(ModuleCommand.VOUCHER);
                Command userCommand = input.commandInput("Type a command : ");
                switch (userCommand) {
                    case CANCEL -> {
                        logger.info("User chose cancel command.");
                        output.cancelMessage();
                        return;
                    }
                    case CREATE -> {
                        logger.info("User chose create command.");
                        output.informVoucherTypeFormat();
                        VoucherType voucherType = input.voucherTypeInput("Type a voucher type's number : ");
                        long discountAmount = input.discountAmountInput(voucherType, "Type discount amount(0 < x <= %d) : ".formatted(voucherType.getMaxDiscountAmount()));
                        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
                        output.completeMessage("생성");
                    }
                    case LIST -> {
                        logger.info("User chose list command.");
                        output.infoMessage(voucherService.getVoucherListByStr());
                    }
                    case DELETE -> {
                        logger.info("User chose delete command.");
                        UUID voucherId = input.voucherIdInput("Type a voucher ID : ");
                        voucherService.removeVoucher(voucherId);
                        output.completeMessage("삭제");
                    }
                    case UPDATE -> {
                        logger.info("User chose update command.");
                        UUID voucherId = input.voucherIdInput("Type a voucher ID : ");
                        output.informVoucherTypeFormat();
                        VoucherType newVoucherType = input.voucherTypeInput("Type a new voucher type's number : ");
                        long newDiscountAmount = input.discountAmountInput(newVoucherType, "Type discount amount of a new voucher(0 < x <= %d) : ".formatted(newVoucherType.getMaxDiscountAmount()));
                        voucherService.updateVoucher(voucherId, newVoucherType, newDiscountAmount);
                        output.completeMessage("수정");
                    }
                }
            } catch (Exception e) {
                logger.error("Voucher : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }

    private void executeCustomerCommand(){

    }

    private void executeWalletCommand(){

    }
}
