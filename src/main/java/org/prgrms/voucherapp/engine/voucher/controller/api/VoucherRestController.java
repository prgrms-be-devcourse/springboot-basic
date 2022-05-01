package org.prgrms.voucherapp.engine.voucher.controller.api;

import org.prgrms.voucherapp.Navigator;
import org.prgrms.voucherapp.engine.voucher.dto.VoucherDto;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // TODO : ModelAttribute 사용하여 리팩토링
    @GetMapping
    public ResponseEntity<List<VoucherDto>> getVouchers(@RequestParam(value = "type") Optional<String> voucherType,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> after,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> before){

        logger.info("parameter : {}, {}, {}",voucherType, after, before);
        Optional<VoucherType> paramVoucherType = voucherType.isPresent() ? VoucherType.getType(voucherType.get()) : Optional.empty();
        List<Voucher> voucherList = voucherService.getVouchersByFilter(paramVoucherType, after, before);
        return ResponseEntity.status(HttpStatus.OK).body(voucherList.stream().map(VoucherDto::of).toList());
    }

}
