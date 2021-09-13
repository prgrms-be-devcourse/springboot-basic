package org.prgms.order.voucher.service;

import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherIndexType;
import org.prgms.order.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher insert(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher update(Voucher voucher){ return voucherRepository.update(voucher); }
    public void updateExpiryDate(UUID voucherId, LocalDateTime withNano) {
        voucherRepository.updateExpiryDate(voucherId, withNano);
    }
    public void deleteById(UUID voucherId){voucherRepository.deleteById(voucherId);}


    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Can not find a voucher for {0}", voucherId)
                ));
    }
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
    public List<Voucher> findByType(VoucherIndexType type){return voucherRepository.findByType(type);}
    public List<Voucher> findByTypeAmount(VoucherIndexType type, long amount){return voucherRepository.findByTypeAmount(type, amount);}
    public List<Voucher> findAvailables(){ return voucherRepository.findAvailables();}
    public boolean findAvailableById(UUID voucherId){ return (voucherRepository.findById(voucherId).get().getExpiredAt() == null || voucherRepository.findById(voucherId).get().getExpiredAt().isAfter(LocalDateTime.now())); }


    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
