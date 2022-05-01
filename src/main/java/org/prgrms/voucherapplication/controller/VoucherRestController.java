package org.prgrms.voucherapplication.controller;

import org.prgrms.voucherapplication.dto.CreateVoucherRequest;
import org.prgrms.voucherapplication.dto.VouchersResponse;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.service.JdbcVoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class VoucherRestController {

    private final JdbcVoucherService voucherService;

    public VoucherRestController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 파라미터 없으면 전체 조회
     * 파라미터 있으면 조건별 조회
     * @param startDateTime
     * @param endDateTime
     * @param voucherType
     * @return
     */
    @GetMapping("/api/v1/vouchers")
    public ResponseEntity findVouchers(@RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDateTime,
                                       @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDateTime,
                                       @RequestParam("voucherType") Optional<String> voucherType) {

        List<SqlVoucher> vouchers = voucherService.getVouchersByCondition(startDateTime, endDateTime, voucherType);
        List<VouchersResponse> responses = vouchers.stream().map(VouchersResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * voucher 생성
     * @param request
     * @return
     */
    @PostMapping("/api/v1/voucher")
    public ResponseEntity createVoucher(@RequestBody CreateVoucherRequest request) {
        voucherService.saveVoucher(request.voucherType(), request.discountAmount());
        return new ResponseEntity<>("voucher was created successfully.", HttpStatus.CREATED);
    }

    /**
     * voucher 삭제
     * @param voucherId
     * @return
     */
    @DeleteMapping("/api/v1/voucher")
    public ResponseEntity deleteVoucher(@RequestParam("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * voucher Id로 조회
     * @param voucherId
     * @return
     */
    @GetMapping("/api/v1/voucher")
    public ResponseEntity getVoucher(@RequestParam("voucherId") UUID voucherId) {
        Optional<SqlVoucher> maybeVoucher = voucherService.getVoucherById(voucherId);
        if (maybeVoucher.isPresent()) {
            SqlVoucher voucher = maybeVoucher.get();
            VouchersResponse vouchersResponse = new VouchersResponse(voucher);
            return new ResponseEntity<>(vouchersResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("There are no result.", HttpStatus.OK);
    }

}
