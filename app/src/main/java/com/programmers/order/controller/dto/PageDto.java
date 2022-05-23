package com.programmers.order.controller.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Builder;
import lombok.Data;

public class PageDto {

	private PageDto() {
	}

	public static class Request {

		private Integer page;
		private Integer size;

		private SortType sort;
		private OrderType order;

		@Builder
		public Request(Integer page, Integer size, String sort, String order) {
			if ((page == null || page < 1) || (size == null || size < 5)) {
				page = 1;
				size = 10;
			}

			this.page = page;
			this.size = size;
			this.sort = SortType.of(sort);
			this.order = OrderType.of(order);
		}

		public Pageable getPageable(Sort sort) {
			if (sort.isEmpty()) {
				sort = sort.by("voucher_id : ASC");
			}
			return PageRequest.of(page - 1, size, sort);
		}
	}

	@Data
	public static class Response<DTO, DOMAIN> {
		private List<DTO> responses;
		private int totalPage;
		private int page;
		private int size;
		private int start;
		private int end;
		private boolean previous;
		private boolean next;
		private List<Integer> pageInventories;

		public Response(Page<DOMAIN> result, Function<DOMAIN, DTO> toResponse) {
			responses = result.stream().map(toResponse).toList();
			totalPage = result.getTotalPages();
			makePageList(result.getPageable());
		}

		private void makePageList(Pageable pageable) {

			this.page = pageable.getPageNumber() + 1;
			this.size = pageable.getPageSize();

			//temp end page
			int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

			start = tempEnd - 9;

			previous = start > 1;

			end = Math.min(totalPage, tempEnd);

			next = totalPage > tempEnd;

			pageInventories = IntStream.rangeClosed(start, end).boxed().toList();

		}

	}

}
