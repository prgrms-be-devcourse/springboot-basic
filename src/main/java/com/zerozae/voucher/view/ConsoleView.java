package com.zerozae.voucher.view;

import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.validator.InputValidator;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import static java.lang.System.out;

@Component
public class ConsoleView implements Input, Output{

    private static final String INPUT_READ_EXCEPTION_MESSAGE = "입력을 읽을 때 오류가 발생했습니다.";
    private final BufferedReader bufferedReader;

    public ConsoleView() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Long inputNumber(){
        try {
            return InputValidator.validateInputNumber(bufferedReader.readLine());
        } catch (IOException e) {
            throw ErrorMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        } catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    @Override
    public String inputUuid() {
        try {
            return bufferedReader.readLine();
        }catch (IOException e){
            throw ErrorMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        }catch (IllegalArgumentException e){
            throw ErrorMessage.error("유효하지 않은 UUID 형식입니다.");
        }
    }

    @Override
    public String inputCommand(){
        return checkInputStringAndGet();
    }

    @Override
    public String inputVoucherType(){
        return checkInputStringAndGet();
    }

    @Override
    public String inputCustomerName() {
        return checkInputStringAndGet();
    }

    @Override
    public String inputCustomerType() {
        return checkInputStringAndGet();
    }

    @Override
    public void printCommand() {
        String command = """
        
        === Select Program ===
        Type exit to exit the program.
        Type customer to run customer program.
        Type voucher to run voucher program.
        Type Wallet to Run Wallet Program.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printInfo(String voucherInfo) {
        out.print(voucherInfo);
    }

    @Override
    public void printSystemMessage(String message) {
        String systemMessage = MessageFormat.format("\n[System] {0}\n", message);
        out.println(systemMessage);
    }

    @Override
    public void printErrorMessage(String message) {
        String systemMessage = MessageFormat.format("\n[System] {0}", message);
        out.println(systemMessage);
    }

    @Override
    public void printVoucherCommand() {
        String command = """
        
        === Voucher Program ===
        Type back to Main Menu.
        Type create to Create a New Voucher.
        Type list to List All Vouchers.
        Type search to Search Voucher By Id From Voucher List.
        Type update to Update Voucher Information.
        Type delete to Remove Voucher By Id From Voucher List.
        Type delete_all to Remove All Vouchers From Voucher List.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printCustomerCommand() {
        String command = """
        
        === Customer Program ===
        Type back to Main Menu.
        Type create to Create a New Customer.
        Type list to Show All Customer.
        Type blacklist to Show All Blacklist Customer.
        Type search to Search Customer By Id From Customer List.
        Type update to Update Customer Information.
        Type delete to Delete Customer By Id From Customer List.
        Type delete_all to Remove All Customers From Customer List.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printWalletCommand(){
        String command = """
                
        === Wallet Program ===
        Type back to Main Menu.
        Type register to Register Voucher to Customer.
        Type voucher_list to List of Vouchers Owned By Customer.
        Type Owner to Show VoucherOwner.
        Type remove to Remove Voucher Owned by Customer.
        Type delete_all to Delete All Wallets.
        """;
        out.println(command);
        printPrompt();
    }

    public void printPrompt(){
        out.print("> ");
    }

    private String checkInputStringAndGet() {
        try {
            return InputValidator.validateInputString(bufferedReader.readLine());
        } catch (IOException e) {
            throw ErrorMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        } catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }
}
