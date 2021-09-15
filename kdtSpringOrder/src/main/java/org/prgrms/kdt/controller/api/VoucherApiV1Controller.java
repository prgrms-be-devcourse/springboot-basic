package org.prgrms.kdt.controller.api;

import org.prgrms.kdt.controller.view.CustomerForm;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class VoucherApiV1Controller {

    private final VoucherService voucherService;

    public VoucherApiV1Controller(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/Vouchers/new")
    public ResponseEntity createVoucherByCustomerId(VoucherSaveRequestDto voucherSaveRequestDto){
        return voucherService.createVoucher(voucherSaveRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

}
