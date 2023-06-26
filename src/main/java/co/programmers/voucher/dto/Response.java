package co.programmers.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Response<T> {
	private State state;
	private T responseData;

	public enum State {SUCCESS, FAILED}
}
