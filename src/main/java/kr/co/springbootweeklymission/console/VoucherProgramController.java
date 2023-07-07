package kr.co.springbootweeklymission.console;

import kr.co.springbootweeklymission.member.presentation.MemberConsoleController;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.VoucherConsoleController;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.wallet.presentation.WalletConsoleController;
import kr.co.springbootweeklymission.wallet.presentation.dto.request.WalletReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

@RequiredArgsConstructor
public class VoucherProgramController implements CommandLineRunner {
    private final MemberConsoleController memberConsoleController;
    private final VoucherConsoleController voucherConsoleController;
    private final WalletConsoleController walletConsoleController;

    private static boolean IS_RUNNING = true;

    @Override
    public void run(String... args) {
        do {
            OutputView.outputCommand();
            Command command = InputView.inputCommand();

            if (command.isCreateMember()) {
                OutputView.outputCreateMember();
                OutputView.outputMemberStatus();
                final MemberReqDTO.CREATE create = MemberReqDTO.CREATE.builder()
                        .memberStatus(InputView.inputMemberStatus())
                        .build();
                memberConsoleController.createMember(create);
                continue;
            }

            if (command.isUpdateMember()) {
                OutputView.outputUpdateMember();
                OutputView.outputMemberStatus();
                UUID memberId = UUID.fromString(InputView.inputMemberId());
                final MemberReqDTO.UPDATE update = MemberReqDTO.UPDATE.builder()
                        .memberStatus(InputView.inputMemberStatus())
                        .build();
                memberConsoleController.updateMemberById(memberId, update);
                continue;
            }

            if (command.isDeleteMember()) {
                OutputView.outputDeleteMember();
                memberConsoleController.deleteMemberById(UUID.fromString(InputView.inputMemberId()));
                continue;
            }

            if (command.isReadMember()) {
                OutputView.outputMember(memberConsoleController.getMemberById(UUID.fromString(InputView.inputMemberId())));
                continue;
            }

            if (command.isReadAllBlackMember()) {
                OutputView.outputBlackMembers(memberConsoleController.getMembersByBlack());
                continue;
            }

            if (command.isCreateVoucher()) {
                OutputView.outputCreateVoucher();
                OutputView.outputVoucherPolicy();
                final VoucherReqDTO.CREATE create = VoucherReqDTO.CREATE.builder()
                        .voucherPolicy(InputView.inputVoucherPolicy())
                        .amount(InputView.inputAmount())
                        .build();
                voucherConsoleController.createVoucher(create);
                continue;
            }

            if (command.isUpdateVoucher()) {
                OutputView.outputUpdateVoucher();
                OutputView.outputVoucherPolicy();
                UUID voucherId = UUID.fromString(InputView.inputVoucherId());
                final VoucherReqDTO.UPDATE update = VoucherReqDTO.UPDATE.builder()
                        .voucherPolicy(InputView.inputVoucherPolicy())
                        .amount(InputView.inputAmount())
                        .build();
                voucherConsoleController.updateVoucherById(voucherId, update);
                continue;
            }

            if (command.isDeleteVoucher()) {
                OutputView.outputDeleteVoucher();
                voucherConsoleController.deleteVoucherById(UUID.fromString(InputView.inputVoucherId()));
                continue;
            }

            if (command.isReadVoucher()) {
                OutputView.outputVoucher(voucherConsoleController.getVoucherById(UUID.fromString(InputView.inputVoucherId())));
                continue;
            }

            if (command.isReadAllVouchers()) {
                OutputView.outputVouchers(voucherConsoleController.getVouchersAll());
                continue;
            }

            if (command.isCreateVoucherMember()) {
                OutputView.outputCreateVoucherMember();
                final WalletReqDTO.CREATE create = WalletReqDTO.CREATE.builder()
                        .voucherId(UUID.fromString(InputView.inputVoucherId()))
                        .memberId(UUID.fromString(InputView.inputMemberId()))
                        .build();
                walletConsoleController.createVoucherMember(create);
                continue;
            }

            if (command.isReadVouchersByMember()) {
                OutputView.outputVouchers(walletConsoleController.getVouchersByMemberId(UUID.fromString(InputView.inputMemberId())));
                continue;
            }

            if (command.isReadMemberByVoucher()) {
                OutputView.outputMember(walletConsoleController.getMemberByVoucherId(UUID.fromString(InputView.inputVoucherId())));
                continue;
            }

            if (command.isDeleteVoucherMember()) {
                OutputView.outputDeleteVoucherMember();
                final WalletReqDTO.DELETE delete = WalletReqDTO.DELETE.builder()
                        .voucherId(UUID.fromString(InputView.inputVoucherId()))
                        .memberId(UUID.fromString(InputView.inputMemberId()))
                        .build();
                walletConsoleController.deleteVoucherMemberByVoucherIdAndMemberId(delete);
                continue;
            }

            IS_RUNNING = false;

        } while (IS_RUNNING);
    }
}
