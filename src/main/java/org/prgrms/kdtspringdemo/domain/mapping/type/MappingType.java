package org.prgrms.kdtspringdemo.domain.mapping.type;


import java.util.Arrays;

public enum MappingType {
    ASSIGN("Assign voucher to the customer"),
    LISTASCUSTOMER("Inquire certain customer's voucher list"),
    DELETEVOUCHER("Delete the voucher of customer"),
    LISTASVOUCHER("Inquire certain voucher's customer"),
    NONE("Type letter correctly");

    private final String stateInfo;

    MappingType(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static MappingType of(String inputType) {
        return Arrays.stream(MappingType.values())
                .filter(type -> String.valueOf(type).equalsIgnoreCase(inputType))
                .findFirst()
                .orElse(NONE);
    }

    public void writeStateInfo() {
        System.out.println(stateInfo);
    }
}
