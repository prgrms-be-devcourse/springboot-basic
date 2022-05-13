package org.prgrms.springbootbasic.controller.api;

import static org.prgrms.springbootbasic.util.DtoConverter.toVoucherDTO;
import static org.prgrms.springbootbasic.util.DtoConverter.toVoucherDTOs;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.dto.CreateVoucherRequest;
import org.prgrms.springbootbasic.dto.SearchCondition;
import org.prgrms.springbootbasic.dto.VoucherDTO;
import org.prgrms.springbootbasic.dto.VoucherListResponse;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
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
    public ResponseEntity<VoucherListResponse> findAllVouchers(SearchCondition searchCondition) {
        List<Voucher> vouchers;
        if (searchCondition.getVoucherType() != null) {
            vouchers = voucherService.findVoucherUsingType(searchCondition.getVoucherType());
        } else if ((searchCondition.getStart() != null && searchCondition.getEnd() != null)) {
            vouchers = voucherService.findVoucherUsingCreatedAt(
                LocalDate.parse(searchCondition.getStart(), formatter).atStartOfDay(),
                LocalDate.parse(searchCondition.getEnd(), formatter).atStartOfDay());
        } else {
            vouchers = voucherService.findAll();
        }

        return ResponseEntity.ok(new VoucherListResponse(toVoucherDTOs(vouchers)));
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
