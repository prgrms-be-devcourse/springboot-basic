package org.programmers.springbootbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.exception.WrongInputException;

@AllArgsConstructor
@Getter
public class VoucherInputDto {
    private String type;
    private long amount;


    public void validateVoucher() throws WrongInputException {
        if(isWrongInput()) throw new WrongInputException();
    }

    private boolean isWrongInput() {
        return VoucherType.WRONG_INPUT.equals(VoucherType.valueOf(type));
    }
}
