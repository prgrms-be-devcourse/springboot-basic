package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.Console;
import org.prgrms.kdt.member.controller.MemberController;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.wallet.controller.WalletController;
import org.prgrms.kdt.wallet.dto.request.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.response.JoinedWalletsResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class ControllerRequestManager {
    private final VoucherController voucherController;
    private final MemberController memberController;
    private final WalletController walletController;
    private final Console console;

    public ControllerRequestManager(VoucherController voucherController, MemberController memberController, WalletController walletController, Console console) {
        this.voucherController = voucherController;
        this.memberController = memberController;
        this.walletController = walletController;
        this.console = console;
    }

    public void createVoucher() throws IOException {
        VoucherType voucherType = VoucherType.getTypeByNum(console.getVoucherTypes());
        double discountAmount = Double.parseDouble(console.getDiscountAmount());
        voucherController.create(new CreateVoucherRequest(voucherType, discountAmount));
    }

    public void findAllVoucher(){
        console.printAllVoucher(voucherController.findAll());
    }

    public void findAllBlackMember(){
        console.printAllMember(memberController.findAllBlackMember());
    }

    public void createMember() throws IOException {
        String memberName = console.getMemberName();
        memberController.createMember(new CreateMemberRequest(memberName, MemberStatus.COMMON));
    }

    public void findAllMember(){
        console.printAllMember(memberController.findAllMember());
    }

    public void assignVoucher() throws IOException {
        UUID memberUuid = console.getMemberId();
        UUID voucherUuid = console.getVoucherId();
        walletController.createWallet(new CreateWalletRequest(memberUuid, voucherUuid));
    }

    public void findVouchersByMember() throws IOException {
        JoinedWalletsResponse walletResponse = walletController.findVouchersByMemberId(console.getMemberId());
        console.printAllWallet(walletResponse);
    }

    public void deleteWalletById() throws IOException {
        walletController.deleteWalletById(console.getWalletId());
    }

    public void findMembersByVoucher() throws IOException {
        JoinedWalletsResponse walletResponse = walletController.findMembersByVoucherId(console.getVoucherId());
        console.printAllWallet(walletResponse);
    }

    public void findAllWallet(){
        console.printAllWallet(walletController.findAllWallet());
    }
}
