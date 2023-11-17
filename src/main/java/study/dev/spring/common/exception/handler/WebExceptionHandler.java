package study.dev.spring.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import study.dev.spring.common.exception.CommonException;

@ControllerAdvice
public class WebExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

	@ExceptionHandler(CommonException.class)
	public String handleCustomException(
		CommonException e,
		Model model
	) {
		logger.error("custom exception : ", e);

		model.addAttribute("message", e.getMessage());
		return "/error/error";
	}

	@ExceptionHandler(Exception.class)
	public String handleServerException(
		Exception e,
		Model model
	) {
		logger.error("server exception : ", e);

		model.addAttribute("message", "서버 내부 오류입니다. 다시 시도해주세요");
		return "/error/error";
	}
}
