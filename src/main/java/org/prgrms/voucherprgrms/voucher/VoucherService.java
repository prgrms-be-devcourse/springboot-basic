package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.prgrms.voucherprgrms.voucher.model.VoucherSearchParam;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    public VoucherService(@Qualifier("named") VoucherRepository voucherRepository, VoucherCreator voucherCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
    }

    /**
     * create Voucher
     */
    @Transactional
    public Voucher createVoucher(VoucherForm voucherForm) throws IllegalArgumentException, DuplicateKeyException {
        Voucher voucher = voucherCreator.create(voucherForm);
        logger.info("CREATE Voucher({})", voucher.getVoucherId());
        return voucherRepository.insert(voucher);
    }


    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher findByVoucherId(String voucherId) {
        UUID id = UUID.fromString(voucherId);
        return voucherRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Voucher> findByType(String voucherType) {
        return voucherRepository.findByVoucherType(voucherType);
    }

    public List<Voucher> findByCreatedAt(LocalDateTime start, LocalDateTime end) {
        return voucherRepository.findByCreated(start, end);
    }

    @Transactional
    public void deleteVoucher(String voucherId) throws IllegalArgumentException {
        UUID id = UUID.fromString(voucherId);
        voucherRepository.deleteById(id);
    }

    public List<Voucher> search(VoucherSearchParam searchParam) {
        switch (searchParam.getSearchType()) {
            case "VoucherType":
                return findByType(searchParam.getSearchKeyword());
            case "CreatedAt":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime start = LocalDate.parse(searchParam.getStartDate(), formatter).atStartOfDay();
                LocalDateTime end = LocalDate.parse(searchParam.getEndDate(), formatter).atStartOfDay();

                return findByCreatedAt(start, end);
            default:
                throw new IllegalArgumentException("SearchType error");
        }
    }

}
