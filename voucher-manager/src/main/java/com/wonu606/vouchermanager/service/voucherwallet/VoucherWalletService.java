package com.wonu606.vouchermanager.service.voucherwallet;

import com.wonu606.vouchermanager.repository.voucherwallet.VoucherWalletRepository;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;

    private final OwnedVoucherQueryConverter ownedVoucherQueryConverter;
    private final OwnedVoucherResultConverter ownedVoucherResultConverter;
    private final WalletAssignQueryConverter walletSaveQueryConverter;
    private final WalletDeleteByCustomerQueryConverter walletDeleteQueryConverter;
    private final WalletAssignQueryConverter walletAssignQueryConverter;
    private final WalletSaveResultConverter walletSaveResultConverter;
    private final OwnedCustomersQueryConverter ownedCustomersQueryConverter;
    private final OwnedCustomersResultConverter ownedCustomersResultConverter;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public OwnedVouchersResult findOwnedVouchersByCustomer(OwnedVoucherParam param) {
        OwnedVoucherQuery query = ownedVoucherQueryConverter.convert(param);
        OwnedVoucherResultSet resultSet =
                voucherWalletRepository.findOwnedVouchersByCustomer(query);

        return ownedVoucherResultConverter.convert(resultSet);
    }

    public void deleteWallet(WalletDeleteParam param) {
        WalletDeleteQuery query = walletDeleteQueryConverter.convert(param);
        voucherWalletRepository.delete(query);
    }

    public WalletSaveResultSet assignWallet(WalletAssignParam param) {
        WalletSaveQuery query = walletSaveQueryConverter.convert(param);

        WalletSaveResultSet resultSet = voucherWalletRepository.save(query);
        return walletSaveResultConverter.convert(resultSet);
    }

    public OwnedCustomersResult findOwnedCustomersByVoucher(OwnedCustomersParam param) {
        OwnedCustomersQuery query = ownedCustomersQueryConverter.convert(param);
        OwnedCustomersResultSet resultSet =
                voucherWalletRepository.findOwnedCustomersByVoucher(query);

        return ownedCustomersResultConverter.convert(resultSet);
    }
}
