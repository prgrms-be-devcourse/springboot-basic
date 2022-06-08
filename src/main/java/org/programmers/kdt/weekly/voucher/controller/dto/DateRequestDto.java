package org.programmers.kdt.weekly.voucher.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

public record DateRequestDto(
	@NotNull
	@Future(message = "시작일자는 현재 날짜 이후일 수 없습니다.")
	LocalDate begin,

	@NotNull
	@Future(message = "종료일자는 현재 날짜 이후일 수 없습니다.")
	LocalDate end) {

	@AssertTrue(message = "시작일은 종료일보다 미래일 수 없습니다.")
	public boolean isValidSalePeriod() {
		return end.isAfter(begin);
	}
}
