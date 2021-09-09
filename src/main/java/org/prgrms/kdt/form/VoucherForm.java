package org.prgrms.kdt.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 11:57 오전
 */
public class VoucherForm {

    @NotBlank(message = "공백을 허용하지 않습니다.")
    @Length(min = 1, max = 20, message = "1 ~ 20자 이내로 입력해주세요.")
    private String name;

    @Min(value = 0, message = "0이하는 입력할 수 없습니다.")
    @Max(value = 1_000_000, message = "1,000,000을 넘길 수 없습니다.")
    private String discount;

    @NotBlank(message = "타입을 반드시 선택해주세요.")
    private String voucherType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

}
