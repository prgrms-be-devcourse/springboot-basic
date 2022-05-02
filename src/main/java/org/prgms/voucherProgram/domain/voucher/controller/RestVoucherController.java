package org.prgms.voucherProgram.domain.voucher.controller;

import static java.util.stream.Collectors.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.prgms.voucherProgram.domain.voucher.dto.SimpleResponse;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherDto;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherFindRequest;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.domain.voucher.service.VoucherService;
import org.prgms.voucherProgram.global.error.ExceptionControllerAdvice;
import org.prgms.voucherProgram.global.error.ExceptionResponse;
import org.prgms.voucherProgram.global.error.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vouchers")
public class RestVoucherController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDto>> getVouchers(@RequestParam Map<String, String> params) {
        List<VoucherDto> vouchers = findVouchers(params).stream()
            .map(VoucherDto::from)
            .collect(toList());
        return ResponseEntity.ok(vouchers);
    }

    private List<Voucher> findVouchers(Map<String, String> params) {
        if (params.isEmpty()) {
            return voucherService.findAllVoucher();
        }

        return voucherService.findVouchers(VoucherFindRequest.of(params));
    }

    @PostMapping
    public ResponseEntity<VoucherDto> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherService.create(voucherRequest);
        return ResponseEntity.ok(VoucherDto.from(voucher));
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDto> getVoucher(@PathVariable("voucherId") UUID voucherId) {
        Voucher voucher = voucherService.findVoucher(voucherId);
        return ResponseEntity.ok(VoucherDto.from(voucher));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<SimpleResponse> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return ResponseEntity.ok(new SimpleResponse("Success"));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), new Date(),
            e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleCustomException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), new Date(),
            e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        logger.error(e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), new Date(),
            e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
