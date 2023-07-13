package com.programmers.springmission.voucher.presentation;

import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.CrudType;
import com.programmers.springmission.voucher.application.VoucherService;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.request.VoucherUpdateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        console.outputVoucherMenu();
        CrudType inputValue = CrudType.of(console.input());

        switch (inputValue) {
            case CREATE -> {
                VoucherResponse voucherResponse = createVoucher();
                console.outputVoucherCreate(voucherResponse);
            }
            case FIND_ONE -> {
                VoucherResponse voucherResponse = findOneVoucher();
                console.outputOneVoucher(voucherResponse);
            }
            case FIND_ALL -> {
                List<VoucherResponse> voucherResponse = voucherService.findAllVoucher();
                console.outputAllVoucher(voucherResponse);
            }
            case UPDATE -> {
                VoucherResponse voucherResponse = updateAmount();
                console.outputVoucherUpdate(voucherResponse);
            }
            case DELETE_BY_ID -> {
                deleteVoucher();
                console.outputVoucherDelete();
            }
            case DELETE_ALL ->  {
                voucherService.deleteAllVoucher();
                console.outputVoucherDeleteAll();
            }
            case WALLET -> {
                VoucherResponse voucherResponse = updateCustomer();
                console.outputVoucherAssign(voucherResponse);
            }
        }
    }

    private VoucherResponse createVoucher() {
        VoucherType inputType = VoucherType.of(console.inputVoucherPolicy());
        long inputAmount = Long.parseLong(console.inputVoucherAmount());

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputType, inputAmount);
        return voucherService.createVoucher(voucherCreateRequest);
    }

    private VoucherResponse findOneVoucher() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        return voucherService.findOneVoucher(inputVoucherId);
    }

    private VoucherResponse updateAmount() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        long inputAmount = Long.parseLong(console.inputVoucherAmount());

        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(inputAmount);
        return voucherService.updateAmount(inputVoucherId, voucherUpdateRequest);
    }

    private void deleteVoucher() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        voucherService.deleteVoucher(inputVoucherId);
    }

    private VoucherResponse updateCustomer() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        UUID inputCustomerId = UUID.fromString(console.inputCustomerId());
        return voucherService.updateCustomer(inputVoucherId, inputCustomerId);
    }
}

