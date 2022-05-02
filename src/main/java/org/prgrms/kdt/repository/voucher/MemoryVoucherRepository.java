package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Optional<Voucher> insertVoucher(Voucher voucher) {
        return Optional.ofNullable(storage.put(voucher.getVoucherId(), voucher));
    }

    @Override
    public VoucherMap getVoucherList() {
        return new VoucherMap(storage);
    }

    @Override
    public void deleteVoucherById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public Optional<Voucher> getByVoucherId(UUID voucherId) {
        if (storage.containsKey(voucherId)) {
            return Optional.empty();
        }
        return Optional.of(storage.get(voucherId));
    }

    @Override
    public Vouchers getVoucherListOwnerIdIsEmpty() {
        List<Voucher> voucherList = storage.values().stream()
                .filter(voucher -> voucher.getCustomer() == null)
                .toList();
        return new Vouchers(voucherList);
    }

    @Override
    public Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId) {
        Voucher voucher = storage.get(voucherId);
        Customer originalCustomer = voucher.getCustomer();
        Customer newCustomer = new Customer(
                customerId,
                originalCustomer.getName(),
                originalCustomer.getEmail(),
                originalCustomer.getLastLoginAt(),
                originalCustomer.getCreateAt());
        Voucher newVoucher = createVoucher(voucher, newCustomer);
        return Optional.ofNullable(storage.replace(voucherId, newVoucher));
    }

    @Override
    public Optional<Voucher> getVoucherNotProvided(UUID voucherId) {
        Voucher returnVoucher = storage.get(voucherId);
        Customer returnCustomer = returnVoucher.getCustomer();
        if (returnCustomer == null) {
            return Optional.of(returnVoucher);
        }
        return Optional.empty();
    }

    @Override
    public void deleteAllVouchers() {
        storage.clear();
    }

    @Override
    public Vouchers getVoucherListByVoucherType(int voucherType) {
        List<Voucher> voucherList = storage.values().stream()
                .filter(voucher -> voucher.getVoucherType() == voucherType)
                .toList();
        return new Vouchers(voucherList);
    }

    @Override
    public Vouchers getVoucherListByCreatedFromToDate(String fromDate, String toDate) {
        LocalDateTime fromLocalDateTime = stringToLocalDateTime(fromDate);
        LocalDateTime toLocalDateTime = stringToLocalDateTime(toDate);
        List<Voucher> voucherList = storage.values().stream()
                .filter(voucher -> voucher.getCreateAt().isAfter(fromLocalDateTime)
                        && voucher.getCreateAt().isBefore(toLocalDateTime))
                .toList();
        return new Vouchers(voucherList);
    }

    private LocalDateTime stringToLocalDateTime(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return LocalDateTime.from(localDate);
    }

    private Voucher createVoucher(Voucher voucher, Customer customer) {
        int voucherType = voucher.getVoucherType();
        if (VoucherType.FIXED_AMOUNT.getTypeNumber() == voucherType) {
            return new FixedAmountVoucher(
                    voucher.getVoucherId(),
                    voucher.getDiscountAmount(),
                    customer,
                    voucher.getOwnedAt(),
                    voucher.getCreateAt());
        }
        return new PercentDiscountVoucher(
                voucher.getVoucherId(),
                voucher.getDiscountAmount(),
                voucher.getCreateAt(),
                customer,
                voucher.getOwnedAt());
    }
}
