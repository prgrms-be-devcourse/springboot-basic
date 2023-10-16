package study.dev.spring.voucher.presentation.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {

	INPUT_NAME("등록할 바우처의 이름을 입력해주세요 : "),
	SELECT_VOUCHER_TYPE("등록할 바우처의 타입을 선택해주세요" + System.lineSeparator() +
		"1. 정액 할인 2. 정률 할인 : "),
	INPUT_DISCOUNT_AMOUNT("할인가 혹은 할인률을 입력해주세요 : ");

	private final String value;
}
