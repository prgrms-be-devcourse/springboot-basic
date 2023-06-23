package kr.co.springbootweeklymission;

import kr.co.springbootweeklymission.domain.member.api.MemberController;
import kr.co.springbootweeklymission.domain.voucher.api.VoucherController;
import kr.co.springbootweeklymission.domain.voucher.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.global.view.Command;
import kr.co.springbootweeklymission.global.view.InputView;
import kr.co.springbootweeklymission.global.view.OutputView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

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

            if (command.isBlackList()) {
                OutputView.outputBlackMembers(memberController.getMembersByBlack());
                continue;
            }

            if (command.isVoucherList()) {
                OutputView.outputVouchers(voucherController.getVouchersAll());
                continue;
            }

            if (command.isCreateVoucher()) {
                OutputView.outputVoucherPolicy();
                final VoucherReqDTO.CREATE create = VoucherReqDTO.CREATE.builder()
                        .voucherPolicy(InputView.inputVoucherPolicy())
                        .amount(InputView.inputAmount())
                        .build();
                voucherController.createVoucher(create);
                OutputView.outputCreateVoucher();
                continue;
            }

            IS_RUNNING = false;

        } while (IS_RUNNING);
    }
}
