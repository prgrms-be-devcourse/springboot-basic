package com.programmers.voucher.controller.api;

import com.programmers.voucher.dto.DateTypeDto;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.dto.VoucherRegisterForm;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/vouchers")
public class ApiVoucherController {
    private final VoucherService voucherService;
    private final Logger log = LoggerFactory.getLogger(ApiVoucherController.class);

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping
    public ResponseEntity<List<VoucherDto>> findAll() {

        List<VoucherDto> voucherDtoList = voucherService.findAll();

        return new ResponseEntity<>(voucherDtoList, OK);
    }

    @PostMapping
    public ResponseEntity<VoucherDto> createVoucher(@RequestBody VoucherRegisterForm registerForm) {

        String type = registerForm.getType();
        String value = registerForm.getValue();

        VoucherDto voucherDto = voucherService.register(type, value);
        return new ResponseEntity<>(voucherDto, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDto> findVoucher(@PathVariable("id") UUID voucherId) {
        VoucherDto voucherDto = voucherService.getVoucher(voucherId);
        return new ResponseEntity<>(voucherDto, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VoucherDto> deleteVoucher(@PathVariable("id") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);

        return new ResponseEntity<>(OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VoucherDto>> findByType(@PathVariable("type") String type) {
        List<VoucherDto> voucherDtoList = voucherService.getTypeVoucher(type);

        return new ResponseEntity<>(voucherDtoList, OK);
    }

    @GetMapping("/time")
    public ResponseEntity<List<VoucherDto>> findByDateTime(@ModelAttribute DateTypeDto dateTypeDto) {
        LocalDateTime from = dateTypeDto.getFrom();
        LocalDateTime to = dateTypeDto.getTo();

        List<VoucherDto> voucherDtoList = voucherService.findVoucherByPeriod(from, to);
        return new ResponseEntity<>(voucherDtoList, OK);
    }
}