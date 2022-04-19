# SpringBoot Basic Weekly Mission 2
### 과제 요구사항

**기본) 바우처 관리 애플리케이션 추가 기능**
- [x] 단위테스트 작성
- [x] Customers 테이블 정의 및 추가
- [x] Vouchers 테이블 정의 및 추가
- [x] JdbcTemplate을 사용한 CustomerRepository
- [x] JdbcTemplate을 사용한 VoucherRepository (CRUD API)

**심화) 바우처 지갑**

- [x] 고객의 바우처 보유 정보 조회
- [x] 특정 바우처 보유 고객 정보 조회
- [x] 특정 고객에게 바우처 할당 기능
- [x] 고객 보유 바우처 제거 기능

### **바우처 관리 애플리케이션 설명**
    === Voucher Program ===
    Type exit to exit the program.
    Type create to create a new voucher.
    Type list to see all vouchers.
    Type blacklist to see all blacklist custom info.
    ========================
    Type give voucher to give voucher to custmer.
    Type take voucher to take a voucher from customer.
    Type customer list to see customer list with same voucher.
    Type voucher list to see voucher list of customer.

- give voucher 커맨드를 통해 고객에게 바우처를 부여할수 있다.
    ```
  >>give voucher
  Type custmer id you want to give voucher to.
  >>90876254-1988-4f45-b296-ebbb6fedd464
  Type voucher id you want to give to customer.
  >>34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698
  ```
- take voucher 커맨드를 통해 고객이 가지고 있는 바우처를 회수할 수 있다.
    ```
  >>take voucher
  Type custmer id you want to take voucher from.
  >>90876254-1988-4f45-b296-ebbb6fedd464
  Type voucher id you want to take from customer.
  >>34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698
    ```

- customer list 커맨드를 통해 동일한 바우처를 가진 고객 정보를 조회할 수 있다.
    ```
  >>customer list
  Type voucher id you want see customer list.
  >>>34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698
  name: moon age: 20 sex: woman
    ```
- voucher list 커맨드를 통해 고객이 가진 바우처 정보를 조회할 수 있다.
  ```
  >>voucher list
  Type customer id you want see voucher list.
  >>90876254-1988-4f45-b296-ebbb6fedd464
  id: 34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698
  type: fixed
  amount: 1000
  ```

### Class Diagram

![Untitled-2022-03-29-1415](https://user-images.githubusercontent.com/37391733/163552243-7c7b42fb-3fb1-49b1-b612-9cce565bef8b.png)
