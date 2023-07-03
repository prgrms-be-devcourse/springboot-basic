package kr.co.programmers.springbootbasic.customer.domain;

import kr.co.programmers.springbootbasic.customer.exception.NoValidStatusException;

import java.util.Arrays;

public enum CustomerStatus {
    WHITE(1),
    BLACK(2);
    private final int statusId;

    CustomerStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public static CustomerStatus resolveId(int id) {
        return Arrays.stream(values())
                .filter((status) -> status.getStatusId() == id)
                .findFirst()
                .orElseThrow(() -> new NoValidStatusException("올바르지 않은 사용자 상태 ID입니다.\n\n"));
    }
}
