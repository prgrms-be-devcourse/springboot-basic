package org.promgrammers.springbootbasic.domain.wallet.service;

import org.promgrammers.springbootbasic.domain.wallet.dto.request.CreateWalletRequest;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletListResponse;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletResponse;
import org.promgrammers.springbootbasic.domain.wallet.model.Wallet;
import org.promgrammers.springbootbasic.domain.wallet.repository.impl.JdbcWalletRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.promgrammers.springbootbasic.exception.ErrorCode.NOT_FOUNT_WALLET;

@Service
public class WalletService {

    private final JdbcWalletRepository walletRepository;

    public WalletService(JdbcWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletResponse create(CreateWalletRequest walletRequest) {
        Wallet wallet = new Wallet(walletRequest.voucher(), walletRequest.customer());
        walletRepository.save(wallet);
        return new WalletResponse(wallet.getWalletId(), wallet.getVoucher(), wallet.getCustomer());
    }

    @Transactional(readOnly = true)
    public WalletListResponse findAll() {
        List<Wallet> walletList = walletRepository.findAll();

        validateList(walletList);

        List<WalletResponse> walletResponseList = walletList
                .stream()
                .map(wallet -> new WalletResponse(wallet.getWalletId(), wallet.getVoucher(), wallet.getCustomer()))
                .toList();

        return new WalletListResponse(walletResponseList);
    }

    @Transactional(readOnly = true)
    public WalletResponse findById(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new BusinessException(NOT_FOUNT_WALLET));
        return new WalletResponse(walletId, wallet.getVoucher(), wallet.getCustomer());
    }

    @Transactional(readOnly = true)
    public WalletListResponse findAllWalletByCustomerId(UUID customerId) {
        List<Wallet> walletList = walletRepository.findAllWalletByCustomerId(customerId);

        validateList(walletList);

        List<WalletResponse> walletResponseList = walletList.stream()
                .map(wallet -> new WalletResponse(wallet.getWalletId(), wallet.getVoucher(), wallet.getCustomer()))
                .toList();

        return new WalletListResponse(walletResponseList);
    }

    @Transactional(readOnly = true)
    public WalletListResponse findAllWalletByVoucherId(UUID voucherId) {
        List<Wallet> walletList = walletRepository.findAllWalletByVoucherId(voucherId);

        validateList(walletList);

        List<WalletResponse> walletResponseList = walletList.stream()
                .map(wallet -> new WalletResponse(wallet.getWalletId(), wallet.getVoucher(), wallet.getCustomer()))
                .toList();

        return new WalletListResponse(walletResponseList);
    }

    @Transactional
    public void deleteAll() {
        walletRepository.deleteAll();
    }

    @Transactional
    public void deleteById(UUID id) {
        walletRepository.deleteById(id);
    }

    private static void validateList(List<Wallet> list) {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(NOT_FOUNT_WALLET);
        }
    }
}
