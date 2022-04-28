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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<Voucher> vouchers = voucherService.getFindAllVoucher();
        List<VoucherDto> voucherDtos = getVoucherDtoList(vouchers);
        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .body(voucherDtos);
    }


    @GetMapping("/{voucherId}")
    public ResponseEntity voucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .body(entityToDto(voucher));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity voucherByType(@PathVariable String type) {
        List<Voucher> vouchers = voucherService.findByType(type);
        List<VoucherDto> voucherDtos = getVoucherDtoList(vouchers);
        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .body(voucherDtos);
    }

    @GetMapping("/customer/{type}")
    public ResponseEntity customerByVoucherType(@PathVariable String type) {
        List<Customer> customers = voucherServiceFacade.findCustomerByVoucherType(type);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> customerDtos.add(customer.toCustomerDto()));
        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .body(customerDtos);
    }


    @PostMapping("/add")
    public ResponseEntity restAddVoucher(@RequestBody VoucherDto voucherDto) {
        logger.info("save Voucher {}", voucherDto);
        voucherService.createVoucher(voucherDto.getType(), voucherDto.getValue());
        return ResponseEntity.created(URI.create("/api/vouchers/add")).build();
    }


    @DeleteMapping("/{voucherId}")
    public ResponseEntity restDeleteVoucher(@PathVariable UUID voucherId) {
        Voucher deleteVoucher = voucherService.findById(voucherId);
        voucherService.deleteVoucher(deleteVoucher);
        return ResponseEntity.noContent().build();
    }

    private VoucherDto entityToDto(Voucher voucher) {
        return voucher.toVoucherDto();
    }

    private List<VoucherDto> getVoucherDtoList(List<Voucher> vouchers) {
        List<VoucherDto> voucherDtos = new ArrayList<>();
        vouchers.forEach(voucher -> voucherDtos.add(entityToDto(voucher)));
        return voucherDtos;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        return headers;
    }

}
