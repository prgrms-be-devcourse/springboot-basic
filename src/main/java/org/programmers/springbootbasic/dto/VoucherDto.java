package org.programmers.springbootbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.programmers.springbootbasic.data.VoucherType;

/**
 * 사용자 input인 String의 type 대신 VoucherType Enum 클래스로 변경된 DTO 클래스
 */
@Getter
@AllArgsConstructor
public class VoucherDto {
    private VoucherType type;
    private long amount;
}
