package org.prgrms.voucherapp.engine.wallet.service;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.engine.wallet.vo.Wallet;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.slf4j.LoggerFactory.*;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final static Logger logger = getLogger(WalletService.class);

    public WalletService(WalletRepository walletRepository, VoucherService voucherService, CustomerService customerService) {
        this.walletRepository = walletRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public Wallet assignVoucherToCustomer(UUID walletId, UUID customerId, UUID voucherId){
        while (walletRepository.findById(walletId).isPresent()) {
            walletId = UUID.randomUUID();
        }
        return new Wallet(walletId, customerId, voucherId);
    }

    public String getVouchersofCustomerByStr(UUID customerId){
        StringBuilder sb = new StringBuilder();
        Customer customer = customerService.getCustomer(customerId);
        sb.append("--- (id : %s)%s님이 보유한 바우처 ---\n".formatted(customer.getCustomerId(), customer.getName()));
        for (Voucher voucher : walletRepository.findVouchersByCustomerId(customerId)) {
            sb.append(voucher.toString()).append("\n");
        }
        if (sb.isEmpty()) return "아무 바우처도 보유하고 있지 않습니다.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    public void removeVouchersOfCustomer(UUID customerId){
        walletRepository.deleteByCustomerId(customerService.getCustomer(customerId).getCustomerId());
    }

    public String getCustomersOfVoucherByStr(UUID voucherId){
        StringBuilder sb = new StringBuilder();
        Voucher voucher = voucherService.getVoucher(voucherId);
        sb.append("--- %s 바우처가 보유한 고객들 ---\n".formatted(voucher.toString()));
        for (Customer customer : walletRepository.findCustomersByVoucherId(voucherId)) {
            sb.append(customer.toString()).append("\n");
        }
        if (sb.isEmpty()) return "아무 고객도 보유하고 있지 않습니다.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

}
