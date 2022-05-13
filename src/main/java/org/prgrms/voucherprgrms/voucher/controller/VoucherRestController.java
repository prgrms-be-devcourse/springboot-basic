package org.prgrms.voucherprgrms.voucher.controller;

import org.prgrms.voucherprgrms.voucher.VoucherService;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.prgrms.voucherprgrms.voucher.model.VoucherSearchParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherRestController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRestController.class);
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping(value = "/api/v1/vouchers/new")
    @ResponseBody
    public ResponseEntity<String> createVoucher(@ModelAttribute VoucherForm voucherForm) {
        try {
            voucherService.createVoucher(voucherForm);
        } catch (IllegalArgumentException e) {
            logger.error("CREATE VOUCHER ERROR(Illegal Argu)", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DuplicateKeyException e) {
            logger.error("CREATE VOUCHER ERROR(Duplicate Key)", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("생성 성공", HttpStatus.OK);
    }

    //voucher 조회
    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public List<VoucherDTO> voucherList() {
        return voucherService.findAllVoucher().stream().map(VoucherDTO::toVoucherDTO).collect(Collectors.toList());
    }

    @GetMapping(value = "/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public VoucherDTO findVoucher(@PathVariable("voucherId") String voucherId) {
        return VoucherDTO.toVoucherDTO(voucherService.findByVoucherId(voucherId));
    }

    @GetMapping("/api/v1/vouchers/search")
    @ResponseBody
    public List<VoucherDTO> findVoucher(@RequestParam("searchType") String searchType,
                                        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                        @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) {
        VoucherSearchParam searchParam = new VoucherSearchParam(searchType, searchKeyword, startDate, endDate);
        return voucherService.search(searchParam).stream().map(VoucherDTO::toVoucherDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable("voucherId") String voucherId) {
        try {
            voucherService.deleteVoucher(voucherId);
        } catch (IllegalArgumentException e) {
            logger.error("Delete {} failed.", voucherId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new String("삭제 성공".getBytes(), StandardCharsets.UTF_8), HttpStatus.OK);
    }

}
