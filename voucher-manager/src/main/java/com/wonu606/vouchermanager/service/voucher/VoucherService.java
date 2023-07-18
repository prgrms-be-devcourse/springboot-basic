package com.wonu606.vouchermanager.service.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import com.wonu606.vouchermanager.service.voucher.factory.VoucherFactory;
import com.wonu606.vouchermanager.service.voucher.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
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

    private final VoucherWalletService voucherWalletService;

    private final VoucherRepository repository;
    private final VoucherFactory factory;

    public VoucherService(VoucherWalletService voucherWalletService,
            VoucherRepository repository, VoucherFactory factory) {
        this.voucherWalletService = voucherWalletService;
        this.repository = repository;
        this.factory = factory;
    }

    public VoucherCreateResult createVoucher(VoucherCreateParam param) {
        return createVoucher(param, 0);
    }

    public VoucherListResult getVoucherList() {
        VoucherListResultSet resultSet = repository.findAll();
        return VoucherListResultConverter.convert(resultSet);
    }

    public OwnedCustomersResult findOwnedCustomersByVoucher(OwnedCustomersParam param) {
        return voucherWalletService.findOwnedCustomersByVoucher(param);
    }

    public WalletAssignResult assignWallet(WalletAssignParam param) {
        return voucherWalletService.assignWallet(param);
    }

    private VoucherCreateResult createVoucher(VoucherCreateParam voucherCreateParam,
            int retryCount) {
        if (retryCount > MAX_RETRIES) {
            log.warn("uuid를 재성성하여 저장하는 시도 최대 횟수 {}를 초과하였습니다. ", MAX_RETRIES);
            throw new IllegalStateException();
        }

        Voucher voucher = factory.create(voucherCreateParam);
        VoucherQuery query = voucherQueryConverter.convert(voucher);
        try {
            VoucherResultSet resultSet = repository.save(query);
        } catch (DuplicateKeyException e) {
            log.info("DuplicateKeyException가 발생하였습니다. ", e);
            return createVoucher(voucherCreateParam, retryCount + 1);
        }
        return voucherCreateResultConverter.convert(resultSet);
    }
}

