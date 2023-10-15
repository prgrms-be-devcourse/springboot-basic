package com.zerozae.voucher.view;

import com.zerozae.voucher.exception.ExceptionHandler;
import com.zerozae.voucher.validator.InputValidator;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;
import static java.lang.System.out;

@Component
public class ConsoleView implements Input, Output{

    private final BufferedReader bufferedReader;
    public ConsoleView() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String inputCommand(){
        return checkInputAndGet();
    }
    @Override
    public Long inputVoucherDiscount(){
        try {
            return InputValidator.validateInputDiscount(bufferedReader.readLine());
        } catch (IOException e) {
            throw ExceptionHandler.err("입력을 읽을 때 오류가 발생했습니다");
        } catch (ExceptionHandler e){
            throw ExceptionHandler.err(e.getMessage());
        }
    }
    @Override
    public String inputVoucherType(){
        return checkInputAndGet();
    }

    @Override
    public String inputCustomerName() {
        return checkInputAndGet();
    }

    @Override
    public String inputCustomerType() {
        return checkInputAndGet();
    }

    @Override
    public void printCommand() {
        String command = """
        
        === Select Program ===
        Type exit to exit the program.
        Type customer to run customer program.
        Type voucher to run voucher program
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
        Type back to main menu.
        Type create to create a new voucher.
        Type list to list all vouchers.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printCustomerCommand() {
        String command = """
        
        === Customer Program ===
        Type back to main menu.
        Type create to create a new customer.
        Type list to show all customer.
        Type blacklist to show all blacklist customer.
        """;
        out.println(command);
        printPrompt();
    }

    public void printPrompt(){
        out.print("> ");
    }
    private String checkInputAndGet() {
        try {
            return InputValidator.validateInputString(bufferedReader.readLine());
        } catch (IOException e) {
            throw ExceptionHandler.err(getMessage("READ_INPUT_ERROR.MSG"));
        } catch (ExceptionHandler e){
            throw ExceptionHandler.err(e.getMessage());
        }
    }
}
