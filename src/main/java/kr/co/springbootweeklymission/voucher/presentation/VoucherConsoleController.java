package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.console.InputView;
import kr.co.springbootweeklymission.console.OutputView;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherConsoleController {
    private final VoucherService voucherService;

    public void createVoucher() {
        OutputView.outputCreateVoucher();
        OutputView.outputVoucherPolicy();
        final VoucherReqDTO.CREATE createVoucher = VoucherReqDTO.CREATE.builder()
                .voucherPolicy(InputView.inputVoucherPolicy())
                .amount(InputView.inputAmount())
                .build();
        voucherService.createVoucher(createVoucher);
    }

    public VoucherResDTO.READ getVoucherById() {
        UUID voucherId = UUID.fromString(InputView.inputVoucherId());
        return voucherService.getVoucherById(voucherId);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        return voucherService.getVouchersAll();
    }

    public void updateVoucherById() {
        OutputView.outputUpdateVoucher();
        OutputView.outputVoucherPolicy();
        UUID voucherId = UUID.fromString(InputView.inputVoucherId());
        final VoucherReqDTO.UPDATE update = VoucherReqDTO.UPDATE.builder()
                .voucherPolicy(InputView.inputVoucherPolicy())
                .amount(InputView.inputAmount())
                .build();
        voucherService.updateVoucherById(voucherId, update);
    }

    public void deleteVoucherById() {
        OutputView.outputDeleteVoucher();
        UUID voucherId = UUID.fromString(InputView.inputVoucherId());
        voucherService.deleteVoucherById(voucherId);
    }
}
