package study.dev.spring.common.dto;

import java.util.List;

public record ListWrapper<T>(
	List<T> result
) {
}
