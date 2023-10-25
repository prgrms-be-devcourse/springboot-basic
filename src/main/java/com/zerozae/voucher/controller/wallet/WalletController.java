package com.zerozae.voucher.controller.wallet;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.wallet.WalletRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import com.zerozae.voucher.service.voucher.VoucherService;
import com.zerozae.voucher.service.wallet.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletController(WalletService walletService, VoucherService voucherService, CustomerService customerService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public Response createWallet(WalletRequest walletRequest) {
        try {
            walletService.createWallet(walletRequest);
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response findWalletByCustomerId(UUID customerId) {
        try {
            customerService.findById(customerId);
            List<WalletResponse> wallets = walletService.findWalletByCustomerId(customerId);
            List<CustomerResponse> customers = wallets.stream()
                    .map(wallet -> customerService.findById(wallet.getCustomerId()))
                    .toList();

            return Response.success(customers);
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response findWalletByVoucherId(UUID voucherId) {
        try {
            voucherService.findById(voucherId);
            List<WalletResponse> wallets = walletService.findWalletByVoucherId(voucherId);
            List<VoucherResponse> vouchers = wallets.stream()
                    .map(wallet -> voucherService.findById(wallet.getVoucherId()))
                    .toList();

            return Response.success(vouchers);
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response deleteWalletsById(UUID customerId, UUID voucherId){
        try {
            customerService.findById(customerId);
            voucherService.findById(voucherId);
            walletService.deleteWalletFromCustomer(customerId,voucherId);
            return Response.success();
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response deleteAllWallets(){
        walletService.deleteAllWallets();
        return Response.success();
    }
}
