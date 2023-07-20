package com.wonu606.vouchermanager.service.voucherwallet;

import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.repository.voucherwallet.VoucherWalletRepository;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.converter.VoucherWalletServiceConverterManager;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
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
    private final VoucherWalletServiceConverterManager converterManager;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository,
            VoucherWalletServiceConverterManager converterManager) {
        this.voucherWalletRepository = voucherWalletRepository;
        this.converterManager = converterManager;
    }

    public List<OwnedVoucherResult> findOwnedVouchersByCustomer(OwnedVouchersParam param) {
        OwnedVouchersQuery query = converterManager.convert(param, OwnedVouchersQuery.class);
        List<OwnedVoucherResultSet> resultSets =
                voucherWalletRepository.findOwnedVouchersByCustomer(query);

        return resultSets.stream()
                .map(rs -> converterManager.convert(rs, OwnedVoucherResult.class))
                .collect(Collectors.toList());
    }

    public void deleteWallet(WalletDeleteParam param) {
        WalletDeleteQuery query = converterManager.convert(param, WalletDeleteQuery.class);
        voucherWalletRepository.delete(query);
    }

    public WalletAssignResult assignWallet(WalletAssignParam param) {
        WalletInsertQuery query = converterManager.convert(param, WalletInsertQuery.class);

        WalletInsertResultSet resultSet = voucherWalletRepository.insert(query);
        return converterManager.convert(resultSet, WalletAssignResult.class);
    }

    public List<OwnedCustomerResult> findOwnedCustomersByVoucher(OwnedCustomersParam param) {
        OwnedCustomersQuery query = converterManager.convert(param, OwnedCustomersQuery.class);
        List<OwnedCustomerResultSet> resultSets =
                voucherWalletRepository.findOwnedCustomersByVoucher(query);

        return resultSets.stream()
                .map(rs -> converterManager.convert(rs, OwnedCustomerResult.class))
                .collect(Collectors.toList());
    }
}
