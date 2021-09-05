package org.prgrms.kdt.api;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.voucher.VoucherDto;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.wallet.WalletDto;
import org.prgrms.kdt.wallet.WalletService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:39 오후
 */

@RestController
@RequestMapping(value = Apis.PRE_FIX, produces = MediaType.APPLICATION_JSON_VALUE)
public class Apis {

    protected static final String PRE_FIX = "/kdt/api/v1";
    protected static final String POST_WALLET = "/customer/wallet";

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final WalletService walletService;

    public Apis(CustomerService customerService, VoucherService voucherService, WalletService walletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    /**
     * VoucherWalletDto has customer-id and voucher-id.
     * Returns not-found response if the customer and voucher do not exist.
     */
    @PostMapping(POST_WALLET)
    public ResponseEntity insertWallet(@RequestBody WalletDto walletDto) {
        if (isBadRequest(walletDto)) {
            return ResponseEntity.notFound().build();
        }

        walletService.addVoucherByCustomer(walletDto);
        URI uri = URI.create(PRE_FIX + POST_WALLET);
        return ResponseEntity.created(uri).body(walletDto);
    }

    private boolean isBadRequest(WalletDto walletDto) {
        Optional<CustomerDto> customerDto = customerService.getCustomerById(UUID.fromString(walletDto.getCustomerId()));
        if (customerDto.isEmpty()) {
            return true;
        }

        Optional<VoucherDto> voucherDto = voucherService.getVoucherById(UUID.fromString(walletDto.getVoucherId()));
        if (voucherDto.isEmpty()) {
            return true;
        }

        return false;
    }

}
