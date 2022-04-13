package org.prgrms.voucherapplication.entity;

public interface Customer {
    /**
     * 고객 id 반환
     *
     * @return long type customerId
     */
    long getCustomerId();

    /**
     * 고객 이름 반환
     *
     * @return string type name
     */
    String getCustomerName();

    /**
     * 고객 정보를 string으로 반환
     *
     * @return string
     */
    String toString();
}
