package org.prgrms.kdt.shop.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.dto.VoucherCreateRequestDto;
import org.prgrms.kdt.shop.dto.VoucherGetByTypeServiceDto;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class RestXmlVoucherController {

    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(RestJsonVoucherController.class);

    @GetMapping
    public ResponseEntity<List<Voucher>> vouchers() {
        return new ResponseEntity<>(voucherService.findAllVoucher(), HttpStatus.OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID voucherId) {
        return new ResponseEntity<>(voucherService.findVoucherById(voucherId), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Voucher>> getVoucherByType(@RequestParam String voucherType) {
        return new ResponseEntity<>(
            voucherService.findVoucherByType(new VoucherGetByTypeServiceDto(voucherType)),
            HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Voucher> addVoucher(
        @RequestBody VoucherCreateRequestDto voucherCreateRequestDto) {
        return new ResponseEntity<>(
            voucherService.addVoucher(voucherCreateRequestDto.toServiceDto()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{voucherId}")
    public HttpStatus delete(@PathVariable UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping
    public HttpStatus deleteAll(RedirectAttributes redirectAttributes) {
        voucherService.deleteAllVoucher();
        return HttpStatus.NO_CONTENT;
    }
}
