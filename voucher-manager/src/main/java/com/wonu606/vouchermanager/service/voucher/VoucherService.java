package com.wonu606.vouchermanager.service.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.service.voucher.converter.VoucherServiceConverterManager;
import com.wonu606.vouchermanager.service.voucher.factory.VoucherFactory;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResult;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private static final int MAX_RETRIES = 3;

    private final VoucherRepository repository;
    private final VoucherFactory factory;
    private final VoucherServiceConverterManager converterManager;

    public VoucherService(VoucherRepository repository, VoucherFactory factory,
            VoucherServiceConverterManager converterManager) {
        this.repository = repository;
        this.factory = factory;
        this.converterManager = converterManager;
    }

    public VoucherCreateResult createVoucher(VoucherCreateParam param) {
        return createVoucher(param, 0);
    }

    public List<VoucherResult> getVoucherList() {
        List<VoucherResultSet> resultSets = repository.findAll();
        return resultSets.stream()
                .map(rs -> converterManager.convert(rs, VoucherResult.class))
                .collect(Collectors.toList());
    }

    private VoucherCreateResult createVoucher(VoucherCreateParam voucherCreateParam,
            int retryCount) {
        if (retryCount > MAX_RETRIES) {
            log.warn("uuid를 재성성하여 저장하는 시도 최대 횟수 {}를 초과하였습니다. ", MAX_RETRIES);
            throw new IllegalStateException();
        }

        Voucher voucher = factory.create(voucherCreateParam);
        VoucherInsertQuery query = converterManager.convert(voucher, VoucherInsertQuery.class);
        try {
            VoucherInsertResultSet resultSet = repository.insert(query);
            return converterManager.convert(resultSet, VoucherCreateResult.class);
        } catch (DuplicateKeyException e) {
            log.info("DuplicateKeyException가 발생하였습니다. ", e);
            return createVoucher(voucherCreateParam, retryCount + 1);
        }
    }
}

