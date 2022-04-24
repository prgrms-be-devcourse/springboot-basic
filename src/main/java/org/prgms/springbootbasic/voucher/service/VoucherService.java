package org.prgms.springbootbasic.voucher.service;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.entity.Voucher;
import org.prgms.springbootbasic.voucher.entity.VoucherType;

public interface VoucherService {

	/**
	 * 바우터 타입은 시스템이 미리 정해놓음 (FIXEDAMOUNTVOUCHER, PERCENTDISCOUNTVOUCHER)
	 * Voucher의 실물을 생성하는 메서드
	 * @param type
	 * @param value
	 */

	void createVoucher(String type, int value);

	/**
	 * 바우처 목록 조회
	 *
	 * @return Map<VoucherType, Voucher> 바우처 종류 별 목록
	 * */
	Map<VoucherType, List<Voucher>> list();





}
