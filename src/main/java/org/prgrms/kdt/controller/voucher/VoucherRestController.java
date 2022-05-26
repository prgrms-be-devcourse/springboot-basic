package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.controller.ApiResponse;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.controller.ApiResponse.ok;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ApiResponse<List<VoucherDto>> findAll(VoucherSearch search) {
        List<VoucherDto> vouchers = voucherService.findAll(
                search.getStartDate(),
                search.getEndDate(),
                search.getType()
            ).stream()
            .map(VoucherDto::new).toList();

        return ok(vouchers);
    }

    @GetMapping("/{id}")
    public ApiResponse<VoucherDto> findById(@PathVariable UUID id) {
        Voucher voucher = voucherService.findById(id);

        return ok(new VoucherDto(voucher));
    }

    @PostMapping
    public ApiResponse<VoucherDto> create(@RequestBody CreateVoucherRequest request) {
        Voucher voucher = voucherService.create(
            UUID.randomUUID(),
            request.getVoucherValue(),
            request.getVoucherType()
        );

        return ok(new VoucherDto(voucher));
    }

    @PatchMapping
    public ApiResponse<VoucherDto> update(@RequestBody UpdateVoucherRequest request) {
        Voucher voucher = voucherService.update(
            request.getVoucherId(),
            request.getVoucherValue()
        );

        return ok(new VoucherDto(voucher));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        voucherService.delete(id);
    }
}
