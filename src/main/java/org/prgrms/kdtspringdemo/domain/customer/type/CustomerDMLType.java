package org.prgrms.kdtspringdemo.domain.customer.type;


import java.util.Arrays;

public enum CustomerDMLType {
    INSERT("Insert Customer"),
    UPDATE("Update Customer - not work"),
    COUNT("Count all customers"),
    FINDALL("Show all customers"),
    FINDBYID("Find customer by id"),
    FINDBYNAME("Find customer by name"),
    FINDBYEMAIL("Find customer by email"),
    DELETEALL("delete all customer"),
    NONE("Type letter correctly");;

    private final String stateInfo;

    CustomerDMLType(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static CustomerDMLType of(String inputDML) {
        return Arrays.stream(CustomerDMLType.values())
                .filter(type -> String.valueOf(type).equalsIgnoreCase(inputDML))
                .findFirst()
                .orElse(NONE);
    }

    public void writeStateInfo() {
        System.out.println(stateInfo);
    }

}
