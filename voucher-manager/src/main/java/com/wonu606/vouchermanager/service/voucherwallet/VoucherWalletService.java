package com.wonu606.vouchermanager.service.voucherwallet;

import com.wonu606.vouchermanager.repository.voucherwallet.VoucherWalletRepository;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.converter.OwnedCustomersQueryConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.OwnedCustomersResultConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.OwnedVoucherQueryConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.OwnedVoucherResultConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.WalletDeleteQueryConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.WalletInsertQueryConverter;
import com.wonu606.vouchermanager.service.voucherwallet.converter.WalletInsertResultConverter;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;

    private final OwnedVoucherQueryConverter ownedVoucherQueryConverter;
    private final OwnedVoucherResultConverter ownedVoucherResultConverter;
    private final WalletDeleteQueryConverter walletDeleteQueryConverter;
    private final WalletInsertQueryConverter walletInsertQueryConverter;
    private final WalletInsertResultConverter walletInsertResultConverter;

    private final OwnedCustomersQueryConverter ownedCustomersQueryConverter;
    private final OwnedCustomersResultConverter ownedCustomersResultConverter;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;

        ownedVoucherQueryConverter = new OwnedVoucherQueryConverter();
        ownedVoucherResultConverter = new OwnedVoucherResultConverter();
        walletDeleteQueryConverter = new WalletDeleteQueryConverter();
        walletInsertQueryConverter = new WalletInsertQueryConverter();
        walletInsertResultConverter = new WalletInsertResultConverter();
        ownedCustomersQueryConverter = new OwnedCustomersQueryConverter();
        ownedCustomersResultConverter = new OwnedCustomersResultConverter();
    }

    public List<OwnedVoucherResult> findOwnedVouchersByCustomer(OwnedVoucherParam param) {
        OwnedVouchersQuery query = ownedVoucherQueryConverter.convert(param);
        List<OwnedVoucherResultSet> resultSets =
                voucherWalletRepository.findOwnedVouchersByCustomer(query);

        return resultSets.stream()
                .map(ownedVoucherResultConverter::convert)
                .collect(Collectors.toList());
    }

    public void deleteWallet(WalletDeleteParam param) {
        WalletDeleteQuery query = walletDeleteQueryConverter.convert(param);
        voucherWalletRepository.delete(query);
    }

    public WalletAssignResult assignWallet(WalletAssignParam param) {
        WalletInsertQuery query = walletInsertQueryConverter.convert(param);

        WalletInsertResultSet resultSet = voucherWalletRepository.insert(query);
        return walletInsertResultConverter.convert(resultSet);
    }

    public List<OwnedCustomerResult> findOwnedCustomersByVoucher(OwnedCustomersParam param) {
        OwnedCustomersQuery query = ownedCustomersQueryConverter.convert(param);
        List<OwnedCustomerResultSet> resultSets =
                voucherWalletRepository.findOwnedCustomersByVoucher(query);

        return resultSets.stream()
                .map(ownedCustomersResultConverter::convert)
                .collect(Collectors.toList());
    }
}
