package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.ui.exception.InputValidator;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.SystemWriter;
import org.weekly.weekly.util.CustomerMenu;
import org.weekly.weekly.util.ManageMenu;
import org.weekly.weekly.util.Menu;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.request.VoucherInfoRequest;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;

import java.util.List;
import java.util.function.Supplier;

@Component
public class CommandLineApplication {
    private final CommandReader commandReader;
    private final SystemWriter commandWriter;

    @Autowired
    public CommandLineApplication(CommandReader commandReader, SystemWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public ManageMenu readManageMenu() {
        return readMenu(()-> {
            commandWriter.printMangeProgram();
            return ManageMenu.getMenu(readMenuInput());
        });
    }

    public VoucherMenu readVoucherMenu() {
        return readMenu(()-> {
            commandWriter.printVoucherProgram();
            return VoucherMenu.getMenu(readMenuInput());
        });
    }

    public CustomerMenu readCustomerMenu() {
        return readMenu(()-> {
            commandWriter.printCustomerProgram();
            return CustomerMenu.getMenu(readMenuInput());
        });
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

    public CustomerCreationRequest createCustomerFromInput() {
        while(true) {
            try {
                String email = processEmail();
                String name = processName();
                return CustomerCreationRequest.of(email, name);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public CustomerUpdateRequest customerDetailFromInput() {
        while(true) {
            try {
                String email = processEmail();
                return CustomerUpdateRequest.of(email);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public CustomerUpdateRequest customerUpdateRequest(){
        while(true) {
            try {
                String email = processEmail();
                String newEmail = processNewEmail();
                return CustomerUpdateRequest.of(email, newEmail);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public void printErrorMsg(String errorMsg) {
        commandWriter.printErrorMessage(errorMsg);
    }

    public void printResult(Response response) {
        commandWriter.printReuslt(response.getResult());
    }

    public void printDeleteMessage() {
        commandWriter.printDeleteMessage();
    }


    private String readMenuInput() {
        String userInput = commandReader.readLine();
        InputValidator.isEmpty(userInput);
        return userInput.toUpperCase();
    }

    private String readUserInput() {
        String userInput = commandReader.readLine();
        InputValidator.isEmpty(userInput);
        return userInput;
    }

    private <T extends Menu> T readMenu(Supplier<T> menuSupplier) {
        while(true) {
            try {
                return menuSupplier.get();
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    private DiscountType readDiscountType() {
        commandWriter.printSelectDiscount();
        String no = readUserInput();
        return DiscountType.getDiscountTypeByNumber(no);
    }

    private VoucherInfoRequest readVoucherInfo(DiscountType discountType){
        commandWriter.printCreateVoucher(discountType);
        String voucherInfo = readUserInput();
        return VoucherInfoRequest.of(voucherInfo);
    }

    private String processEmail() {
        commandWriter.printEmailInputMessage();
        return readUserInput();
    }

    private String processNewEmail() {
        commandWriter.printNewEmailInputMessage();
        return readUserInput();
    }

    private String processName() {
        commandWriter.printNameInputMessage();
        return readUserInput();
    }
}
