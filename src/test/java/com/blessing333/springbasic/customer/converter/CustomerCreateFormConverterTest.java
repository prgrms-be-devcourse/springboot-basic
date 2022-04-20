package com.blessing333.springbasic.customer.converter;

import com.blessing333.springbasic.customer.domain.Customer;
import com.blessing333.springbasic.customer.ui.CustomerCreateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerCreateFormConverterTest {
    private final CustomerCreateFormConverter converter = new CustomerCreateFormConverter();

    @DisplayName("사용자가 작성한 CustomerCreateForm을 Customer 도메인 객체로 변환한다")
    @Test
    void convertTest(){
        CustomerCreateForm form = new CustomerCreateForm("test","test@Email");

        Customer converted = converter.convert(form);

        assertThat(converted.getName()).isEqualTo(form.getName());
        assertThat(converted.getEmail()).isEqualTo(form.getEmail());
    }

    @DisplayName("이름이 비었으면 IllegalArgumentException 발생시킨다")
    @Test
    void convertTestWithBlankName(){
        CustomerCreateForm form = new CustomerCreateForm("","test@Email");
        assertThrows(IllegalArgumentException.class,()->converter.convert(form));
    }

    @DisplayName("메일이 비었으면 IllegalArgumentException 발생시킨다")
    @Test
    void convertTestWithBlankEmail(){
        CustomerCreateForm form = new CustomerCreateForm("test","");
        assertThrows(IllegalArgumentException.class,()->converter.convert(form));
    }

}
