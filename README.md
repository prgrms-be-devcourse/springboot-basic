# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 요구사항
- [ ] gradle 로 프로젝트를 구성하고 Spring Boot CLI를 설치해 사용해보고 프로젝트를 만든다.
- [ ] 사용자의 명령을 입력받을 수 있다.
- [ ] 요구사항을 출력할 수 있다.
- [ ] 유저는 바우처를 생성할 수 있다.
  - [ ] 고정금액, 백분율바우처를 선택할 수 있다.
- [ ] 생성한 바우처를 조회할 수 있다.
  - [ ] 바우처 정보를 `메모리`에 관리한다.
- [ ] 적절한 로그를 기록하고 logback을 설정해서 에러는 파일로 기록된다.
---
- [ ] 바우처 정보를 `파일`로 관리한다.
- [ ] 고객 블랙 리스트 명단을 파일로 작성한다.
  - [ ] 블랙리스트를 읽어오고 조회할 수 있다.

---
```shell

=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.

create

=== Voucher Type Choice ===
Type fixed {Amount}
Type percent {1 ~ 100}

fixed 1000

바우처가 생성되었습니다.

=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.

list

fixed 1000
fixed 2000
percent 10
percent 20
percent 30

exit

```
