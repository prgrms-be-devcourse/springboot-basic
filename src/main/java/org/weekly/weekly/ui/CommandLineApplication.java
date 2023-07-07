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
            this.commandWriter.printMangeProgram();
            return ManageMenu.getMenu(readUserInput());
        });
    }

    public VoucherMenu readVoucherMenu() {
        return readMenu(()-> {
            this.commandWriter.printVoucherProgram();
            return VoucherMenu.getMenu(readUserInput());
        });
    }

    public CustomerMenu readCustomerMenu() {
        return readMenu(()-> {
            this.commandWriter.printCustomerProgram();
            return CustomerMenu.getMenu(readUserInput());
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

    public CustomerUpdateRequest deleteCustomerFromInput() {
        while(true) {
            try {
                String email = processEmail();
                return CustomerUpdateRequest.of(email);
            } catch (Exception exception) {
                printErrorMsg(exception.getMessage());
            }
        }
    }

    public void printErrorMsg(String errorMsg) {
        this.commandWriter.printErrorMessage(errorMsg);
    }

    public void printResult(Response response) {
        this.commandWriter.printReuslt(response.getResult());
    }


    private String readUserInput() {
        String userInput = this.commandReader.readLine();
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
        this.commandWriter.printSelectDiscount();
        String no = readUserInput();
        return DiscountType.getDiscountTypeByNumber(no);
    }

    private VoucherInfoRequest readVoucherInfo(DiscountType discountType){
        this.commandWriter.printCreateVoucher(discountType);
        String voucherInfo = readUserInput();
        return VoucherInfoRequest.of(voucherInfo);
    }

    private String processEmail() {
        this.commandWriter.printEmailInputMessage();
        return readUserInput();
    }
    private String processName() {
        this.commandWriter.printNameInputMessage();
        return readUserInput();
    }
}
