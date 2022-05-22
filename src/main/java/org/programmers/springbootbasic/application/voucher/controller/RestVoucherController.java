package org.programmers.springbootbasic.application.voucher.controller;

import javassist.NotFoundException;
import org.programmers.springbootbasic.application.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.VoucherResponse;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class RestVoucherController {
    private final DefaultVoucherService defaultVoucherService;

    private final VoucherConverter voucherConverter;

    public RestVoucherController(DefaultVoucherService defaultVoucherService, VoucherConverter voucherConverter) {
        this.defaultVoucherService = defaultVoucherService;
        this.voucherConverter = voucherConverter;
    }

    @PostMapping("/vouchers")
    public VoucherResponse createVoucher(@RequestBody CreateVoucherRequest voucherRequest) {
        var voucher = defaultVoucherService.createVoucher(voucherRequest);
        return voucherConverter.convertVoucherDto(voucher);
    }

    @GetMapping("/vouchers/{voucherId}")
    public VoucherResponse findVoucher(@PathVariable("voucherId") UUID voucherId) throws NotFoundException {
        var voucher = defaultVoucherService.getVoucher(voucherId);
        return voucherConverter.convertVoucherDto(voucher);
    }

    @GetMapping("/vouchers")
    public List<VoucherResponse> findVouchers() {
        return defaultVoucherService
                .getVoucherList().stream()
                .map(voucherConverter::convertVoucherDto)
                .toList();
    }

    @GetMapping("/vouchers/type/{voucherType}")
    public List<VoucherResponse> findVoucherByVoucherType(@PathVariable("voucherType") String voucherType) {
        var type = VoucherType.findByType(voucherType);
        return defaultVoucherService.
                getVoucherListByVoucherType(type).stream()
                .map(voucherConverter::convertVoucherDto)
                .toList();
    }

    //vouchers?order=created-at
    @GetMapping("/vouchers/created-at")
    public List<VoucherResponse> findVoucherOrderByCreatedAt() {
        return defaultVoucherService.
                getVoucherListOrderByCreatedAt().stream()
                .map(voucherConverter::convertVoucherDto)
                .toList();
    }

    @PutMapping("/vouchers/{voucherId}") //pathVariable voucherId
    public VoucherResponse updateVoucher(@RequestBody UpdateVoucherRequest updateVoucherRequest) throws NotFoundException {
        var updateVoucher = defaultVoucherService.updateVoucher(updateVoucherRequest);
        return voucherConverter.convertVoucherDto(updateVoucher);
    }

    @DeleteMapping("/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        defaultVoucherService.deleteVoucher(voucherId);
    }
}
