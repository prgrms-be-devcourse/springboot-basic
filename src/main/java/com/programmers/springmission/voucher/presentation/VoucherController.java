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
        console.outputVoucherCrud();
        CrudType inputValue = CrudType.of(console.input());

        switch (inputValue) {
            case CREATE -> {
                VoucherResponse voucherResponse = createVoucher();
                console.outputVoucherCreate(voucherResponse);
            }
            case FIND_BY_ID -> {
                VoucherResponse voucherResponse = findByIdVoucher();
                console.outputVoucherFindById(voucherResponse);
            }
            case FIND_ALL -> {
                List<VoucherResponse> voucherResponse = findAllVoucher();
                console.outputVoucherFindAll(voucherResponse);
            }
            case UPDATE -> {
                VoucherResponse voucherResponse = updateVoucher();
                console.outputVoucherUpdate(voucherResponse);
            }
            case DELETE_BY_ID -> {
                deleteByIdVoucher();
                console.outputVoucherDeleteById();
            }
            case DELETE_ALL ->  {
                deleteAllVoucher();
                console.outputVoucherDeleteAll();
            }
        }
    }

    private VoucherResponse createVoucher() {
        VoucherType inputType = VoucherType.of(console.inputVoucherPolicy());
        long inputAmount = Long.parseLong(console.inputVoucherAmount());

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputType, inputAmount);
        return voucherService.createVoucher(voucherCreateRequest);
    }

    private VoucherResponse findByIdVoucher() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        return voucherService.findByIdVoucher(inputVoucherId);
    }

    private List<VoucherResponse> findAllVoucher() {
        return voucherService.findAllVoucher();
    }

    private VoucherResponse updateVoucher() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        long inputAmount = Long.parseLong(console.inputVoucherAmount());

        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(inputVoucherId, inputAmount);
        return voucherService.updateVoucher(voucherUpdateRequest);
    }

    private void deleteByIdVoucher() {
        UUID inputVoucherId = UUID.fromString(console.inputVoucherId());
        voucherService.deleteByIdVoucher(inputVoucherId);
    }

    private void deleteAllVoucher() {
        voucherService.deleteAllVoucher();
    }
}

