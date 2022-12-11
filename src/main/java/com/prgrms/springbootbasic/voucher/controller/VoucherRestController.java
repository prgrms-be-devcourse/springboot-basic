package com.prgrms.springbootbasic.voucher.controller;

import com.prgrms.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.prgrms.springbootbasic.voucher.controller.VoucherResponse.*;

@Profile({"prod", "test"})
@RestController
@RequestMapping("/api/v2/voucher")
public class VoucherRestController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherShortcut>> list(){
        List<VoucherShortcut> vouchers = voucherService.list();
        logger.info("List up all Vouchers. voucher shortcuts {}", vouchers);
        return ResponseEntity.ok(vouchers);
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody VoucherForm voucherForm, Model model){
        voucherService.create(voucherForm);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v2/voucher"));
        logger.info("New Voucher created. input info : {}", voucherForm);
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<VoucherDetails> detail(@PathVariable String uuid){
        VoucherDetails details = voucherService.findById(UUID.fromString(uuid));
        logger.info("Voucher Detail. voucher id is {}", uuid);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        voucherService.delete(uuid);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v2/voucher"));
        logger.info("Delete voucher. voucher id was {}", uuid);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
