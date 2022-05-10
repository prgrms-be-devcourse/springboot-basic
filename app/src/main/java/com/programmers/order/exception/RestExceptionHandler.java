package com.programmers.order.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.order.exception.JdbcException.JdbcException;

@RestControllerAdvice
public class RestExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DomainException.ConstraintViolationException.class)
	public String handleConstraintViolationException(DomainException.ConstraintViolationException e) {
		log.warn("domain 제약 조건을 위배하여 객체를 생성할 수 없습니다. : {} ", e.getMessage());
		return ErrorMessage.CLIENT_ERROR.getMessage();
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ServiceException.NotSupportedException.class)
	public String handleNotSupportedException(ServiceException.NotSupportedException e) {
		log.warn("지원하지 않는 서비스 타입입니다. : {} ", e.getMessage());
		return ErrorMessage.CLIENT_ERROR.getMessage();
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(JdbcException.NotExecuteQueryException.class)
	public String handleNotExecuteQueryException(JdbcException.NotExecuteQueryException e) {
		log.warn("쿼리를 실행 하는데 오류를 발생했습니다. : {} ", e.getMessage());
		return ErrorMessage.SERVER_ERROR.getMessage();
	}

}
