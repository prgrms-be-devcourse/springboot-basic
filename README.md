# SpringBoot Basic Weekly Mission 2
### 과제 요구사항

**기본) 바우처 관리 애플리케이션 추가 기능**
- [x] 단위테스트 작성
- [x] Customers 테이블 정의 및 추가
- [x] Vouchers 테이블 정의 및 추가
- [ ] JdbcTemplate을 사용한 CustomerRepository
- [x] JdbcTemplate을 사용한 VoucherRepository (CRUD API)

**심화) 바우처 지갑**

- [ ] 고객의 바우처 보유 정보 조회(SELECT)
- [ ] 특정 바우처 보유 고객 정보 조회(JOIN)
- [ ] 특정 고객에게 바우처 할당 기능(UPDATE)
- [ ] 고객 보유 바우처 제거 기능(UPDATE)

### **바우처 관리 애플리케이션 설명**
    === Voucher Program ===
    Type exit to exit the program.
    Type create to create a new voucher.
    Type list to list all vouchers.
    Type blacklist to list all blacklist custom info

    Type customer voucher
    Type edit customer voucher(add, delete)
    Type voucher customer

- create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher 중 선택)
    ```
    >>create
    Type voucher type(fixed or percent) and amount
    fixed
    1000
    ```
- list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
    ```
    >>list
    id: af4f2e6d-c39d-4ea3-b114-955c79103d78
    type: fixed
    amount: 1000.0
    ```

- blacklist 커맨드를 통해 csv파일의 기록된 블랙리스트 정보를 조회할 수 있다.
    ```
    >>blacklist
    name: moon age: 25 sex: man description: X
    name: moon age: 26 sex: woman description: X
    ```
### Class Diagram

![Untitled-2022-03-29-1415](https://user-images.githubusercontent.com/37391733/163552243-7c7b42fb-3fb1-49b1-b612-9cce565bef8b.png)
