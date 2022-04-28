package org.prgrms.springbasic.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.controller.api.request.VoucherRequest;
import org.prgrms.springbasic.controller.api.response.SuccessResponse;
import org.prgrms.springbasic.controller.api.response.VoucherResponse;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.service.web.VoucherService;
import org.prgrms.springbasic.utils.exception.NoDatabaseChangeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.prgrms.springbasic.domain.voucher.VoucherType.valueOf;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_DELETED;
import static org.springframework.http.HttpStatus.OK;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VoucherApiController {

    private final VoucherService voucherService;

    //전체 조회기능
    @GetMapping("/vouchers")
    public SuccessResponse<List<VoucherResponse>> voucherList(HttpServletRequest request) {
        var voucherResponses = voucherService.findVouchers()
                .stream()
                .map(VoucherResponse::of)
                .toList();

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                OK.value(),
                "succeed in searching vouchers.",
                voucherResponses
        );
    }

    //조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
    @GetMapping("/vouchers/period")
    public SuccessResponse<List<VoucherResponse>> voucherSearchByCreatedAt(HttpServletRequest request, @RequestParam String from, @RequestParam String to) {
        var voucherResponses = voucherService.findVoucherByPeriod(from, to)
                .stream()
                .map(VoucherResponse::of)
                .toList();

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                request.getQueryString(),
                OK.value(),
                "succeed in searching vouchers by period.",
                voucherResponses
        );
    }

    @GetMapping("/{voucherType}/vouchers")
    public SuccessResponse<List<VoucherResponse>> voucherByType(HttpServletRequest request, @PathVariable String voucherType) {
        var voucherResponses = voucherService.findVoucherByVoucherType(valueOf(voucherType.toUpperCase())).stream()
                .map(VoucherResponse::of).toList();

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                OK.value(),
                "succeed in searching vouchers by type.",
                voucherResponses
        );
    }

    //바우처 추가기능
    @PostMapping("/vouchers/new")
    public SuccessResponse<VoucherResponse> voucherAdd(HttpServletRequest request, @RequestBody VoucherRequest voucherRequest) {
        var voucherType = voucherRequest.getVoucherType();

        var newVoucher = voucherType.createVoucher(voucherRequest.getDiscountInfo());

        var savedVoucher = voucherService.addVoucher(newVoucher);

        var voucherResponse = VoucherResponse.of(savedVoucher);

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                OK.value(),
                "succeed in creating new voucher.",
                voucherResponse
        );
    }

    //바우처 삭제기능
    @DeleteMapping("/{voucherId}/vouchers")
    public SuccessResponse<Boolean> voucherRemove(HttpServletRequest request, @PathVariable UUID voucherId) {
        System.out.println(voucherId);
        var isDeleted = voucherService.removeVoucherById(voucherId);

        if (!isDeleted) {
            throw new NoDatabaseChangeException(NOT_DELETED.getMessage());
        }

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                OK.value(),
                "succeed in deleting a voucher.",
                null
        );
    }


    //바우처 아이디로 조회 가능
    @GetMapping("/{voucherId}/vouchers/detail")
    public SuccessResponse<VoucherResponse> voucherSearch(HttpServletRequest request, @PathVariable UUID voucherId) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        var voucherResponse = VoucherResponse.of(retrievedVoucher);

        return SuccessResponse.responseOf(
                now(),
                request.getRequestURI(),
                OK.value(),
                "succeed in deleting vouchers.",
                voucherResponse
        );
    }
}