package org.prgrms.springbootbasic.util;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springbootbasic.dto.CustomerDto;
import org.prgrms.springbootbasic.dto.VoucherDTO;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public class DtoConverter {

    private DtoConverter() {
        throw new AssertionError("유틸성 클래스");
    }

    public static List<VoucherDTO> toVoucherDTOs(List<Voucher> voucher) {
        return voucher.stream()
            .map(VoucherDTO::new)
            .collect(Collectors.toList());
    }

    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher);
    }

    public static List<CustomerDto> toCustomerDtos(List<Customer> customers) {
        return customers.stream()
            .map(DtoConverter::toCustomerDto)
            .collect(Collectors.toList());
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(
            customer.getCustomerId().toString(),
            customer.getEmail(),
            customer.getName());
    }
}
