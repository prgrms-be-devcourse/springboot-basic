package com.programmers.order.exception;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ControllerException {

	public static class NotValidatedException extends MethodArgumentNotValidException {
		public NotValidatedException(MethodParameter parameter, BindingResult bindingResult) {
			super(parameter, bindingResult);
		}
	}
}
