package org.prgms.kdtspringweek1.controller.springMvcController;

import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.voucher.service.dto.SelectVoucherTypeDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/spring-mvc/voucher")
public class SpringMvcVoucherController {

    private final VoucherService voucherService;

    public SpringMvcVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping()
    public List<FindVoucherResponseDto> getAllVouchers() {
        return voucherService.searchAllVouchers();
    }

    @GetMapping("/voucher-type/{num}")
    public List<FindVoucherResponseDto> getVoucherByVoucherType(@PathVariable long num) {
        return voucherService.searchVouchersByVoucherType(SelectVoucherTypeDto.getVoucherTypeByNum(num).getName());
    }

    @GetMapping("/{voucherId}")
    public FindVoucherResponseDto getVoucherByVoucherId(@PathVariable String voucherId) {
        return voucherService.searchVoucherById(UUID.fromString(voucherId)).get();
    }

    @PostMapping()
    public void createVoucher(@RequestBody CreateVoucherRequestDto createVoucherRequestDto) {
        voucherService.registerVoucher(createVoucherRequestDto.toVoucher());
    }

    @DeleteMapping("/{voucherId}")
    public void deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
    }
}