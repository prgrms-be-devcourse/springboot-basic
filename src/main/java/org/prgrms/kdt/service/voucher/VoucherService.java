package org.prgrms.kdt.service.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherException;
import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.factory.VoucherFactory;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private final VoucherFactory voucherFactory = new VoucherFactory();

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucherById(Long voucherId) {
        try {
            VoucherEntity voucherEntity = voucherRepository.findById(voucherId);
            return voucherFactory.toDomain(voucherEntity);
        } catch (Exception e) {
            throw new VoucherException(ErrorMessage.NOT_FOUND_VOUCHER);
        }
    }

    public List<Voucher> getVouchersByType(String voucherType){
        List<VoucherEntity> voucherEntities = voucherRepository.findByType(voucherType);
        return voucherEntities.stream().map(x -> voucherFactory.toDomain(x)).collect(Collectors.toList());
    }

    public List<Voucher> getVouchers() {
        List<VoucherEntity> voucherEntities = voucherRepository.findAll();
        return voucherEntities.stream().map(x -> voucherFactory.toDomain(x)).collect(Collectors.toList());

    }

    public Voucher save(VoucherType type, Long discount) {
        switch (type) {
            case FIXED, PERCENT -> {
                return voucherFactory.toDomain(voucherRepository.insert(VoucherEntity.from(VoucherFactory.createVoucher(type,discount))));
            }
            default -> throw new VoucherException(ErrorMessage.INVALID_TYPE);
        }
    }

    public void deleteById(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }



}
