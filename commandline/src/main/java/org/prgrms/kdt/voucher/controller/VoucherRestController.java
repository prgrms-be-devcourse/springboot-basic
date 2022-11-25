package org.prgrms.kdt.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/rest/allVoucher")
    public List<VoucherDto> allVoucher() {
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        List<Voucher> vouchers = voucherService.getAllVoucher();

        for (Voucher voucher : vouchers) {
            voucherDtoList.add(voucherToVoucherDto(voucher));
        }

        return voucherDtoList;
    }

    @GetMapping("/rest/{voucherId}")
    public VoucherDto findByIdVoucher(@PathVariable("voucherId") long voucherId) {
        Voucher voucher = voucherService.findById(voucherId);

        log.info("###voucher={}", voucher);

        VoucherDto voucherDto = voucherToVoucherDto(voucher);

        return voucherDto;
    }

    @PostMapping("/rest/insert")
    public String insertVoucer(HttpEntity<InsertVoucherDto> httpEntity) {
        InsertVoucherDto insertVoucherDto = httpEntity.getBody();
        log.info("inserVoucherDto, typeNumber ={}, discountDegree={}", insertVoucherDto.getTypeNumber(), insertVoucherDto.getDiscountDegree());
        voucherService.createVoucher(insertVoucherDto.getTypeNumber(), insertVoucherDto.getDiscountDegree());
        return "ok";
    }


    @PostMapping("/rest/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") long voucherId) {
        voucherService.deleteById(voucherId);
        return "ok";
    }

    @GetMapping("/rest/typeName/{typeNumber}")
    public List<VoucherDto> findByTypeName(@PathVariable("typeNumber") String typeNumber) {
        try {
            log.info("typeNumber={}", typeNumber);
            List<VoucherDto> voucherDtoList = new ArrayList<>();
            for (Voucher voucher : voucherService.findByTypeName(typeNumber)) {
                log.info("voucher=>{}", voucher);
                voucherDtoList.add(voucherToVoucherDto(voucher));
            }
            return voucherDtoList;
        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
            return null;
        }
    }

    private VoucherDto voucherToVoucherDto(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getTypeName(), voucher.getDiscountDegree());
    }

}
