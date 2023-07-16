package com.programmers.springweekly;

import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.VoucherMenu;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.view.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ConsoleVoucher {

    private final VoucherController voucherController;
    private final Console console;

    public void menu() {
        console.outputVoucherMenuGuide();
        VoucherMenu voucherMenu = VoucherMenu.from(console.inputMessage());

        switch (voucherMenu) {
            case CREATE -> createVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
            case SELECT -> selectVoucher();
            default -> throw new IllegalArgumentException("Input :" + voucherMenu + "찾으시는 바우처 메뉴가 없습니다.");
        }
    }

    private void createVoucher() {
        console.outputSelectCreateVoucherGuide();
        VoucherType voucherType = VoucherType.from(console.inputMessage());

        console.outputDiscountGuide();
        VoucherCreateRequest voucherCreateRequest = console.inputVoucherCreate(voucherType);

        voucherController.save(voucherCreateRequest);
        console.outputCompleteGuide();
    }

    private void updateVoucher() {
        console.outputVoucherUUIDGuide();
        UUID voucherId = console.inputUUID();

        console.outputVoucherUpdateGuide();
        VoucherUpdateRequest voucherUpdateRequest = console.inputVoucherUpdate(voucherId);

        voucherController.update(voucherUpdateRequest);
        console.outputCompleteGuide();
    }

    private void deleteVoucher() {
        console.outputVoucherUUIDGuide();
        UUID voucherId = console.inputUUID();

        if (!voucherController.existById(voucherId)) {
            console.outputErrorMessage(voucherId + " 찾는 바우처가 존재하지 않습니다.");
            return;
        }

        if (voucherController.deleteById(voucherId) == 0) {
            console.outputErrorMessage(voucherId + " 삭제하려는 바우처가 저장되어있지 않아 삭제할 수 없습니다.");
            return;
        }

        console.outputCompleteGuide();
    }

    private void selectVoucher() {
        VoucherListResponse voucherListResponse = voucherController.findAll();

        if (CollectionUtils.isEmpty(voucherListResponse.getVoucherList())) {
            console.outputErrorMessage("저장된 바우처가 없습니다.");
            return;
        }

        console.outputGetVoucherAll(voucherListResponse);
    }

}
