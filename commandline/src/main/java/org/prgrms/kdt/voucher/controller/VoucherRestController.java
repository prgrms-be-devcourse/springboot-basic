package org.prgrms.kdt.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.exception.voucher.VoucherUserException;
import org.prgrms.kdt.exception.ErrorResult;
import org.prgrms.kdt.exception.voucher.VoucherServerException;
import org.prgrms.kdt.util.ControllerResult;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dto.InsertVoucherDto;
import org.prgrms.kdt.voucher.dto.VoucherDto;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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

        VoucherDto voucherDto = voucherToVoucherDto(voucher);

        return voucherDto;
    }

    @PostMapping("/rest/insert")
    public String insertVoucher(HttpEntity<InsertVoucherDto> httpEntity) {
        InsertVoucherDto insertVoucherDto = httpEntity.getBody();
        voucherService.createVoucher(insertVoucherDto.getTypeNumber(), insertVoucherDto.getDiscountDegree());
        return ControllerResult.OK;
    }


    @PostMapping("/rest/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") long voucherId) {
        voucherService.deleteById(voucherId);
        return ControllerResult.OK;
    }

    @GetMapping("/rest/typeName/{typeNumber}")
    public List<VoucherDto> findByTypeName(@PathVariable("typeNumber") String typeNumber) {
        List<VoucherDto> voucherDtoList = new ArrayList<>();
        for (Voucher voucher : voucherService.findByTypeName(typeNumber)) {
            voucherDtoList.add(voucherToVoucherDto(voucher));
        }
        return voucherDtoList;
    }

    private VoucherDto voucherToVoucherDto(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getTypeName(), voucher.getDiscountDegree());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler()
    public ErrorResult userHandler(VoucherUserException userException) {
        log.error("[customerExceptionHandler] => {}", userException);

        return new ErrorResult(ControllerResult.USER_ERROR, userException.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult serverHandler(VoucherServerException serverException) {
        log.error("[customerExceptionHandler] => {}", serverException);

        return new ErrorResult(ControllerResult.SERVER_ERROR, serverException.getMessage());
    }
}
