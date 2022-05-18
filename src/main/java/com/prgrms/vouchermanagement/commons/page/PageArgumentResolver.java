package com.prgrms.vouchermanagement.commons.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Logger logger = LoggerFactory.getLogger(PageArgumentResolver.class);

	private static final String OFFSET_PARAM_NAME = "offset";
	private static final String LIMIT_PARAM_NAME = "limit";

	private static final long DEFAULT_OFFSET = 0L;
	private static final int DEFAULT_LIMIT = 5;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Pageable.class
			.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		String offsetParam = webRequest.getParameter(OFFSET_PARAM_NAME);
		String limitParam = webRequest.getParameter(LIMIT_PARAM_NAME);

		return new SimplePage(getOffset(offsetParam), getLimit(limitParam));
	}

	private long getOffset(String offsetParam) {
		long offset = DEFAULT_OFFSET;

		try {
			offset = Long.parseLong(offsetParam);
		} catch (NumberFormatException e) {
			logger.info("요청 파라미터 Page offset 은 0 이상의 정수값 이어야한다");
		}
		offset = offset < 0 ? DEFAULT_OFFSET : offset;

		return offset;
	}

	private int getLimit(String limitParam) {
		int limit = DEFAULT_LIMIT;

		try {
			limit = Integer.parseInt(limitParam);
		} catch (NumberFormatException e) {
			logger.info("요청 파라미터 Page limit 은 0 이상의 정수값 이어야한다");
		}

		limit = limit < 1 ? DEFAULT_LIMIT : limit;

		return limit;
	}

}
