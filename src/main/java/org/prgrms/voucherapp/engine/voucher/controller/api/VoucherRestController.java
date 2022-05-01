package org.prgrms.voucherapp.engine.voucher.controller.api;

import org.apache.coyote.Response;
import org.prgrms.voucherapp.Navigator;
import org.prgrms.voucherapp.engine.voucher.dto.VoucherCreateDto;
import org.prgrms.voucherapp.engine.voucher.dto.VoucherDto;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.global.ResponseFormat;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<ResponseFormat> getVouchers(@RequestParam(value = "type") Optional<String> voucherType,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> after,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> before){

        logger.info("parameter : {}, {}, {}",voucherType, after, before);
        Optional<VoucherType> paramVoucherType = voucherType.isPresent() ? VoucherType.getType(voucherType.get()) : Optional.empty();
        List<Voucher> voucherList = voucherService.getVouchersByFilter(paramVoucherType, after, before);
        List<VoucherDto> voucherDtos = voucherList.stream().map(VoucherDto::of).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseFormat(true, HttpStatus.OK.value(), "바우처 조회 성공", voucherDtos));
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<ResponseFormat> getVoucher(@PathVariable UUID voucherId){
        VoucherDto resDto = VoucherDto.of(voucherService.getVoucher(voucherId));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseFormat(true, HttpStatus.OK.value(), "바우처 조회 성공", resDto));
    }

    @PostMapping
    public ResponseEntity<ResponseFormat> createVoucher(@RequestBody VoucherCreateDto createDto){
        Voucher voucher = voucherService.createVoucher(createDto.type(), createDto.voucherId(), createDto.amount());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseFormat(true, HttpStatus.OK.value(), "바우처 생성 성공", VoucherDto.of(voucher)));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<ResponseFormat> deleteVoucher(@PathVariable UUID voucherId){
        voucherService.removeVoucher(voucherId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseFormat(true, HttpStatus.OK.value(), "바우처 삭제 성공 : "+voucherId, null));
    }

}
