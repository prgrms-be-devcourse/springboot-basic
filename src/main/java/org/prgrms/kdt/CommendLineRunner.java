package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.Console;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.controller.MemberController;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.util.Menu;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.wallet.controller.WalletController;
import org.prgrms.kdt.wallet.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.WalletListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@Component
public class CommendLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);
    private final VoucherController voucherController;
    private final MemberController memberController;
    private final WalletController walletController;
    private final Console console;

    public CommendLineRunner(VoucherController voucherController, MemberController memberController, WalletController walletController, Console console) {
        this.voucherController = voucherController;
        this.memberController = memberController;
        this.walletController = walletController;
        this.console = console;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                console.printMenu();
                int getUserMenu = Integer.parseInt(console.getUserMenu());
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = menu.isNotExit();
                executeAction(menu);

            } catch (InvalidInputException e) {
                console.printError();
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }

    // handler mapping
    private void executeAction(Menu menu) throws IOException {
        switch (menu) {
            case CREATE:
                createVoucher();
                break;
            case LIST:
                console.printAllVoucher(voucherController.findAll());
                break;
            case BLACK_LIST:
                console.printAllMember(memberController.findAllBlackMember());
                break;
            case CREATE_MEMBER:
                createMember();
                break;
            case MEMBER_LIST:
                console.printAllMember(memberController.findAllMember());
                break;
            case ASSIGN_VOUCHER:
                assignVoucher();
                break;
            case VOUCHER_LIST_BY_MEMBER:
                console.printAllWallet(findVouchersByMember());
                break;
            case DELETE_WALLET:
                walletController.deleteWalletById(console.getWalletId());
                break;
            case MEMBER_LIST_BY_VOUCHER:
                console.printAllWallet(findMembersByVoucher());
                break;
            case WALLET_LIST:
                console.printAllWallet(walletController.findAllWallet());
                break;
        }
    }

    private void createMember() throws IOException{
        String memberName = console.getMemberName();
        memberController.createMember(new CreateMemberRequest(memberName, MemberStatus.COMMON));
    }

    private WalletListResponse findMembersByVoucher() throws IOException {
        return walletController.findMembersByVoucherId(console.getVoucherId());
    }

    private WalletListResponse findVouchersByMember() throws IOException {
        return walletController.findVouchersByMemberId(console.getMemberId());
    }

    private void assignVoucher() throws IOException {
        UUID memberUuid = console.getMemberId();
        UUID voucherUuid = console.getVoucherId();
        walletController.createWallet(new CreateWalletRequest(memberUuid, voucherUuid));
    }

    private void createVoucher() throws IOException {
        VoucherType voucherType = VoucherType.getTypeByNum(console.getVoucherTypes());
        double discountAmount = Double.parseDouble(console.getDiscountAmount());
        voucherController.create(new CreateVoucherRequest(voucherType, discountAmount));
    }
}
