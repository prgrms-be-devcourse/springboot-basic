package com.programmers.order.service;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixVoucherStoreManagerTest {

	private Pattern limitRegex;

	@BeforeEach
	public void init(){
		limitRegex = Pattern.compile("^[0-9]{1,9}");
	}
	@Test
	@DisplayName("정규표현식 테스트")
	public void testRegex(){
	    //given

		boolean matches = limitRegex.matcher("123_456_789").matches();
		System.out.println(matches);
		//when

	    //then
	}

}