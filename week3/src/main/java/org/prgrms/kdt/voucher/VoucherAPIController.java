package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.model.CreateVoucherRequest;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.service.IVoucherJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class VoucherAPIController {
    private final static Logger logger = LoggerFactory.getLogger(VoucherAPIController.class);

    private final IVoucherJdbcService voucherService;

    public VoucherAPIController(IVoucherJdbcService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/api/v1/vouchers")
    public List<Voucher> findVouchers() {
        return voucherService.list();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> findVoucher(@PathVariable("voucherId") UUID voucherId){
        var voucher = voucherService.getVoucher(voucherId);
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/vouchers")
    @ResponseBody
    public ResponseEntity<List<Voucher>> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest){
        voucherService.create(createVoucherRequest.voucherType(), createVoucherRequest.amount());
        var vouchers = voucherService.list();

        //과제 확인 용도로 body 에 전체 바우처리스트를 담아서 확인
        return ResponseEntity.status(HttpStatus.CREATED).body(vouchers);
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<List<Voucher>> deleteByVoucherId(@PathVariable UUID voucherId){
        voucherService.deleteById(voucherId);
        var vouchers = voucherService.list();

        return ResponseEntity.status(HttpStatus.CREATED).body(vouchers);
    }


    @GetMapping("/api/v1/vouchers/voucher-type")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVouchersByVoucherType(@RequestParam("voucherType") VoucherType voucherType){
        logger.info("voucherType: {}", voucherType);
        var voucher = voucherService.findByVoucherType(voucherType);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(voucher);
    }

    @GetMapping("/api/v1/vouchers/created-at")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVouchersByTerm(@RequestParam("start") String start, @RequestParam("end") String end){
        var voucher = voucherService.findByVouchersTerm(start, end);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(voucher);
    }

}
