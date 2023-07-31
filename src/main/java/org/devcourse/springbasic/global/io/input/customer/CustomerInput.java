package org.devcourse.springbasic.global.io.input.customer;

import org.devcourse.springbasic.domain.customer.domain.CustomerMenuType;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;

public interface CustomerInput {
    CustomerMenuType menu();
    CustomerDto.SaveRequestDto signUp();
}
