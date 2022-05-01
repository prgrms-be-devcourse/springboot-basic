package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(value = "", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public List<VoucherResponse> findVouchers(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {

        // Type 조회
        if (checkFindByType(type)) {
            return getVoucherListByType(type);
        }

        // 기간 조회
        if (checkFindByPeriod(from, end)) {
            return getVoucherListByPeriod(from, end);
        }

        // 전체 조회
        return getAllVoucherList();
    }

    @PostMapping(value = "", consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<Object> addVoucher(@RequestBody CreateVoucherRequest customerRequest) {
        UUID voucherId = voucherService.addVoucher(customerRequest.getVoucherType(), customerRequest.getAmount());
        return ResponseEntity.created(URI.create("/api/v1/vouchers/" + voucherId)).build();
    }

    @DeleteMapping(value = "/{voucherId}")
    public ResponseEntity<Object> removeVoucher(@PathVariable UUID voucherId) {
        voucherService.removeVoucher(voucherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{voucherId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponse> findById(@PathVariable UUID voucherId) {
        Optional<Voucher> optionalVoucher = voucherService.findVoucherById(voucherId);

        if (optionalVoucher.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        VoucherResponse voucherResponse = VoucherResponse.from(optionalVoucher.get());
        return new ResponseEntity<>(voucherResponse, HttpStatus.OK);
    }

    private boolean checkFindByPeriod(LocalDate from, LocalDate end) {
        return from != null && end != null;
    }

    private boolean checkFindByType(Integer type) {
        return type != null;
    }

    private List<VoucherResponse> getVoucherListByType(Integer type) {
        List<Voucher> vouchers = voucherService.findVoucherByType(VoucherType.getVoucherType(type));
        return VoucherResponse.fromList(vouchers);
    }

    private List<VoucherResponse> getAllVoucherList() {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        return VoucherResponse.fromList(vouchers);
    }

    private List<VoucherResponse> getVoucherListByPeriod(LocalDate from, LocalDate end) {
        LocalDateTime fromLocalDateTime = LocalDateTime.of(from, LocalTime.of(0, 0));
        LocalDateTime endLocalDateTime = LocalDateTime.of(end, LocalTime.of(23, 59));
        List<Voucher> vouchers = voucherService.findVoucherByPeriod(fromLocalDateTime, endLocalDateTime);
        return VoucherResponse.fromList(vouchers);
    }
}
