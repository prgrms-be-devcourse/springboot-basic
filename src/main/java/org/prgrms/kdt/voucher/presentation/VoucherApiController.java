package org.prgrms.kdt.voucher.presentation;

import org.prgrms.kdt.common.dto.ResponseDto;
import org.prgrms.kdt.common.dto.ResponseMessage;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;
import org.prgrms.kdt.voucher.dto.VoucherDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public ResponseEntity<ResponseDto> vouchers() {
        List<Voucher> vouchers = voucherService.findAll();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, vouchers));
    }

    @PostMapping("/vouchers/createdDate")
    public ResponseEntity<ResponseDto> createdDateVouchers(@RequestBody LocalDateTime date) {
        List<Voucher> vouchers = voucherService.findByCreatedDate(date);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, vouchers));
    }

    @PostMapping("/vouchers/type")
    public ResponseEntity<ResponseDto> typeVouchers(@RequestBody Type type) {
        List<Voucher> vouchers = voucherService.findByType(type);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, vouchers));
    }

    @PostMapping("/voucher")
    public ResponseEntity<ResponseDto> createVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherService.createVoucher(voucherDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, voucher));
    }

    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<ResponseDto> deleteVoucher(@PathVariable("id") UUID id) {
        voucherService.deleteById(id);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS));
    }

    @GetMapping("/voucher/{id}")
    public ResponseEntity<ResponseDto> voucher(@PathVariable("id") UUID id) {
        Voucher voucher = voucherService.findById(id);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS, voucher));
    }

}
