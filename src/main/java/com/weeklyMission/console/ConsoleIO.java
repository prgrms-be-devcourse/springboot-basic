package com.weeklyMission.console;

import com.weeklyMission.common.ResponseInfo;
import com.weeklyMission.member.dto.MemberResponse;
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

    private Integer getNumber(){
        System.out.print(">");
        return sc.nextInt();
    }

    private String getId(){
        System.out.print(">");
        return sc.next();
    }

    public String printSelectMode(){
        System.out.println("=== Select Program ===" + System.lineSeparator()
            + "Type voucher to Voucher Mode." + System.lineSeparator()
            + "Type member to Member Mode" + System.lineSeparator()
            + "Type wallet to Wallet Mode" + System.lineSeparator()
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

        return getNumber();
    }

    public void printSuccessCreate(){
        System.out.println("=== Create Success ===");
    }

    public void printSuccessGetVoucherList(List<VoucherResponse> voucherList){
        System.out.println("=== Voucher List ===");
        voucherList.forEach(v-> System.out.println(v.printInfo() + System.lineSeparator()));
    }

    public String commandVoucherId(){
        System.out.println("=== Voucher Id ===" + System.lineSeparator());

        return getId();
    }

    public String commandMemberId(){
        System.out.println("=== Member Id ===" + System.lineSeparator());

        return getId();
    }

    public void printSuccessGet(ResponseInfo response){
        System.out.println("=== Voucher ===");
        System.out.println(response.printInfo());
    }

    public void printSuccessDelete(){
        System.out.println("=== Complete DeleteVoucher ===");
    }

    public String printSelectMemberFunction(){
        System.out.println("=== Member Mode ===" + System.lineSeparator()
            + "Type create to create a new member." + System.lineSeparator()
            + "Type list to list all members."  + System.lineSeparator()
            + "Type find to list find member by id."  + System.lineSeparator()
            + "Type delete to list delete member by id."  + System.lineSeparator());

        return getCommand();
    }

    public String nameCommand() {
        System.out.println("=== Name ===" + System.lineSeparator());

        return getCommand();
    }

    public String emailCommand() {
        System.out.println("=== Email ===" + System.lineSeparator());

        return getCommand();
    }

    public Integer ageCommand() {
        System.out.println("=== Age ===" + System.lineSeparator());

        return getNumber();
    }

    public void printSuccessGetMemberList(List<MemberResponse> memberList) {
        System.out.println("=== Member List ===");
        memberList.forEach(member -> System.out.println(member.printInfo() + System.lineSeparator()));
    }

    public void printExitMessage(){
        System.out.println("프로그램을 종료합니다.");
    }

    public String printSelectWalletFunction() {
        System.out.println("=== Wallet Mode ===" + System.lineSeparator()
            + "Type create to create a new member." + System.lineSeparator()
            + "Type findmember to list find member By voucher." + System.lineSeparator()
            + "Type findvoucher to list find voucher by member." + System.lineSeparator()
            + "Type delete to list delete member by id."  + System.lineSeparator());

        return getCommand();
    }
}
