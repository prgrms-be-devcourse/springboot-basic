package org.prgrms.springbootbasic.util;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.dto.CustomerDto;
import org.prgrms.springbootbasic.dto.VoucherDTO;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public class DtoConverter {

    private DtoConverter() {
        throw new AssertionError("유틸성 클래스");
    }

    public static List<VoucherDTO> toVoucherDTOs(List<Voucher> vouchers) {
        return vouchers.stream()
            .map(DtoConverter::toVoucherDTO)
            .collect(Collectors.toList());
    }

    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        if (voucher.isFixed()) {
            return new VoucherDTO(
                voucher.getVoucherId(),
                VoucherType.FIXED,
                ((FixedAmountVoucher) voucher).getAmount(),
                0,
                voucher.getCustomerId()
            );
        } else {
            return new VoucherDTO(
                voucher.getVoucherId(),
                VoucherType.PERCENT,
                0,
                ((PercentDiscountVoucher) voucher).getPercent(),
                voucher.getCustomerId()
            );
        }
    }

    public static List<CustomerDto> toCustomerDtos(List<Customer> customers) {
        return customers.stream()
            .map(DtoConverter::toCustomerDto)
            .collect(Collectors.toList());
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(
            customer.getCustomerId().toString(),
            customer.getEmail().getEmail(),
            customer.getName().getName());
    }
}
