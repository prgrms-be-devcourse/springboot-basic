package com.pgms.part1.view;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConsoleView extends CommonConsoleView{
    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getMenu(){
        String command = textIO.newStringInputReader()
                .read("""
                         
                         === Customer Menu ===
                        Type **create** to create a new customer.
                        Type **list** to list all customers.
                        Type **blacklist** to list all blocked customers.
                        Type **block** to block customer.
                        Type **exit** to exit the menu.
                        """);

        return command;
    }

    public void listBlockedCustomers(List<CustomerResponseDto> customerResponseDtos){
        System.out.println("\n === Customer Black List ===");
        customerResponseDtos.stream().forEach(c ->
                System.out.println("ID: " + c.id() + " | isBlocked: " + c.isBlocked())
        );
    }

    public Long updateCustomerBlocked() {
        return textIO.newLongInputReader()
                .read("""
                         
                         === Block Customer ===
                        Enter Customer's id
                        """);
    }

    public void listCustomers(List<CustomerResponseDto> customerResponseDtos){
        System.out.println("\n === Customer List ===");
        customerResponseDtos.stream().forEach(c ->
                System.out.println("ID: " + c.id() + " | isBlocked: " + c.isBlocked())
        );
    }

    public CustomerCreateRequestDto createCustomer(){
        String name = textIO.newStringInputReader()
                .read("""
                         
                         === Create Customer ===
                        Enter name
                        """);

        String email = textIO.newStringInputReader()
                .read("""
                        
                        Enter email
                        """);

        return new CustomerCreateRequestDto(name, email);
    }
}
