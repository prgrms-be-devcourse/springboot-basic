package kr.co.programmers.springbootbasic.customer.domain;

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
}
