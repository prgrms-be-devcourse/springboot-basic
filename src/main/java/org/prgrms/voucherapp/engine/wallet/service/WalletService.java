package org.prgrms.voucherapp.engine.wallet.service;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.engine.wallet.dto.CustomerVoucherDto;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.engine.wallet.vo.Wallet;
import org.prgrms.voucherapp.exception.NullVoucherException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.UUID;

import static org.slf4j.LoggerFactory.*;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final static Logger logger = getLogger(WalletService.class);

    public WalletService(WalletRepository walletRepository, VoucherService voucherService, CustomerService customerService) {
        this.walletRepository = walletRepository;
    }

    public CustomerVoucherDto getWallet(UUID walletId){
        return walletRepository
                .findById(walletId)
                .orElseThrow(() -> new NullVoucherException(MessageFormat.format("{0}는 존재하지 않는 바우처 id입니다.", walletId)));
    }

    @Transactional
    public Wallet assignVoucherToCustomer(UUID walletId, UUID customerId, UUID voucherId){
        while (walletRepository.findById(walletId).isPresent()) {
            walletId = UUID.randomUUID();
        }
        return walletRepository.insert( new Wallet(walletId, customerId, voucherId));
    }

    public String getVouchersOfCustomerByStr(Customer customer){
        StringBuilder sb = new StringBuilder();
        sb.append("--- (id : %s)%s님이 보유한 바우처 ---\n".formatted(customer.getCustomerId(), customer.getName()));
        for (Voucher voucher : walletRepository.findVouchersByCustomerId(customer.getCustomerId())) {
            sb.append(voucher.toString()).append("\n");
        }
        if (sb.isEmpty()) return "아무 바우처도 보유하고 있지 않습니다.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    @Transactional
    public void removeByWalletId(UUID walletId){
        CustomerVoucherDto oldWallet = this.getWallet(walletId);
        walletRepository.deleteByWalletId(walletId);
        logger.info("--- 삭제된 지갑 정보 --- \n%s".formatted(oldWallet));
    }

    public String getCustomersOfVoucherByStr(Voucher voucher){
        StringBuilder sb = new StringBuilder();
        sb.append("--- %s 바우처가 보유한 고객들 ---\n".formatted(voucher.toString()));
        for (Customer customer : walletRepository.findCustomersByVoucherId(voucher.getVoucherId())) {
            sb.append(customer.toString()).append("\n");
        }
        if (sb.isEmpty()) return "아무 고객도 보유하고 있지 않습니다.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    public String getWalletListByStr(){
        StringBuilder sb = new StringBuilder();
        for (CustomerVoucherDto wallet : walletRepository.findAll()) {
            sb.append(wallet.toString()).append("\n");
        }
        if (sb.isEmpty()) return "Wallet Repository is empty.";
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

}
