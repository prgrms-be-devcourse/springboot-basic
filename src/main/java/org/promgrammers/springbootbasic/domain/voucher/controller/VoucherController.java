package org.promgrammers.springbootbasic.domain.voucher.controller;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.DeleteVoucherType;
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
                VoucherListResponse voucherList = findAll();
                console.print(voucherList.toString());
            }
            case FIND_ONE -> {
                VoucherResponse foundVoucher = findById();
                console.print(foundVoucher.voucherOutput());
            }
            case UPDATE -> {
                VoucherResponse updatedVoucher = update();
                console.print(updatedVoucher.voucherOutput());
            }
            case DELETE -> {
                deleteByType();
                console.print("삭제가 완료되었습니다.");
            }
        }
    }

    private VoucherResponse createVoucher() {
        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.from(inputVoucherType);
        long discountAmount = console.askForDiscountAmount();

        CreateVoucherRequest createVoucherRequest = CreateVoucherRequest.of(voucherType, discountAmount);
        return voucherService.create(createVoucherRequest);
    }

    private VoucherResponse findById() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        return voucherService.findById(voucherId);
    }

    private VoucherListResponse findAll() {
        return voucherService.findAll();
    }

    private VoucherResponse update() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.from(inputVoucherType);

        long discountAmount = console.askForDiscountAmount();

        UpdateVoucherRequest updateVoucherRequest = new UpdateVoucherRequest(voucherId, voucherType, discountAmount);

        return voucherService.update(updateVoucherRequest);
    }

    private void deleteByType() {
        String inputType = console.askForVoucherDeleteType();
        DeleteVoucherType deleteType = DeleteVoucherType.from(inputType);

        switch (deleteType) {
            case ID -> deleteVoucherById();

            case ALL -> deleteAll();
        }
    }

    private void deleteVoucherById() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        voucherService.deleteById(voucherId);
    }

    private void deleteAll() {
        voucherService.deleteAll();
    }
}
