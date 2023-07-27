package org.programmers.VoucherManagement.voucher.presentation;


import jakarta.validation.Valid;
import org.programmers.VoucherManagement.global.response.BaseResponse;
import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherCreateRequestData;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherCreateResponseData;
import org.programmers.VoucherManagement.voucher.presentation.dto.VoucherUpdateRequestData;
import org.programmers.VoucherManagement.voucher.presentation.mapper.VoucherControllerMapper;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.programmers.VoucherManagement.global.response.SuccessCode.DELETE_VOUCHER_SUCCESS;
import static org.programmers.VoucherManagement.global.response.SuccessCode.UPDATE_VOUCHER_SUCCESS;

@RestController
@RequestMapping("/voucher")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 바우처 생성
     *
     * @param data
     * @return BaseResponse<VoucherCreateResponse>
     */
    @PostMapping()
    public BaseResponse<VoucherCreateResponseData> createVoucher(@Valid @RequestBody VoucherCreateRequestData data) {
        return new BaseResponse<>(VoucherControllerMapper.INSTANCE.UpdateResponseToData(
                voucherService.saveVoucher(VoucherControllerMapper.INSTANCE.dataToCreateRequest(data))));
    }

    /**
     * 바우처 수정
     *
     * @param voucherId
     * @param data
     * @return BaseResponse<String>
     */
    @PatchMapping("/{voucherId}")
    public BaseResponse<String> updateVoucher(@PathVariable String voucherId, @Valid @RequestBody VoucherUpdateRequestData data) {
        UUID voucherUUID = UUID.fromString(voucherId);
        voucherService.updateVoucher(voucherUUID, VoucherControllerMapper.INSTANCE.dataToUpdateRequest(data));
        return new BaseResponse<>(UPDATE_VOUCHER_SUCCESS);
    }

    /**
     * 저장된 모든 바우처 조회
     *
     * @return BaseResponse<VoucherGetResponses>
     */
    @GetMapping()
    public BaseResponse<VoucherGetResponses> getVoucherList() {
        return new BaseResponse<>(voucherService.getVoucherList());
    }

    /**
     * 바우처 삭제
     *
     * @param voucherId
     * @return BaseResponse<String>
     */
    @DeleteMapping()
    public BaseResponse<String> deleteVoucher(@RequestParam String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        return new BaseResponse<>(DELETE_VOUCHER_SUCCESS);
    }

}
