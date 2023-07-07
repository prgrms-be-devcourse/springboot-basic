package org.promgrammers.springbootbasic.domain.voucher.controller;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.DeleteVoucherType;
import org.promgrammers.springbootbasic.domain.voucher.model.FindVoucherType;
import org.promgrammers.springbootbasic.domain.voucher.model.UpdateVoucherType;
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
            case FIND_ONE -> findByType();

            case UPDATE -> updateByType();

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

    private void findByType() {
        String inputType = console.askForVoucherFindType();
        FindVoucherType findTYpe = FindVoucherType.from(inputType);

        switch (findTYpe) {
            case FIND_BY_VOUCHER_ID -> findById();

            case FIND_BY_CUSTOMER_ID -> findByCustomerId();
        }
    }

    private VoucherListResponse findByCustomerId() {
        String requestCustomer = console.askForCustomerId();
        UUID customerId = UUID.fromString(requestCustomer);

        VoucherListResponse voucherListResponse = voucherService.findAllByCustomerId(customerId);
        console.print(voucherListResponse.toString());
        return voucherListResponse;
    }

    private VoucherResponse findById() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        VoucherResponse voucherResponse = voucherService.findById(voucherId);
        console.print(voucherResponse.voucherOutput());
        return voucherResponse;
    }

    private VoucherListResponse findAll() {
        return voucherService.findAll();
    }

    private void updateByType() {
        String inputType = console.askForVoucherUpdateType();
        UpdateVoucherType updateType = UpdateVoucherType.from(inputType);

        switch (updateType) {
            case ASSIGN -> assignVoucherToCustomer();

            case BY_ID -> update();

            case REMOVE -> removeVoucherFromCustomer();
        }
    }

    private void removeVoucherFromCustomer() {
        String requestCustomer = console.askForCustomerId();
        UUID customerId = UUID.fromString(requestCustomer);

        String requestVoucher = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestVoucher);

        voucherService.removeVoucherFromCustomer(customerId, voucherId);
        console.print("바우처 삭제가 완료 되었습니다.");
    }

    private void assignVoucherToCustomer() {
        String requestCustomer = console.askForCustomerId();
        UUID customerId = UUID.fromString(requestCustomer);

        String requestVoucher = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestVoucher);

        voucherService.assignVoucherToCustomer(customerId, voucherId);
        console.print("바우처 할당이 완료되었습니다.");
    }

    private VoucherResponse update() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        long discountAmount = console.askForDiscountAmount();

        UpdateVoucherRequest updateVoucherRequest = new UpdateVoucherRequest(voucherId, discountAmount);

        VoucherResponse voucherResponse = voucherService.update(updateVoucherRequest);
        console.print(voucherResponse.toString());

        return voucherResponse;
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
