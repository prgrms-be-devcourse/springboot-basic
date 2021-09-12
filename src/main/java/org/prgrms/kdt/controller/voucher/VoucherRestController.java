package org.prgrms.kdt.controller.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.controller.ApiResult;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.error.NotFoundException;
import org.prgrms.kdt.service.SimpleVoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.prgrms.kdt.controller.ApiResult.OK;

@Slf4j
@RestController
@RequestMapping("api")
public class VoucherRestController {

    private final SimpleVoucherService simpleVoucherService;

    public VoucherRestController(SimpleVoucherService simpleVoucherService) {
        this.simpleVoucherService = simpleVoucherService;
    }

    @GetMapping(path = "voucher/list")
    public ApiResult<List<VoucherDto>> vouchers() {
        return OK(
                simpleVoucherService.list().stream()
                        .map(VoucherDto::new)
                        .collect(toList())
        );
    }

    @GetMapping(path = "voucher/{voucherId}")
    public ApiResult<VoucherDto> voucher(
            @PathVariable Long voucherId
    ) {
        return OK(
                simpleVoucherService.findByVoucherId(voucherId)
                .map(VoucherDto::new)
                .orElseThrow(() -> new NotFoundException(Voucher.class, voucherId))
        );
    }

    @PostMapping(path = "voucher")
    public ApiResult<VoucherDto> create(
            @RequestBody VoucherRequest request
    ) {
        return OK(
                new VoucherDto(
                        simpleVoucherService.create(request.getVoucherType())
                )
        );
    }

    @PatchMapping(path = "voucher")
    public ApiResult<VoucherDto> allocate(
            @RequestBody VoucherRequest request
    ) {
        return OK(
                new VoucherDto(
                        simpleVoucherService.allocate(request.getVoucherId(), request.getCustomerId())
                )
        );
    }
}
