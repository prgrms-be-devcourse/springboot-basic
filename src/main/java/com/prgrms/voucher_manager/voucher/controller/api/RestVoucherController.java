package com.prgrms.voucher_manager.voucher.controller.api;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerController;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.infra.facade.VoucherServiceFacade;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/api/vouchers")
public class RestVoucherController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final VoucherServiceFacade voucherServiceFacade;
    private final VoucherService voucherService;

    public RestVoucherController(VoucherServiceFacade voucherServiceFacade, VoucherService voucherService) {
        this.voucherServiceFacade = voucherServiceFacade;
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public ResponseEntity vouchers() {
        List<VoucherDto> vouchers = voucherService.getFindAllVoucher();
        return ResponseEntity.ok()
                .body(vouchers);
    }


    @GetMapping("/{voucherId}")
    public ResponseEntity voucherById(@PathVariable UUID voucherId) {
        VoucherDto voucherDto = voucherService.findById(voucherId);
        return ResponseEntity.ok()
                .body(voucherDto);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity voucherByType(@PathVariable String type) {
        List<VoucherDto> voucherDtos = voucherService.findByType(type);
        return ResponseEntity.ok()
                .body(voucherDtos);
    }

    @GetMapping("/{type}/customer")
    public ResponseEntity customerByVoucherType(@PathVariable String type) {
        List<CustomerDto> customers = voucherServiceFacade.findCustomerByVoucherType(type);
        return ResponseEntity.ok()
                .body(customers);
    }

    @GetMapping("/date")
    public ResponseEntity voucherByDate(@RequestParam String start,
                                        @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<VoucherDto> voucherDtos = voucherService.findByDate(startDate, endDate);
        return ResponseEntity.ok()
                .body(voucherDtos);
    }


    @GetMapping("/date/type")
    public ResponseEntity customerByVoucherType(@RequestParam String start,
                                                @RequestParam String end,
                                                @RequestParam String type) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<VoucherDto> voucherDtos = voucherService.findByDateAndType(startDate, endDate, type);
        return ResponseEntity.ok()
                .body(voucherDtos);
    }


    @PostMapping("")
    public ResponseEntity addVoucher(@RequestBody VoucherDto voucherDto) {
        logger.info("save Voucher {}", voucherDto);
        voucherService.createVoucher(voucherDto.getType(), voucherDto.getValue());
        return ResponseEntity.created(URI.create("/api/vouchers/add")).build();
    }


    @DeleteMapping("/{voucherId}")
    public ResponseEntity deleteVoucher(@PathVariable UUID voucherId) {
      voucherService.deleteVoucher(voucherId);
        return ResponseEntity.noContent().build();
    }




}
