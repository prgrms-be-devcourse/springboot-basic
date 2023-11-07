package org.prgms.kdtspringweek1.controller.restController;

import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.voucher.service.dto.SelectVoucherTypeDto;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rest")
@Profile({"rest"})
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public List<FindVoucherResponseDto> getAllVouchers() {
        return voucherService.searchAllVouchers();
    }

    @GetMapping("/vouchers/type")
    public List<FindVoucherResponseDto> getVoucherByVoucherType(@RequestParam String num) {
        return voucherService.searchVouchersByVoucherType(SelectVoucherTypeDto.getVoucherTypeByNum(Long.parseLong(num)).getName());
    }

    @GetMapping("/voucher")
    public FindVoucherResponseDto getVoucherByVoucherId(@RequestParam String id) {
        return voucherService.searchVoucherById(UUID.fromString(id)).get();
    }

    @PostMapping("/voucher")
    public void createVoucher(@RequestBody CreateVoucherRequestDto createVoucherRequestDto) {
        voucherService.registerVoucher(createVoucherRequestDto.toVoucher());
    }

    @DeleteMapping("/voucher")
    public void deleteVoucher(@RequestParam String id) {
        voucherService.deleteVoucherById(UUID.fromString(id));
    }
}