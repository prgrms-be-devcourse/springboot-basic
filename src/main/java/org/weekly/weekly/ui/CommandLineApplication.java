package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.exception.InputValidator;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.request.VoucherInfoRequest;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;

@Component
public class CommandLineApplication {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    @Autowired
    public CommandLineApplication(CommandReader commandReader, CommandWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public VoucherMenu readMenu() {
        while(true) {
            try {
                this.commandWriter.printVoucherProgram();
                return VoucherMenu.getMenu(readUserInput());
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public VoucherCreationRequest createVoucherFromInput() {
        while(true) {
            try {
                DiscountType discountType = readDiscountType();
                VoucherInfoRequest voucherInfoRequest = readVoucherInfo(discountType);
                return new VoucherCreationRequest(voucherInfoRequest, discountType);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public void printErrorMsg(String errorMsg) {
        this.commandWriter.printErrorMsg(errorMsg);
    }

    public void printResult(Response response) {
        this.commandWriter.printReuslt(response.getResult());
    }


    private String readUserInput() {
        String userInput = this.commandReader.readLine();
        InputValidator.isEmpty(userInput);
        return userInput;
    }


    private DiscountType readDiscountType() {
        this.commandWriter.printSelectDiscount();
        String no = readUserInput();
        return DiscountType.getDiscountTypeByNumber(no);
    }

    private VoucherInfoRequest readVoucherInfo(DiscountType discountType){
        this.commandWriter.printCreateVoucher(discountType);
        String voucherInfo = readUserInput();
        return VoucherInfoRequest.of(voucherInfo);
    }


}
