package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MappingVoucherRepository implements VoucherRepository {

    private final VoucherResultSetRepository voucherResultSetRepository;

    public MappingVoucherRepository(VoucherResultSetRepository voucherResultSetRepository) {
        this.voucherResultSetRepository = voucherResultSetRepository;
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherResultSetRepository.save(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return voucherResultSetRepository.findById(id)
                .map(this::convertResultSetToEntity);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherResultSetRepository.findAll()
                .stream().map(this::convertResultSetToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAllByUuIds(List<UUID> uuidList) {
        List<VoucherResultSet> voucherResultSetList = voucherResultSetRepository.findAllByUuids(uuidList);
        return voucherResultSetList.stream()
                .map(this::convertResultSetToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        voucherResultSetRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        voucherResultSetRepository.deleteAll();
    }

    private Voucher convertResultSetToEntity(VoucherResultSet voucherResultSet) {
        String simpleName = voucherResultSet.getSimpleName();

        if (isFixedAmountVoucher(simpleName)) {
            return createFixedAmountVoucher(voucherResultSet);
        } else if (isPercentageVoucher(simpleName)) {
            return createPercentageVoucher(voucherResultSet);
        }
        throw new IllegalStateException("VoucherResult를 Voucher로 변환할 수 없습니다: " + simpleName);
    }

    private boolean isFixedAmountVoucher(String simpleName) {
        return FixedAmountVoucher.class.getSimpleName().equals(simpleName);
    }

    private FixedAmountVoucher createFixedAmountVoucher(VoucherResultSet voucherResultSet) {
        return new FixedAmountVoucher(voucherResultSet.getUuid(),
                new FixedAmountValue(voucherResultSet.getDiscountValue()));
    }

    private boolean isPercentageVoucher(String simpleName) {
        return PercentageVoucher.class.getSimpleName().equals(simpleName);
    }

    private PercentageVoucher createPercentageVoucher(VoucherResultSet voucherResultSet) {
        return new PercentageVoucher(voucherResultSet.getUuid(),
                new PercentageDiscountValue(voucherResultSet.getDiscountValue()));
    }

}
