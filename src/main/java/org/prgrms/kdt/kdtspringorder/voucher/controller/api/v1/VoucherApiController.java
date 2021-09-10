package org.prgrms.kdt.kdtspringorder.voucher.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.kdtspringorder.common.dto.ApiResponse;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.dto.VoucherDto;
import org.prgrms.kdt.kdtspringorder.voucher.repository.VoucherJdbcRepository;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoucherApiController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final VoucherService voucherService;

    @GetMapping("/vouchers")
    public ResponseEntity<ApiResponse> list() {

        logger.debug("Access list()");

        final ApiResponse apiResponse = ApiResponse.builder()
            .payload(this.voucherService.getVouchers())
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<ApiResponse> detailById( @PathVariable("voucherId") UUID voucherId ) {

        logger.debug("Access detailById()");
        logger.info("[Param] voucherId = " + voucherId);

        final ApiResponse apiResponse = ApiResponse.builder()
            .payload(this.voucherService.getVoucher(voucherId))
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/voucher")
    public ResponseEntity<ApiResponse> detailByVoucherType( @RequestParam("voucherType") VoucherType voucherType ) {

        logger.debug("Access detailByVoucherType()");
        logger.info("[Param] voucherType = " + voucherType);

        final ApiResponse apiResponse = ApiResponse.builder()
            .payload(this.voucherService.getVouchersByVoucherType(voucherType))
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/voucher/customer")
    public ResponseEntity<ApiResponse> detailByCustomerId( @RequestParam("customerId") UUID customerId ) {

        logger.debug("Access detailByCustomerId()");
        logger.info("[Param] customerId = " + customerId);

        final ApiResponse apiResponse = ApiResponse.builder()
            .payload(this.voucherService.getVouchersByCustomer(customerId))
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/voucher")
    public ResponseEntity<ApiResponse> create( @Valid @RequestBody VoucherDto.Create voucherDto) {

        logger.debug("Access create()");
        logger.info("[Param] VoucherDto.Join = " + voucherDto.toString());

        this.voucherService.saveVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());

        final ApiResponse apiResponse = ApiResponse.builder()
            .status(HttpStatus.CREATED)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @DeleteMapping("/voucher/{voucherId}")
    public ResponseEntity<ApiResponse> delete( @PathVariable("voucherId") UUID voucherId) {

        logger.debug("Access delete()");
        logger.info("[Param] voucherId = " + voucherId);

        this.voucherService.deleteVoucher(voucherId);

        final ApiResponse apiResponse = ApiResponse.builder()
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

    @PatchMapping("/voucher")
    public ResponseEntity<ApiResponse> allocate( @Valid @RequestBody VoucherDto.Allocate voucherDto) {

        logger.debug("Access allocate()");
        logger.info("[Param] VoucherDto.Allocate = " + voucherDto.toString());

        this.voucherService.allocateVoucherToCustomer(voucherDto.getVoucherId(), voucherDto.getCustomerId());

        final ApiResponse apiResponse = ApiResponse.builder()
            .status(HttpStatus.OK)
            .success(true)
            .build();

        return ResponseEntity.ok(apiResponse);

    }

}
