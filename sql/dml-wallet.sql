INSERT INTO wallet(voucher_id, customer_id)
VALUES (:voucherId, :customerId);

-- 만약 직접 참조를한다면;
select v.*
from wallet
         join vouchers_demo as v on wallet.voucher_id = v.voucher_id;
select c.*
from wallet
         join customers_demo as c on wallet.customer_id = c.customer_id;

-- 만약 간접 참조를 한다면;
select voucher_id
from wallet
where customer_id = :costomerId;
select customer_id
from wallet
where voucher_id = :voucherId;

delete
from wallet;
delete
from wallet
where customer_id = :customerId;
