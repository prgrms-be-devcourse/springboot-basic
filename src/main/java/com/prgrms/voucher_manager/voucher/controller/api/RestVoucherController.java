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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController()
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
    public List<VoucherDto> vouchers() {
        List<Voucher> vouchers = voucherService.getFindAllVoucher();
        return getVoucherDtoList(vouchers);
    }

    @GetMapping("/{voucherId}")
    public VoucherDto voucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return entityToDto(voucher);
    }

    @GetMapping("/type/{type}")
    public List<VoucherDto> voucherByType(@PathVariable String type) {
        List<Voucher> vouchers = voucherService.findByType(type);
        return getVoucherDtoList(vouchers);
    }

    @GetMapping("/customer/{type}")
    public List<CustomerDto> customerByVoucherType(@PathVariable String type) {
        List<Customer> customers = voucherServiceFacade.findCustomerByVoucherType(type);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> customerDtos.add(customer.toCustomerDto()));
        return customerDtos;
    }


    @PostMapping("/add")
    public VoucherDto restAddVoucher(@RequestBody VoucherDto voucherDto) {
        logger.info("save Voucher {}", voucherDto);
        voucherService.createVoucher(voucherDto.getType(), voucherDto.getValue());
        return voucherDto;
    }


    @DeleteMapping("/{voucherId}")
    public Voucher restDeleteVoucher(@PathVariable UUID voucherId) {
        Voucher deleteVoucher = voucherService.findById(voucherId);
        voucherService.deleteVoucher(deleteVoucher);
        return deleteVoucher;
    }

    private static VoucherDto entityToDto(Voucher voucher) {
        return voucher.toVoucherDto();
    }

    private List<VoucherDto> getVoucherDtoList(List<Voucher> vouchers) {
        List<VoucherDto> voucherDtos = new ArrayList<>();
        vouchers.forEach(voucher -> voucherDtos.add(entityToDto(voucher)));
        return voucherDtos;
    }

}
