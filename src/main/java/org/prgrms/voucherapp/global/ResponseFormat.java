package org.prgrms.voucherapp.global;

public record ResponseFormat(boolean success, int status, String message, Object body) {
}
