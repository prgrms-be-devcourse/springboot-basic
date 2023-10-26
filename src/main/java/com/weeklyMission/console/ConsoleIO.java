package com.weeklyMission.console;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.voucher.dto.VoucherResponse;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ConsoleIO {

    private final Scanner sc;

    public ConsoleIO(){
        this.sc = new Scanner(System.in);
    }

    private String getCommand(){
        System.out.print(">");
        return sc.next().toLowerCase();
    }

    private Integer getAmount(){
        System.out.print(">");
        return sc.nextInt();
    }

    private UUID getId(){
        System.out.print(">");
        return UUID.fromString(sc.next());
    }

    public String printSelectMode(){
        System.out.println("=== Select Program ===" + System.lineSeparator()
            + "Type voucher to Voucher Mode." + System.lineSeparator()
            + "Type member to Member Mode" + System.lineSeparator()
            + "Type exit to exit the program." + System.lineSeparator());

        return getCommand();
    }

    public String printSelectVoucherFunction(){
        System.out.println("=== Voucher Mode ===" + System.lineSeparator()
            + "Type create to create a new voucher." + System.lineSeparator()
            + "Type list to list all vouchers."  + System.lineSeparator()
            + "Type find to list find voucher by id."  + System.lineSeparator()
            + "Type delete to list delete voucher by id."  + System.lineSeparator());

        return getCommand();
    }

    public String printSelectVoucherType(){
        System.out.println("=== Voucher Type ===" + System.lineSeparator()
            + "Type fixed to create a FixedAmountvoucher." + System.lineSeparator()
            + "Type percent to create PercentDiscountvoucher."  + System.lineSeparator());

        return getCommand();
    }

    public Integer printAmountCommand(){
        System.out.println("=== Voucher Amount ===" + System.lineSeparator());

        return getAmount();
    }

    public void printSuccessCreate(VoucherResponse voucherResponse){
        System.out.println(voucherResponse.toString() + "create Success");
    }

    public void printSuccessGetAllList(List<VoucherResponse> voucherList){
        System.out.println("=== Voucher List ===");
        voucherList.forEach(v-> System.out.println(v.toString() + System.lineSeparator()));
    }

    public UUID printCommandId(){
        System.out.println("=== Command Id ===" + System.lineSeparator());

        return getId();
    }

    public void printSuccessGet(VoucherResponse voucherResponse){
        System.out.println("=== Voucher ===");
        System.out.println(voucherResponse.toString());
    }

    public void printSuccessDelete(){
        System.out.println("=== Complete DeleteVoucher ===");
    }

    public String printSelectMemberFunction(){
        System.out.println("=== Member Mode ===" + System.lineSeparator()
            + "Type list to list all blackList."  + System.lineSeparator());

        return getCommand();
    }

    public void printSuccessGetBlackList(List<Member> blackList) {
        blackList.forEach(member -> System.out.println(member.toString() + System.lineSeparator()));
    }

    public void printExitMessage(){
        System.out.println("프로그램을 종료합니다.");
    }
}
