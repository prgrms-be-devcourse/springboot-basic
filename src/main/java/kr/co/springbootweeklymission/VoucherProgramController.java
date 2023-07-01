package kr.co.springbootweeklymission;

import kr.co.springbootweeklymission.member.api.MemberController;
import kr.co.springbootweeklymission.member.api.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.view.Command;
import kr.co.springbootweeklymission.view.InputView;
import kr.co.springbootweeklymission.view.OutputView;
import kr.co.springbootweeklymission.voucher.api.VoucherController;
import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Profile("!test")
@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherProgramController implements CommandLineRunner {
    private final MemberController memberController;
    private final VoucherController voucherController;

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
                memberController.createMember(create);
                continue;
            }

            if (command.isUpdateMember()) {
                OutputView.outputUpdateMember();
                OutputView.outputMemberStatus();
                UUID memberId = UUID.fromString(InputView.inputMemberId());
                final MemberReqDTO.UPDATE update = MemberReqDTO.UPDATE.builder()
                        .memberStatus(InputView.inputMemberStatus())
                        .build();
                memberController.updateMemberById(memberId, update);
                continue;
            }

            if (command.isDeleteMember()) {
                OutputView.outputDeleteMember();
                memberController.deleteMemberById(UUID.fromString(InputView.inputMemberId()));
                continue;
            }

            if (command.isReadMember()) {
                OutputView.outputMember(memberController.getMemberById(UUID.fromString(InputView.inputMemberId())));
                continue;
            }

            if (command.isReadAllBlackMember()) {
                OutputView.outputBlackMembers(memberController.getMembersByBlack());
                continue;
            }

            if (command.isCreateVoucher()) {
                OutputView.outputCreateVoucher();
                OutputView.outputVoucherPolicy();
                final VoucherReqDTO.CREATE create = VoucherReqDTO.CREATE.builder()
                        .voucherPolicy(InputView.inputVoucherPolicy())
                        .amount(InputView.inputAmount())
                        .build();
                voucherController.createVoucher(create);
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
                voucherController.updateVoucherById(voucherId, update);
                continue;
            }

            if (command.isDeleteVoucher()) {
                OutputView.outputDeleteVoucher();
                voucherController.deleteVoucherById(UUID.fromString(InputView.inputVoucherId()));
                continue;
            }

            if (command.isReadVoucher()) {
                OutputView.outputVoucher(voucherController.getVoucherById(UUID.fromString(InputView.inputVoucherId())));
                continue;
            }

            if (command.isReadAllVouchers()) {
                OutputView.outputVouchers(voucherController.getVouchersAll());
                continue;
            }

            IS_RUNNING = false;

        } while (IS_RUNNING);
    }
}
