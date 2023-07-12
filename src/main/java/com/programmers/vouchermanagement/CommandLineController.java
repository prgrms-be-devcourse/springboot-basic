package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.view.Command;
import com.programmers.vouchermanagement.view.InputView;
import com.programmers.vouchermanagement.view.OutputView;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.UUID;

@Profile("!test")
@Slf4j
//@Controller
@RequiredArgsConstructor
public class CommandLineController implements CommandLineRunner {

    private final VoucherController voucherController;

    @Override
    public void run(String... args) {
        boolean running = true;

        while (running) {
            try {
                running = isRunning();
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }

        }
    }

    private boolean isRunning() {
        OutputView.showCommand();
        Command command = InputView.inputCommand();
        switch (command) {
            case CREATE -> {
                OutputView.showDiscountType();
                VoucherCreationRequest request = InputView.inputVoucherInfo();
                voucherController.createVoucher(request);
            }
            case LIST-> {
                List<VoucherResponse> vouchers = voucherController.getVouchers();
                OutputView.showVouchers(vouchers);
            }
            case EXIT -> {
                return false;
            }
            case UPDATE -> {
                List<VoucherResponse> vouchers = voucherController.getVouchers();
                OutputView.showVoucherUpdate(vouchers);
                UUID id = InputView.inputVoucherUpdate(vouchers);
                OutputView.showDiscountType();
                VoucherCreationRequest updateVoucherInfo = InputView.inputVoucherInfo();
                VoucherUpdateRequest request = new VoucherUpdateRequest(id, updateVoucherInfo.type(), updateVoucherInfo.amount());
                voucherController.updateVoucher(request);
            }
            case DELETE -> {
                List<VoucherResponse> vouchers = voucherController.getVouchers();
                OutputView.showVoucherDelete(vouchers);
                UUID id = InputView.inputVoucherDelete(vouchers);
                voucherController.deleteVoucher(id);
            }
        }
        return true;
    }
}
