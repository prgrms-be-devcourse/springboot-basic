package study.dev.spring.common.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import study.dev.spring.common.exception.CommonException;

@ControllerAdvice
public class WebExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public String handleCustomException(
		CommonException e,
		Model model
	) {
		model.addAttribute("message", e.getMessage());

		return "/error/error";
	}
}
