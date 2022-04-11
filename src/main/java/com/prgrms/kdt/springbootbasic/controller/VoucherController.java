package com.prgrms.kdt.springbootbasic.controller;

import com.prgrms.kdt.springbootbasic.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.inputPackage.CustomInput;
import com.prgrms.kdt.springbootbasic.outputPackage.CustomOutput;
import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;
import com.prgrms.kdt.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class VoucherController {
    private static CustomOutput customOutput;
    private static CustomInput customInput;
    private static VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(CustomOutput customOutput, CustomInput customInput, VoucherService voucherService){
        this.customOutput = customOutput;
        this.customInput = customInput;
        this.voucherService = voucherService;
    }

    public void runVoucherProgramInOrder() throws IOException {
        while(true) {
            logger.info("[VoucherController : runVoucherProgramInOrder] Start New Process");
            informCommand();
            String command = getCommand();
            logger.info(MessageFormat.format("[VoucherController : runVoucherProgramInOrder] Command : {0}", command));
            handleCommand(command);
            logger.info("[VoucherController : runVoucherProgramInOrder] End New Process");
        }

    }

    public void informCommand(){
        customOutput.informCommandWithConsole();
    }

    public String getCommand() throws IOException {
        return customInput.getCommand();
    }

    public void handleCommand(String command) throws IOException {
        CommandEnum.getCommandEnum(command).runCommand();
        customOutput.informProcessEnd();
    }

    private static void showVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        customOutput.printVoucherList(voucherList);
    }

    private static void createVoucher() throws IOException {
        customOutput.informNewVoucherInfo();
        VoucherInfo voucherInfo = customInput.getNewVoucherInfo();
        int voucherType = voucherInfo.getVoucherType();
        long discountAmount= voucherInfo.getDiscountAmount();
        Voucher voucher = null;

        switch (voucherType){
            case 1:
                logger.info("[VoucherController : createVoucher] Create FixedAmountVoucher");
                voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
                break;
            case 2:
                logger.info("[VoucherController : createVoucher] Create PercetnDiscountVoucher");
                voucher = new PercentDiscountVoucher(UUID.randomUUID(),discountAmount);
                break;
        }

        if (saveVoucher(voucher) == false){
            customOutput.printError("정상적으로 저장되지 않았습니다. 다시 시도해주세요.");
        }
    }

    private static boolean saveVoucher(Voucher voucher){
        //voucher을 초기화를 안했다고 해서 null로 초기화를 했는데, 이 때문에 null check를 해주었습니다. null로 초기화하는거 말고 더 좋은 방법이 있을까요?

        if (voucher == null){
            return false;
        }

        try {
            voucherService.saveVoucher(voucher);
        }catch(Exception e){
            logger.error("[VoucherController : createVoucher] 바우처가 정상적으로 저장되지 않음");
            return false;
        }

        return true;
    }


    enum CommandEnum {
        EXIT("exit"){
            @Override
            public void runCommand(){
                System.exit(0);
            }
        },
        CREATE("create"){
            @Override
            public void runCommand() throws IOException {
                createVoucher();
            }
        },
        LIST("list"){
            @Override
            public void runCommand(){
                showVoucherList();
            }
        };

        private final String command;

        CommandEnum(String command) {
            this.command = command;
        }

        public static CommandEnum getCommandEnum(String command){
            return Arrays.stream(CommandEnum.values()).filter(c -> c.command.equals(command)).collect(Collectors.toList()).get(0);
        }

        public abstract void runCommand() throws IOException;
    }
}


