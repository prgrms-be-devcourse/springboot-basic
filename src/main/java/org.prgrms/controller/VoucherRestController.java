package org.prgrms.controller;

import java.util.List;
import java.util.UUID;
import org.prgrms.dto.VoucherDTO;
import org.prgrms.exception.ErrorCode;
import org.prgrms.exception.NotFoundVoucherException;
import org.prgrms.service.VoucherService;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getVoucherList() {
        return voucherService.findAll();
    }

    @GetMapping("/{id}")
    public Voucher getVoucher(@PathVariable UUID id) {
        return voucherService.findById(id)
            .orElseThrow(() -> new NotFoundVoucherException(ErrorCode.NOT_FOUND_VOUCHER));
    }

    @GetMapping("/period")
    public List<Voucher> getVoucherByCreationTime(@RequestParam("startDate") String startDate,
        @RequestParam("endDate") String endDate) {
        return voucherService.findByPeriod(startDate, endDate);
    }

    @GetMapping("/type")
    public List<Voucher> getVoucherByType(@RequestParam("type") String type) {
        return voucherService.findByType(type);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Voucher createVoucher(@RequestBody VoucherDTO voucherDTO) {
        return voucherService.save(voucherDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoucherById(@PathVariable UUID id) {
        voucherService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(){
        voucherService.deleteAll();
    }

}
