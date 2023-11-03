package org.programmers.springorder.controller.voucher;

import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> findAllVouchers() {
        List<VoucherResponseDto> vouchers = voucherService.getAllVoucher();
        return ResponseEntity.ok()
                .body(vouchers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDto> findVoucher(@PathVariable UUID id) {
        Voucher voucher = voucherService.findById(id);
        VoucherResponseDto response = VoucherResponseDto.of(voucher);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/type")
    public ResponseEntity<List<VoucherResponseDto>> findVoucherByType(@RequestParam("type") VoucherType voucherType) {
        List<VoucherResponseDto> vouchers = voucherService.findByType(voucherType);
        return ResponseEntity.ok()
                .body(vouchers);
    }

    @PostMapping
    public ResponseEntity<VoucherResponseDto> addVoucher(@RequestBody VoucherRequestDto request) {
        Voucher savedVoucher = voucherService.createVoucher(request);
        VoucherResponseDto response = VoucherResponseDto.of(savedVoucher);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
