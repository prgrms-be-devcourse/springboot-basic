package org.prgrms.kdt.controller;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.enums.CommandType;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class InputController {

    public static Scanner sc = new Scanner(System.in);

    public CommandType getCommandType(){
        return CommandType.getCommandType(sc.next().toUpperCase());
    }

    public UUID getCustomerId() {
        return UUID.fromString(sc.next());
    }

    public VoucherType getVoucherType() {
        return VoucherType.getVoucherType(sc.next().toUpperCase());
    }

    public int getDiscount() {
        return Integer.parseInt(sc.next());
    }

}