package com.programmers.springbootbasic.domain.voucher.presentation;

import com.programmers.springbootbasic.common.dto.BaseListResponse;
import com.programmers.springbootbasic.domain.voucher.application.VoucherService;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherCriteria;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = "tomcat")
@RestController
@RequestMapping("/api/voucher")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public UUID createVoucher(@Valid @RequestBody CreateVoucherRequest request) {
        return voucherService.create(request);
    }

    @GetMapping("/vouchers")
    public BaseListResponse<VoucherResponse> getAllVouchers() {
        return BaseListResponse.from(voucherService.findAll());
    }

    @GetMapping("/vouchers/search")
    public BaseListResponse<VoucherResponse> getVouchersByCriteria(
        @Valid @ModelAttribute VoucherCriteria criteria
    ) {
        return BaseListResponse.from(voucherService.findByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public VoucherResponse getVoucherById(@PathVariable UUID id) {
        return voucherService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteVoucherById(@PathVariable UUID id) {
        voucherService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateVoucher(@PathVariable UUID id, @RequestBody UpdateVoucherRequest request) {
        voucherService.update(id, request);
    }
}
