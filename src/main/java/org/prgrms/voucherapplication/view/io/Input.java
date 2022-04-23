package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.exception.InvalidMenuException;
import org.prgrms.voucherapplication.exception.InvalidVoucherTypeException;

public interface Input {
    /**
     * 메뉴 입력 (create, list)
     *
     * @return: 선택된 메뉴
     */
    Menu inputMenu() throws InvalidMenuException;

    /**
     * 바우처 유형 선택 (1, 2)
     *
     * @return: 선택된 바우처 유형
     */
    VoucherType inputVoucherType() throws InvalidVoucherTypeException;

    /**
     * 할인율/할인액 입력
     *
     * @return: 입력된 할인율/할인액
     */
    Long inputDiscount(VoucherType type);

    /**
     * 고객 이름, 이메일 입력
     * @return 입력된 name, email로 Customer 객체 생성 후 반환
     */
    Customer inputCustomerInformation();
}
