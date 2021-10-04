package org.programmers.kdt.voucher.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.*;
import org.programmers.kdt.voucher.factory.VoucherFactory;
import org.programmers.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    @Override
    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void useVoucher(Voucher voucher) {
        // TODO : To be implemented later...
    }

    private void addVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        logger.info("New Voucher has been successfully added to repository : {} {} {}",
                voucher.getClass(), voucher.getVoucherId(), voucher.getDiscount());
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount) {
        Voucher voucher;
        try {
            voucher = voucherFactory.createVoucher(voucherType, voucherId, discount);
        } catch (RuntimeException e) {
            logger.error("Failed to created a voucher : {}", e.getMessage());
            throw e;
        }
        addVoucher(voucher);
        return voucher;
    }

    @Override
    public void removeVoucher(UUID voucherid) {
        voucherRepository.deleteVoucher(voucherid);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public List<Voucher> getAllUnregisteredVouchers() {
        return voucherRepository.findAllUnregisteredVouchers();
    }

    @Override
    public VoucherStatus getVoucherStatus(Voucher voucher) {
        return voucher.getStatus();
    }

    @Override
    public Voucher addOwner(Customer customer, Voucher voucher) {
        return voucherRepository.addOwner(customer, voucher);
    }

    @Override
    public void removeOwner(Customer customer, UUID voucherId) {
        voucherRepository.removeOwner(customer, voucherId);
    }

    @Override
    public void releaseAllVoucherBelongsTo(Customer customer) {
        voucherRepository.releaseAllVoucherBelongsTo(customer);
    }

    @Override
    public Optional<UUID> findCustomerIdHoldingVoucherOf(UUID voucherId) {
        return voucherRepository.findCustomerIdByVoucherId(voucherId);
    }

    @Override
    public List<Voucher> getAllVouchersBelongsToCustomer(Customer customer) {
        return voucherRepository.findVouchersByCustomerId(customer.getCustomerId());
    }

    @Override
    public String getPrintFormat(Voucher voucher) {
        return MessageFormat.format("<< {0} Discount Voucher >>\nID : {1}\nDiscount: {2}",
                voucher.getVoucherType(), voucher.getVoucherId(),
                voucher.getVoucherType() == VoucherType.FIXED ? "$" + voucher.getDiscount() : voucher.getDiscount() + "%");
    }

    @Override
    public List<Voucher> getVouchersBetween(Timestamp from, Timestamp to) {
        return voucherRepository.findVouchersBetween(from, to);
    }

    public List<Voucher> getVouchersWithConditions(String voucherId, String voucherType, String dateFrom, String dateTo) {
        // 날짜 범위 검색 조건
        Timestamp from = Timestamp.valueOf(dateFrom + " 00:00:00");
        Timestamp to = Timestamp.valueOf(dateTo + " 23:59:59");
        List<Voucher> vouchers = getVouchersBetween(from, to);

        // Voucher ID 검색 조건이 지정되었을 경우
        if (!voucherId.equals("all")) {
            vouchers = List.of(vouchers.stream().filter(voucher -> voucher.getVoucherId().equals(UUID.fromString(voucherId))).findAny().get());
        }

        // Voucher Type 검색 조건이 지정되었을 경우
        if (!VoucherType.of(voucherType).equals(VoucherType.ALL)) {
            VoucherType type = VoucherType.of(voucherType);
            vouchers = vouchers.stream().filter(voucher -> voucher.getVoucherType().equals(type)).toList();
        }

        return vouchers;
    }

    @Override
    public List<Voucher> filteringWithId(List<Voucher> vouchers, String voucherId) {
        return vouchers.stream().filter(voucher -> voucher.getVoucherId().equals(UUID.fromString(voucherId))).toList();
    }

    @Override
    public List<Voucher> filteringWithType(List<Voucher> vouchers, String voucherType) {
        VoucherType type = VoucherType.of(voucherType);
        return vouchers.stream().filter(voucher -> voucher.getVoucherType().equals(type)).toList();
    }

    @Override
    public VoucherDetailDto getDetailInfoOf(UUID voucherId) {
        VoucherDetailDto result = VoucherConverter.convertToVoucherDetailDto(getVoucher(voucherId));

        Optional<UUID> foundOwnerId = findCustomerIdHoldingVoucherOf(UUID.fromString(result.getVoucherId()));
        if (foundOwnerId.isPresent()) {
            result.updateOwnerId(foundOwnerId.toString());
        }

        return result;
    }
}
