package com.prgrms.voucher.controller;

import com.prgrms.common.util.Generator;
import com.prgrms.common.codes.SuccessCode;
import com.prgrms.common.response.ApiResponse;
import com.prgrms.voucher.controller.dto.VoucherListRequest;
import com.prgrms.voucher.controller.dto.VoucherCreateRequest;
import com.prgrms.voucher.controller.dto.VoucherDeleteResponse;
import com.prgrms.voucher.controller.mapper.VoucherControllerConverter;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import com.prgrms.voucher.service.VoucherService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;
    private final Generator generator;
    private final VoucherControllerConverter voucherControllerConverter;

    public VoucherRestController(VoucherService voucherService, Generator generator,
            VoucherControllerConverter voucherControllerConverter) {
        this.voucherService = voucherService;
        this.generator = generator;
        this.voucherControllerConverter = voucherControllerConverter;
    }

    @GetMapping
    public ApiResponse<List<VoucherServiceResponse>> getVouchers(
            @ModelAttribute VoucherListRequest voucherRequest) {

        VoucherServiceListRequest voucherServiceListRequest = voucherControllerConverter.ofVoucherServiceListRequest(
                voucherRequest);

        return new ApiResponse<>(voucherService.getAllVoucherList(voucherServiceListRequest),
                SuccessCode.SELECT_SUCCESS);
    }

    @DeleteMapping("/{voucherId}")
    public ApiResponse<VoucherDeleteResponse> deleteVoucher(
            @PathVariable("voucherId") String voucherId) {

        String deletedVoucherId = voucherService.deleteByVoucherId(voucherId);

        return new ApiResponse<>(new VoucherDeleteResponse(deletedVoucherId),SuccessCode.DELETE_SUCCESS);
    }

    @GetMapping("/{voucherId}")
    public ApiResponse<VoucherServiceResponse> detailVoucher(
            @PathVariable("voucherId") String voucherId) {

        return new ApiResponse<>(voucherService.detailVoucher(voucherId),SuccessCode.SELECT_SUCCESS);
    }

    @PostMapping
    public ResponseEntity<VoucherServiceResponse> createVoucher(
            @RequestBody VoucherCreateRequest voucherCreateRequest) {
        String id = generator.makeKey();
        LocalDateTime createdAt = generator.makeDate();

        VoucherServiceCreateRequest createRequest = voucherControllerConverter.ofVoucherServiceCreateRequest(
                voucherCreateRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location)
                .body(voucherService.createVoucher(id, createRequest, createdAt));
    }

}
