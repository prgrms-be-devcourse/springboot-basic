package devcourse.springbootbasic;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TestDataFactory {

    public static Customer generateBlacklistCustomer(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, true);
    }

    public static Customer generateNotBlacklistCustomers(String name) {
        return Customer.createCustomer(UUIDUtil.generateRandomUUID(), name, false);
    }

    public static Voucher generateAssignedVoucher(VoucherType voucherType, long initialDiscountValue, UUID customerId) {
        return Voucher.createVoucher(UUIDUtil.generateRandomUUID(), voucherType, initialDiscountValue, customerId, LocalDateTime.now());
    }

    public static Voucher generateUnassignedVoucher(VoucherType voucherType, long initialDiscountValue) {
        return Voucher.createVoucher(UUIDUtil.generateRandomUUID(), voucherType, initialDiscountValue, null, LocalDateTime.now());
    }

    public static Voucher generateUnassignedVoucherWithCreatedAt(VoucherType voucherType, long initialDiscountValue, LocalDateTime createdAt) {
        return Voucher.createVoucher(UUIDUtil.generateRandomUUID(), voucherType, initialDiscountValue, null, createdAt);
    }

    public static List<Voucher> generateSampleVouchers() {
        Voucher voucher1 = generateUnassignedVoucherWithCreatedAt(VoucherType.FIXED, 100, LocalDateTime.of(2022, 12, 4, 1, 0));
        Voucher voucher2 = generateUnassignedVoucherWithCreatedAt(VoucherType.PERCENT, 20, LocalDateTime.of(2022, 12, 5, 2, 0));
        Voucher voucher3 = generateUnassignedVoucherWithCreatedAt(VoucherType.FIXED, 50, LocalDateTime.of(2023, 1, 1, 23, 59, 59));
        Voucher voucher4 = generateUnassignedVoucherWithCreatedAt(VoucherType.FIXED, 150, LocalDateTime.of(2023, 1, 2, 23, 59, 59));
        Voucher voucher5 = generateUnassignedVoucherWithCreatedAt(VoucherType.PERCENT, 10, LocalDateTime.of(2023, 1, 3, 5, 0));

        return List.of(voucher1, voucher2, voucher3, voucher4, voucher5);
    }

    @SuppressWarnings("unused") // @MethodSource 에서 사용
    private static Stream<Arguments> searchConditionProvider() {
        return Stream.of(
                Arguments.of(null, null, null, 5),
                Arguments.of(VoucherType.FIXED, null, null, 3),
                Arguments.of(VoucherType.PERCENT, null, null, 2),
                Arguments.of(null, LocalDate.of(2023, 1, 1), null, 3),
                Arguments.of(null, null, LocalDate.of(2023, 1, 2), 4),
                Arguments.of(VoucherType.FIXED, LocalDate.of(2023, 1, 1), null, 2),
                Arguments.of(VoucherType.FIXED, null, LocalDate.of(2023, 1, 1), 2),
                Arguments.of(VoucherType.FIXED, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5), 2)
        );
    }
}
