package org.promgrammers.springbootbasic.domain.voucher.controller;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherService;
import org.promgrammers.springbootbasic.view.Console;
import org.promgrammers.springbootbasic.view.CrudMenu;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void execute() {
        String inputCommand = console.voucherCommandGuide();
        CrudMenu crudMenu = CrudMenu.from(inputCommand);

        switch (crudMenu) {
            case CREATE -> {
                VoucherResponse voucher = createVoucher();
                console.print("Voucher가 생성되었습니다. : \n   " + voucher.voucherOutput());
            }
            case FIND_ALL -> {
                console.print(findAll().toString());
            }
            case FIND_ONE -> {
                console.print(findById().voucherOutput());
            }
            case UPDATE -> {
                console.print(update().voucherOutput());
            }
            case DELETE -> {
                deleteAll();
                console.print("삭제가 완료되었습니다.");
            }
        }
    }

    public VoucherResponse createVoucher() {
        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.from(inputVoucherType);
        long discountAmount = console.askForDiscountAmount();

        CreateVoucherRequest createVoucherRequest = CreateVoucherRequest.of(voucherType, discountAmount);
        return voucherService.create(createVoucherRequest);
    }

    private VoucherResponse findById() {
        console.print("조회할 바우처 ID를 입력하세요");
        String requestId = console.input();
        UUID voucherId = UUID.fromString(requestId);

        return voucherService.findById(voucherId);
    }

    private VoucherListResponse findAll() {
        return voucherService.findAll();
    }

    private VoucherResponse update() {
        console.print("수정할 바우처 ID를 입력하세요");
        String requestId = console.input();
        UUID voucherId = UUID.fromString(requestId);

        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.from(inputVoucherType);

        long discountAmount = console.askForDiscountAmount();

        UpdateVoucherRequest updateVoucherRequest = new UpdateVoucherRequest(voucherId, voucherType, discountAmount);

        return voucherService.update(updateVoucherRequest);
    }

    private void deleteAll() {
        voucherService.deleteAll();
    }
}
