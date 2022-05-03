package org.prgrms.springbootbasic.controller.api;

import static org.prgrms.springbootbasic.util.DtoConverter.toVoucherDTO;
import static org.prgrms.springbootbasic.util.DtoConverter.toVoucherDTOs;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.dto.CreateVoucherRequest;
import org.prgrms.springbootbasic.dto.VoucherDTO;
import org.prgrms.springbootbasic.dto.VoucherListResponse;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vouchers")
public class ApiVoucherController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findAllVouchers() {
        var vouchers = voucherService.findAll();
        return new VoucherListResponse(toVoucherDTOs(vouchers));
    }

    @GetMapping("/search/{voucherType}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findVoucherUsingType(
        @PathVariable("voucherType") VoucherType voucherType) {
        return new VoucherListResponse(
            toVoucherDTOs(voucherService.findVoucherUsingType(voucherType)));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public VoucherListResponse findVoucherUsingCreatedAt(
        @RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDateTime startTime = LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endTime = LocalDate.parse(end, formatter).atStartOfDay();

        return new VoucherListResponse(
            toVoucherDTOs(voucherService.findVoucherUsingCreatedAt(startTime, endTime)));
    }

    @PostMapping
    public ResponseEntity<Void> createVoucher(
        @RequestBody CreateVoucherRequest createVoucherRequest) throws URISyntaxException {
        voucherService.createVoucher(
            createVoucherRequest.getVoucherType(),
            createVoucherRequest.getAmount() == null ? 0 : createVoucherRequest.getAmount(),
            createVoucherRequest.getPercent() == null ? 0 : createVoucherRequest.getPercent());
        return ResponseEntity.created(new URI("/api/v1/vouchers"))
            .build();
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.ok()
            .header(HttpHeaders.LOCATION, "/api/v1/vouchers")
            .build();
    }

    @GetMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherDTO findVoucherUsingId(@PathVariable("voucherId") UUID voucherId) {
        return toVoucherDTO(voucherService.findVoucher(voucherId));
    }
}
