package org.prgrms.kdt.controller;

import org.prgrms.kdt.dto.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputController {

    public static Scanner sc = new Scanner(System.in);

    public CommandType getCommandType(){
        return CommandType.getCommandType(sc.next().toUpperCase());
    }

    public VoucherType getVoucherType() {
        return VoucherType.getVoucherType(sc.next().toUpperCase());
    }

    public int getDiscount() {
        return Integer.parseInt(sc.next());
    }

}