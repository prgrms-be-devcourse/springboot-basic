package org.prgrms.kdt.api;

import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.exception.BadRequestException;
import org.prgrms.kdt.exception.ErrorResponse;
import org.prgrms.kdt.form.VoucherForm;
import org.prgrms.kdt.voucher.VoucherDto;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.wallet.WalletDto;
import org.prgrms.kdt.wallet.WalletService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:39 오후
 */

@RestController
@RequestMapping(value = Apis.PRE_FIX, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class Apis {

    protected static final String PRE_FIX = "/kdt/api/v1";
    protected static final String WALLET = "/customers/wallet";
    protected static final String CUSTOMER = "/customers/{customerId}";
    protected static final String VOUCHER = "/vouchers/{voucherId}";
    protected static final String VOUCHERS = "/vouchers";
    protected static final String SEARCH = "/search";
    protected static final String VOUCHER_TYPE = "/type/{voucherType}";

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final WalletService walletService;

    public Apis(CustomerService customerService, VoucherService voucherService, WalletService walletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    @GetMapping(CUSTOMER)
    public ResponseEntity getCustomer(@PathVariable String customerId) {
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        customerDto.setVoucherDtos(voucherService.getVouchers(customerId));
        return ResponseEntity.ok().body(customerDto);
    }

    @GetMapping(VOUCHER)
    public ResponseEntity getVoucher(@PathVariable String voucherId) {
        VoucherDto voucherDto = voucherService.getVoucherById(voucherId);
        voucherDto.setCustomerDtos(customerService.getCustomers(voucherId));
        return ResponseEntity.ok().body(voucherDto);
    }

    @GetMapping(VOUCHERS)
    public ResponseEntity getAllVouchers() {
        return ResponseEntity.ok().body(voucherService.getAllVouchers());
    }

    @PostMapping(VOUCHERS)
    public ResponseEntity addVoucher(@RequestBody @Valid VoucherDto voucherDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getFieldError().getDefaultMessage());
        }
        voucherService.addVoucher(voucherDto);
        /**todo 저장된 바우처로 리턴도되록 변경*/
        return ResponseEntity.ok().body(voucherDto);
    }

    @DeleteMapping(VOUCHERS)
    public ResponseEntity deleteVoucher(@RequestBody VoucherDto voucherDto) {
        VoucherDto removeVoucher = voucherService.getVoucherById(voucherDto.getVoucherId());
        voucherService.removeVoucher(removeVoucher.getVoucherId());
        return ResponseEntity.ok().body(removeVoucher);
    }

    @PostMapping(WALLET)
    public ResponseEntity insertWallet(@RequestBody WalletDto walletDto) {
        validateWalletDtoExistedId(walletDto);
        walletService.addWallet(walletDto);
        URI uri = URI.create(PRE_FIX + WALLET);
        /**todo 저장된 지갑으로 리턴도되록 변경*/
        return ResponseEntity.created(uri).body(walletDto);
    }

    @DeleteMapping(WALLET)
    public ResponseEntity deleteWallet(@RequestBody WalletDto walletDto) {
        validateWalletDtoExistedId(walletDto);
        walletService.removeWallet(walletDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(VOUCHERS + SEARCH + VOUCHER_TYPE)
    public ResponseEntity getByVoucherType(@PathVariable String voucherType) {
        if (!VoucherType.isValue(voucherType)) {
            throw  new BadRequestException("not exist : " + voucherType);
        }
        return ResponseEntity.ok().body(voucherService.getVoucherByVoucherType(voucherType));
    }

    private void validateWalletDtoExistedId(@RequestBody WalletDto walletDto) {
        customerService.getCustomerById(walletDto.getCustomerId());
        voucherService.getVoucherById(walletDto.getVoucherId());
    }

}
