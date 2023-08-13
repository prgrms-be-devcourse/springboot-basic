package org.promgrammers.voucher.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomerType {
    WHITE("white"),

    BLACK("black");

    private final String typeName;

}
