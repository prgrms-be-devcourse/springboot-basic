package com.blessing333.springbasic.console_app.customer.ui;

import com.blessing333.springbasic.console_app.ui.ApacheCommandLine;
import com.blessing333.springbasic.console_app.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.console_app.ui.CommandOptions;
import com.blessing333.springbasic.customer.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Profile("console")
public class ApacheCliCustomerUserInterface extends ApacheCommandLine implements CustomerManagingUserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    protected CommandOptions initSupportedCommandOption() {
        return CommandOptionConfigurer.configSupportedCommandOptions(CustomerCommandOptionType.getAvailableCommandOptionTypes());
    }

    @Override
    public void printHelp() {
        printDivider();
        printHelpText("고객 관리 명령어");
        printDivider();
    }

    @Override
    public String requestCustomerId() {
        printMessage("조회할 고객의 아이디를 입력해주세요");
        return requestMessage();
    }

    @Override
    public void printCustomerInformation(Customer customer) {
        printDivider();
        printMessage(customer.toString());
        printDivider();
    }

    @Override
    public void printAllCustomerInformation(List<Customer> customerList) {
        if(customerList.isEmpty()){
            printMessage("등록된 고객이 존재하지 않습니다.");
        }
        else{
            customerList.forEach(this::printCustomerInformation);
        }
    }

    @Override
    public void printRegisterComplete(Customer customer) {
        printMessage("고객 생성이 완료되었습니다.");
        printCustomerInformation(customer);
    }

    @Override
    public CustomerCreateForm requestCustomerInformation() {
        printMessage("고객의 이름을 입력하세요.");
        String name = requestMessage();
        printMessage("고객의 이메일 주소를 입력하세요");
        String email = requestMessage();
        return new CustomerCreateForm(name,email);
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String requestMessage() {
        return scanner.nextLine();
    }

    @Override
    public void printGuide() {
        printMessage("=== Customer Managing Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new customer.");
        printMessage("Type inquiry to find customer information by id.");
        printMessage("Type list to list all customers.");
        printMessage("=======================");
        printMessage("명령을 입력해주세요");
    }

}
