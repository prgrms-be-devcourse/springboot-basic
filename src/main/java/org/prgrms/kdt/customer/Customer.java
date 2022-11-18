package org.prgrms.kdt.customer;

import org.prgrms.kdt.exceptions.CustomerException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Customer {

    private static final String NAME_EXCEPTION = "이름은 빈값일 수 없습니다.";

    private final String customerId;
    private String name;
    private final String email;
    private final LocalDateTime createAt;

    public Customer(String customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Customer(String customerId, String name, String email, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }

    private void validateName(String input){
        if(input.isBlank()){
            throw new CustomerException(NAME_EXCEPTION);
        }
    }

    public void changeName(String afterName){
        validateName(afterName);
        this.name = afterName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public LocalDateTime getCreateAt(){
        return createAt;
    }
}
