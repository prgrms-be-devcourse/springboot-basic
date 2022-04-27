package org.prgrms.springbootbasic.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.prgrms.springbootbasic.dto.VoucherListResponse;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vouchers")
public class ApiVoucherController {

    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findAllVouchers() {
        var vouchers = voucherService.findAll();
        return new VoucherListResponse(DtoConverter.toVoucherDTOs(vouchers));
    }

    @GetMapping("/{voucherType}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findVoucherUsingType(
        @PathVariable("voucherType") VoucherType voucherType) {
        return new VoucherListResponse(
            DtoConverter.toVoucherDTOs(voucherService.findVoucherUsingType(voucherType)));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findVoucherUsingCreatedAt(
        @RequestParam("start") String start, @RequestParam("end") String end) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startTime = LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endTime = LocalDate.parse(end, formatter).atStartOfDay();

        var voucherDTOS = DtoConverter.toVoucherDTOs(
            voucherService.findVoucherUsingCreatedAt(startTime, endTime));

        return new VoucherListResponse(voucherDTOS);
    }
}
