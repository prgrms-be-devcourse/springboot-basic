package team.marco.voucher_management_system.controller.voucher;

import org.springframework.web.bind.annotation.*;
import team.marco.voucher_management_system.controller.ApiResponse;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ApiResponse<List<VoucherResponse>> findAllVouchers() {
        List<VoucherResponse> vouchers = voucherService.getVouchers().stream()
                .map(VoucherResponse::of)
                .toList();

        return ApiResponse.ok(vouchers);
    }

    @GetMapping("/{voucherId}")
    public ApiResponse<VoucherResponse> getVoucher(@PathVariable Long voucherId) {
        VoucherResponse voucher = VoucherResponse.of(voucherService.getVoucher(voucherId));

        return ApiResponse.ok(voucher);
    }

    /**
     *  TODO 조건별 조회기능 (바우처 생성기간 및 특정 할인 타입별)
     */

    @PostMapping
    public ApiResponse<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        Voucher voucher = voucherService.createVoucher(request.toServiceRequest());
        VoucherResponse response = VoucherResponse.of(voucher);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{voucherId}")
    public ApiResponse deleteVoucher(@PathVariable Long voucherId) {
        voucherService.deleteVoucher(voucherId);

        return ApiResponse.ok();
    }
}
