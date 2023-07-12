package kr.co.springbootweeklymission.console;

import kr.co.springbootweeklymission.member.presentation.MemberConsoleController;
import kr.co.springbootweeklymission.voucher.presentation.VoucherConsoleController;
import kr.co.springbootweeklymission.wallet.presentation.WalletConsoleController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class VoucherProgramRunner implements CommandLineRunner {
    private final MemberConsoleController memberConsoleController;
    private final VoucherConsoleController voucherConsoleController;
    private final WalletConsoleController walletConsoleController;

    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        do {
            OutputView.outputCommand();
            Command command = InputView.inputCommand();

            switch (command) {
                case CREATE_MEMBER -> memberConsoleController.createMember();
                case UPDATE_MEMBER -> memberConsoleController.updateMemberById();
                case DELETE_MEMBER -> memberConsoleController.deleteMemberById();
                case READ_MEMBER -> OutputView.outputMember(memberConsoleController.getMemberById());
                case READ_ALL_BLACK_MEMBER -> OutputView.outputBlackMembers(memberConsoleController.getBlackMembers());
                case CREATE_VOUCHER -> voucherConsoleController.createVoucher();
                case UPDATE_VOUCHER -> voucherConsoleController.updateVoucherById();
                case DELETE_VOUCHER -> voucherConsoleController.deleteVoucherById();
                case READ_VOUCHER -> OutputView.outputVoucher(voucherConsoleController.getVoucherById());
                case READ_ALL_VOUCHERS -> OutputView.outputVouchers(voucherConsoleController.getVouchersAll());
                case CREATE_VOUCHER_MEMBER -> walletConsoleController.createWallet();
                case READ_VOUCHERS_BY_MEMBER ->
                        OutputView.outputVouchers(walletConsoleController.getVouchersByMemberId());
                case READ_MEMBER_BY_VOUCHER -> OutputView.outputMember(walletConsoleController.getMemberByVoucherId());
                case DELETE_VOUCHER_MEMBER -> walletConsoleController.deleteByVoucherIdAndMemberId();
                case EXIT -> isRunning = false;
            }

        } while (isRunning);
    }
}
