package org.prgrms.kdt.shop.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.shop.enums.VoucherType.find;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_XML_VALUE)
@RequiredArgsConstructor
public class RestXmlVoucherController {
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @GetMapping
    public ResponseEntity<List<Voucher>> vouchers( ) {
        return new ResponseEntity<List<Voucher>>(voucherService.findAllVoucher(), HttpStatus.OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID voucherId) {
        return new ResponseEntity<Voucher>(voucherService.findVoucherById(voucherId).get(), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Voucher>> getVoucherByType(@RequestParam String voucherType) {
        try {
            if (find(voucherType) == VoucherType.FIXED_AMOUNT || find(voucherType) == VoucherType.PERCENT_DISCOUNT) {
                return new ResponseEntity<List<Voucher>>(voucherService.findVoucherByType(find(voucherType)), HttpStatus.OK);
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            logger.error("Type error", e);
            new ResponseEntity<List<Voucher>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Voucher>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Voucher> addVoucher(@RequestParam int voucherAmount, @RequestParam String voucherType) {
        Voucher voucher;
        try {
            if (find(voucherType) == VoucherType.FIXED_AMOUNT) {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherAmount);
            } else if (find(voucherType) == VoucherType.PERCENT_DISCOUNT) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherAmount);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Tpye Error", e);
            voucher = null;
            return new ResponseEntity<Voucher>(voucher, HttpStatus.BAD_REQUEST);
        }
        voucherService.addVoucher(voucher);
        return new ResponseEntity<Voucher>(voucher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{voucherId}")
    @Transactional
    public HttpStatus delete(@PathVariable UUID voucherId) {
        var voucher = voucherService.findVoucherById(voucherId);
        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping
    @Transactional
    public HttpStatus deleteAll(RedirectAttributes redirectAttributes) {
        voucherService.deleteAllVoucher();
        return HttpStatus.NO_CONTENT;
    }
}
