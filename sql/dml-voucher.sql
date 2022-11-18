# CREATE
INSERT INTO vouchers_demo (voucher_id, voucher_type, value)
VALUES (UUID_TO_BIN(:voucherId), :voucherType, :value);
# READ
# id로 찾기
SELECT v.voucher_id, vt.voucher_type, v.value
FROM vouchers_demo v
         JOIN voucher_type vt on vt.id = v.voucher_type
WHERE voucher_id = UUID_TO_BIN(:voucherId);
# 전체 찾기
SELECT v.voucher_id, vt.voucher_type, v.value
FROM vouchers_demo v
         JOIN voucher_type vt on vt.id = v.voucher_type;
#UPDATE 아직은 추가 X
# UPDATE vouchers_demo set voucher_id =:voucherId,

#DELETE
# id 로 지우기
DELETE
FROM vouchers_demo
WHERE voucher_id = UUID_TO_BIN(:voucherId);
# 전체 지우기
DELETE
FROM vouchers_demo