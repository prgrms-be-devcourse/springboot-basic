package org.prgrms.vouchermanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record CustomerRequest(String name, String email, Boolean isBlack) {

}
